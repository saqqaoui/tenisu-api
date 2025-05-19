package com.latelier.tenisuapi.domain.service;

import com.latelier.tenisuapi.domain.model.CountryCode;
import com.latelier.tenisuapi.domain.model.CountryRatio;
import com.latelier.tenisuapi.domain.model.Player;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatsWinRatioService {

    private final PlayerService playerService;

    public StatsWinRatioService(PlayerService playerService) {
        this.playerService = playerService;
    }
    public CountryRatio bestCountryRatio() {

        // Load all countries data
        Map<CountryCode, List<Player>> playersByCountry = playerService.getAllPlayersGroupedByCountryCode();

        // Init a map to contain ratio by country code
        Map<CountryCode, Double> allCountriesRatio = new EnumMap<>(CountryCode.class);

        for (CountryCode key : playersByCountry.keySet()) {
            allCountriesRatio.put(key, winRatio(playersByCountry.get(key)));
        }

        return getMaxCountryRatio(allCountriesRatio);
    }

    private static CountryRatio getMaxCountryRatio(Map<CountryCode, Double> ratioByCountry) {
        return ratioByCountry.entrySet().stream()
                .map(e -> buildCountryRatio(e.getKey(), e.getValue()))
                .max(Comparator.comparingDouble(CountryRatio::winRatio))
                // no throwing exception here because considering that "no data is available"
                .orElse(new CountryRatio(null, "", 0));
    }

    private static CountryRatio buildCountryRatio(CountryCode code, Double ratio) {
        return new CountryRatio(code,
                code.getLabel(),
                ratio);
    }

    public double winRatio(List<Player> players) {
        long wins = players.stream()
                .flatMap(p -> p.data().last().stream())
                .filter(v -> v == 1)
                .count();

        long losses = players.stream()
                .flatMap(p -> p.data().last().stream())
                .filter(v -> v == 0)
                .count();

        long total = wins + losses;
        return total == 0 ? 0d : (double) wins / total;
    }

}
