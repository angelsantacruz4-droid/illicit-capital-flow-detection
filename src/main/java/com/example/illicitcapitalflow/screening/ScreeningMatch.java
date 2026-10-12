package com.example.illicitcapitalflow.screening;

import java.util.List;

/**
 * A candidate entity from an open-source list that resembles the screened person.
 * {@code topics} uses OpenSanctions tags such as "sanction", "role.pep" or "crime".
 */
public record ScreeningMatch(
        String entityId,
        String name,
        double score,
        boolean strongMatch,
        List<String> topics,
        List<String> countries,
        List<String> datasets,
        String sourceUrl) {
}
