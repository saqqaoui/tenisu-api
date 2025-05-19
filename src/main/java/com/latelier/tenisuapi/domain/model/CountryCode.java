package com.latelier.tenisuapi.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Country codes")
public enum CountryCode {
    SRB("Serbia"),
    USA("United States"),
    SUI("Switzerland"),
    ESP("Spain"),

    /**
     * Represents a player with no known or assigned country.
     * Used as a fallback when country information is missing or unrecognized.
     */
    @Schema(hidden = true)
    UNKNOWN("Unknown Country");

    CountryCode(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}