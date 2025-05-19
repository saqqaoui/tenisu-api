package com.latelier.tenisuapi.presentation.response;


import com.latelier.tenisuapi.domain.model.CountryCode;

public record HeightMedianCountryResponse(CountryCode countryCode, String countryName, double medianHeight) {}
