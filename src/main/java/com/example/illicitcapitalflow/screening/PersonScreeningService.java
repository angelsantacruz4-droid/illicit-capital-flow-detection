package com.example.illicitcapitalflow.screening;

import java.time.LocalDateTime;
import java.util.List;

import com.example.illicitcapitalflow.screening.PersonScreeningResult.RiskLevel;
import org.springframework.stereotype.Service;

@Service
public class PersonScreeningService {

    private static final List<String> HIGH_RISK_TOPICS = List.of("sanction", "crime", "wanted", "debarment");

    private final OpenSanctionsClient client;

    public PersonScreeningService(OpenSanctionsClient client) {
        this.client = client;
    }

    public PersonScreeningResult screen(PersonScreeningRequest person) {
        List<ScreeningMatch> matches = client.match(person);
        return new PersonScreeningResult(person, classify(matches), matches, LocalDateTime.now());
    }

    static RiskLevel classify(List<ScreeningMatch> matches) {
        if (matches.isEmpty()) {
            return RiskLevel.CLEAR;
        }
        boolean high = matches.stream().anyMatch(m -> m.strongMatch()
                && m.topics().stream().anyMatch(t -> HIGH_RISK_TOPICS.stream().anyMatch(t::startsWith)));
        return high ? RiskLevel.HIGH : RiskLevel.REVIEW;
    }
}
