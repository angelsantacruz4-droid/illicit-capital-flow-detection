package com.example.illicitcapitalflow.screening;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.example.illicitcapitalflow.screening.PersonScreeningResult.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

class PersonScreeningServiceTest {

    private MockRestServiceServer server;
    private PersonScreeningService service;

    @BeforeEach
    void setUp() {
        OpenSanctionsProperties properties = new OpenSanctionsProperties();
        properties.setApiUrl("https://api.test");
        properties.setApiKey("secret");
        MockServerRestTemplateCustomizer customizer = new MockServerRestTemplateCustomizer();
        OpenSanctionsClient client = new OpenSanctionsClient(new RestTemplateBuilder(customizer), properties);
        server = customizer.getServer();
        service = new PersonScreeningService(client);
    }

    @Test
    void strongSanctionsMatchIsHighRisk() {
        server.expect(requestTo("https://api.test/match/default?limit=5&threshold=0.7"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "ApiKey secret"))
                .andExpect(content().json("""
                        {"queries":{"q":{"schema":"Person","properties":{
                          "name":["Juan Perez"],"nationality":["ec"],"idNumber":["0912345678"]}}}}
                        """))
                .andRespond(withSuccess("""
                        {"responses":{"q":{"results":[
                          {"id":"ofac-1","caption":"Juan PEREZ","score":0.95,"match":true,
                           "datasets":["us_ofac_sdn"],
                           "properties":{"topics":["sanction"],"country":["ec"]}},
                          {"id":"weak-1","caption":"Juana Perezoso","score":0.4,"match":false,
                           "datasets":["peps"],"properties":{"topics":["role.pep"]}}
                        ]}}}
                        """, MediaType.APPLICATION_JSON));

        PersonScreeningResult result = service.screen(
                new PersonScreeningRequest("Juan Perez", null, "EC", "0912345678"));

        server.verify();
        assertThat(result.riskLevel()).isEqualTo(RiskLevel.HIGH);
        assertThat(result.matches()).singleElement().satisfies(m -> {
            assertThat(m.entityId()).isEqualTo("ofac-1");
            assertThat(m.datasets()).containsExactly("us_ofac_sdn");
            assertThat(m.sourceUrl()).isEqualTo("https://www.opensanctions.org/entities/ofac-1/");
        });
    }

    @Test
    void pepMatchNeedsReview() {
        server.expect(requestTo("https://api.test/match/default?limit=5&threshold=0.7"))
                .andRespond(withSuccess("""
                        {"responses":{"q":{"results":[
                          {"id":"pep-1","caption":"Maria Lopez","score":0.9,"match":true,
                           "datasets":["ec_asamblea"],"properties":{"topics":["role.pep"]}}
                        ]}}}
                        """, MediaType.APPLICATION_JSON));

        PersonScreeningResult result = service.screen(new PersonScreeningRequest("Maria Lopez", null, null, null));

        assertThat(result.riskLevel()).isEqualTo(RiskLevel.REVIEW);
    }

    @Test
    void noResultsIsClear() {
        server.expect(requestTo("https://api.test/match/default?limit=5&threshold=0.7"))
                .andRespond(withSuccess("{\"responses\":{\"q\":{\"results\":[]}}}", MediaType.APPLICATION_JSON));

        assertThat(service.screen(new PersonScreeningRequest("Nadie", null, null, null)).riskLevel())
                .isEqualTo(RiskLevel.CLEAR);
    }
}
