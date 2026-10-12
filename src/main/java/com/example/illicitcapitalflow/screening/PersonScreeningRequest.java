package com.example.illicitcapitalflow.screening;

import jakarta.validation.constraints.NotBlank;

/**
 * Person to screen. Only the full name is required; birth date (YYYY or
 * YYYY-MM-DD), nationality (ISO country code, e.g. "ec") and ID number
 * (cédula / passport) improve match precision.
 */
public record PersonScreeningRequest(
        @NotBlank String fullName,
        String birthDate,
        String nationality,
        String idNumber) {
}
