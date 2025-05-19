package com.latelier.tenisuapi.domain.service;

import com.latelier.tenisuapi.domain.model.CountryCode;
import com.latelier.tenisuapi.domain.model.CountryRatio;
import com.latelier.tenisuapi.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsWinRatioServiceTest {

    @Mock
    private PlayerService playerService;

    private StatsWinRatioService service;

    @BeforeEach
    void setUp() {
        service = new StatsWinRatioService(playerService);
    }

    @Test
    void winRatioComputesCorrectly() {
        Player p1 = mockPlayer(List.of(1, 1, 0, 0));
        Player p2 = mockPlayer(List.of(0, 0, 0, 0));
        double ratio = service.winRatio(List.of(p1, p2));
        assertThat(ratio).isEqualTo(0.25);
    }

    @Nested
    class BestCountryRatio {

        @Test
        void returnsCountryWithHighestRatio() {
            CountryCode srb = CountryCode.valueOf("SRB");
            CountryCode usa = CountryCode.valueOf("USA");

            List<Player> srbPlayers = List.of(
                    mockPlayer(List.of(1, 1, 0, 1)),
                    mockPlayer(List.of(1, 1, 1, 1))
            );

            List<Player> usaPlayers = List.of(
                    mockPlayer(List.of(0, 1, 0, 0)),
                    mockPlayer(List.of(0, 0, 1, 0))
            );

            Map<CountryCode, List<Player>> grouped = new EnumMap<>(CountryCode.class);
            grouped.put(srb, srbPlayers);
            grouped.put(usa, usaPlayers);

            when(playerService.getAllPlayersGroupedByCountryCode()).thenReturn(grouped);

            CountryRatio result = service.bestCountryRatio();

            assertThat(result.countryCode()).isEqualTo(srb);

            verify(playerService).getAllPlayersGroupedByCountryCode();
        }

        @Test
        void returnsDefaultWhenNoData() {
            when(playerService.getAllPlayersGroupedByCountryCode()).thenReturn(Map.of());
            CountryRatio result = service.bestCountryRatio();
            assertThat(result.countryCode()).isNull();
            assertThat(result.countryName()).isEmpty();
            assertThat(result.winRatio()).isZero();
        }
    }

    private Player mockPlayer(List<Integer> results) {
        if (results.size() < 4) throw new IllegalArgumentException();
        Player p = mock(Player.class, RETURNS_DEEP_STUBS);
        when(p.data().last()).thenReturn(results);
        return p;
    }
}
