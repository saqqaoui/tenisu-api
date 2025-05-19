package com.latelier.tenisuapi.domain.service;

import com.latelier.tenisuapi.domain.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsBmiService {

    private final PlayerService playerService;

    public StatsBmiService(PlayerService playerService) {
        this.playerService = playerService;
    }


    public double averageBmi() {
        List<Player> validPlayers = filterPlayersWithValidMetrics(playerService.findAllPlayers());
        List<Double> bmis = calculateBmis(validPlayers);
        return computeAverageBmi(bmis);
    }

    private List<Player> filterPlayersWithValidMetrics(List<Player> players) {
        return players.stream()
                .filter(this::hasValidHeightAndWeight)
                .toList();
    }

    private boolean hasValidHeightAndWeight(Player player) {
        return player.data().height() > 0 && player.data().weight() > 0;
    }

    private List<Double> calculateBmis(List<Player> players) {
        return players.stream()
                .map(this::calculateBmi)
                .toList();
    }

    private double calculateBmi(Player player) {
        double heightInMeters = player.data().height() / 100.0;
        return player.data().weight() / (heightInMeters * heightInMeters);
    }

    private double computeAverageBmi(List<Double> bmis) {
        if (bmis.isEmpty()) return 0;

        double average = bmis.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        return roundToThreeDecimals(average);
    }

    private static double roundToThreeDecimals(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }

}
