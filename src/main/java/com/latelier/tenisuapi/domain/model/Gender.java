package com.latelier.tenisuapi.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Gender {
    M("Male"),
    F("Female");

    Gender(String label) {
        this.label = label;
    }

    private String label;
}