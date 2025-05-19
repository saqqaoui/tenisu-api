package com.latelier.tenisuapi.presentation;

import com.latelier.tenisuapi.domain.model.CountryCode;
import com.latelier.tenisuapi.domain.model.CountryRatio;
import com.latelier.tenisuapi.domain.service.StatsBmiService;
import com.latelier.tenisuapi.domain.service.StatsHeightService;
import com.latelier.tenisuapi.domain.service.StatsWinRatioService;
import com.latelier.tenisuapi.presentation.response.AllStatsResponse;
import com.latelier.tenisuapi.presentation.response.HeightMedianCountryResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
class StatsController {

    private final StatsHeightService statsHeightService;
    private final StatsWinRatioService statsWinRatioService;
    private final StatsBmiService statsBmiService;

    StatsController(StatsHeightService statsHeightService, StatsWinRatioService statsWinRatioService, StatsBmiService statsBmiService) {
        this.statsHeightService = statsHeightService;
        this.statsWinRatioService = statsWinRatioService;
        this.statsBmiService = statsBmiService;
    }

    @GetMapping
    public AllStatsResponse allStats() {

        CountryRatio bestCountry = statsWinRatioService.bestCountryRatio();

        double averageBmi = statsBmiService.averageBmi();

        double medianHeight = statsHeightService.medianHeightAllCountries();

        return new AllStatsResponse(bestCountry, averageBmi, medianHeight);
    }

    @GetMapping("/median-height/{countryCode}")
    public HeightMedianCountryResponse medianHeightByCountry(
            @Parameter(
                    name = "countryCode",
                    in = ParameterIn.PATH,
                    description = "Country codes",
                    required = true,
                    schema = @Schema(type = "string",
                            allowableValues = {"SRB","USA","SUI","ESP"}))
            @PathVariable CountryCode countryCode) {
        double medianHeight = statsHeightService.medianHeightByCountry(countryCode);
        return new HeightMedianCountryResponse(countryCode, countryCode.getLabel(), medianHeight);
    }
}

