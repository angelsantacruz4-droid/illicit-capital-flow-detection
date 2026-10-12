package com.example.illicitcapitalflow.screening;

import java.time.LocalDateTime;
import java.util.List;

public record PersonScreeningResult(
        PersonScreeningRequest query,
        RiskLevel riskLevel,
        List<ScreeningMatch> matches,
        LocalDateTime screenedAt) {

    public enum RiskLevel {
        /** No candidate above the configured threshold. */
        CLEAR,
        /** Candidates found that need analyst review (PEPs, partial matches). */
        REVIEW,
        /** Strong match against a sanctions or crime list. */
        HIGH
    }
}
