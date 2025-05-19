package com.latelier.tenisuapi.domain.service;

import com.latelier.tenisuapi.domain.model.CountryCode;
import com.latelier.tenisuapi.domain.model.CountryRatio;
import com.latelier.tenisuapi.domain.model.Player;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsHeightService {

    private final PlayerService playerService;

    public StatsHeightService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public double medianHeightAllCountries() {
        List<Double> allPlayersHeightList = playerService.findAllPlayers().stream().map(p -> p.data().height()).toList();
        return medianHeight(allPlayersHeightList);
    }

    public double medianHeightByCountry(CountryCode code) {
        List<Double> allPlayersHeightByCountryCode = playerService.getAllPlayersGroupedByCountryCode().get(code)
                .stream().map(p -> p.data().height()).toList();
        return medianHeight(allPlayersHeightByCountryCode);
    }

    /**
     * Calculates the median of a list of numerical (Double) values.
     *
     * <p>The median is a measure of central tendency that represents the middle value
     * in a sorted distribution. It divides the data into two equal halves: 50% of the values
     * are less than or equal to the median, and 50% are greater than or equal.</p>
     *
     * <p>The calculation works as follows:
     * <ul>
     *   <li>If the list has an odd number of elements, the median is the central value.</li>
     *   <li>If the list has an even number of elements, the median is the average of the two middle values.</li>
     * </ul>
     * </p>
     *
     * <p>Unlike the arithmetic mean, the median is resistant to extreme values (outliers)
     * and provides a more accurate picture of the "typical" data point in skewed distributions.</p>
     *
     * <p>Examples:
     * <ul>
     *   <li>Odd list: [1.2, 1.4, 1.6] → median = 1.4</li>
     *   <li>Even list: [1.2, 1.4, 1.6, 1.8] → median = (1.4 + 1.6)/2 = 1.5</li>
     * </ul>
     * </p>
     *
     * @param heights A list of numeric values to analyze (must not be empty)
     * @return The median, rounded to one decimal place, zero if the list is empty
     */
    public double medianHeight(List<Double> heights) {
        if (heights.isEmpty()) return 0;

        //the median, all non-positive values (zero or negative)
        //are filtered out. This ensures the validity and accuracy of the result

        List<Double> sortedHeights = heights.stream().filter(h -> h > 0).sorted().toList();

        int sortedHeightsListSize = sortedHeights.size();

        return (sortedHeightsListSize % 2 == 1)
                ? sortedHeights.get(sortedHeightsListSize / 2)
                : (sortedHeights.get(sortedHeightsListSize / 2 - 1)
                + sortedHeights.get(sortedHeightsListSize / 2)) / 2.0;
    }
}
