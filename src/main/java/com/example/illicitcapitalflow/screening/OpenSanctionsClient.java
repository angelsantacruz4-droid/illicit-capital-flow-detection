package com.example.illicitcapitalflow.screening;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Thin client for the OpenSanctions {@code /match/{dataset}} endpoint.
 * See https://api.opensanctions.org/ for the API reference.
 */
@Component
public class OpenSanctionsClient {

    private static final String QUERY_ID = "q";

    private final RestTemplate restTemplate;
    private final OpenSanctionsProperties properties;

    public OpenSanctionsClient(RestTemplateBuilder builder, OpenSanctionsProperties properties) {
        this.properties = properties;
        RestTemplateBuilder configured = builder;
        if (StringUtils.hasText(properties.getApiKey())) {
            configured = configured.defaultHeader(HttpHeaders.AUTHORIZATION, "ApiKey " + properties.getApiKey());
        }
        this.restTemplate = configured.build();
    }

    public List<ScreeningMatch> match(PersonScreeningRequest person) {
        Map<String, Object> body = Map.of("queries", Map.of(QUERY_ID, Map.of(
                "schema", "Person",
                "properties", toProperties(person))));

        JsonNode response = restTemplate.postForObject(
                properties.getApiUrl() + "/match/{dataset}?limit={limit}&threshold={threshold}",
                body, JsonNode.class,
                properties.getDataset(), properties.getLimit(), properties.getThreshold());

        List<ScreeningMatch> matches = new ArrayList<>();
        if (response == null) {
            return matches;
        }
        for (JsonNode result : response.path("responses").path(QUERY_ID).path("results")) {
            double score = result.path("score").asDouble();
            if (score < properties.getThreshold()) {
                continue;
            }
            JsonNode props = result.path("properties");
            String id = result.path("id").asText();
            matches.add(new ScreeningMatch(
                    id,
                    result.path("caption").asText(),
                    score,
                    result.path("match").asBoolean(),
                    texts(props.path("topics")),
                    texts(props.path("country")),
                    texts(result.path("datasets")),
                    "https://www.opensanctions.org/entities/" + id + "/"));
        }
        return matches;
    }

    private static Map<String, List<String>> toProperties(PersonScreeningRequest person) {
        Map<String, List<String>> props = new LinkedHashMap<>();
        props.put("name", List.of(person.fullName()));
        if (StringUtils.hasText(person.birthDate())) {
            props.put("birthDate", List.of(person.birthDate()));
        }
        if (StringUtils.hasText(person.nationality())) {
            props.put("nationality", List.of(person.nationality().toLowerCase()));
        }
        if (StringUtils.hasText(person.idNumber())) {
            props.put("idNumber", List.of(person.idNumber()));
        }
        return props;
    }

    private static List<String> texts(JsonNode array) {
        List<String> values = new ArrayList<>();
        array.forEach(node -> values.add(node.asText()));
        return values;
    }
}
