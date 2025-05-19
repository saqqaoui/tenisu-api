package com.latelier.tenisuapi.presentation.response;


import com.latelier.tenisuapi.domain.model.CountryRatio;

public record AllStatsResponse(CountryRatio bestCountry, double averageBMI, double medianHeight) {}
