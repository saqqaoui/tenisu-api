package com.latelier.tenisuapi.domain.service;


import com.latelier.tenisuapi.domain.model.CountryCode;
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
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StatsHeightServiceTest {

    @Mock
    private PlayerService playerService;

    private StatsHeightService statsHeightService;

    @BeforeEach
    void setUp() {
        statsHeightService = new StatsHeightService(playerService);
    }

    @Nested
    class MedianHeight {

        @Test
        void oddSizedList() {
            double median = statsHeightService.medianHeight(List.of(190.0, 180.0, 200.0));

            assertThat(median).isEqualTo(190.0);
        }

        @Test
        void evenSizedList() {
            double median = statsHeightService.medianHeight(List.of(175.0, 185.0, 165.0, 195.0));

            assertThat(median).isEqualTo(180.0);
        }

        @Test
        void ignoresNonPositiveValues() {
            double median = statsHeightService.medianHeight(List.of(0.0, -10.0, 180.0, 190.0, 200.0));

            assertThat(median).isEqualTo(190.0);
        }

        @Test
        void emptyListReturnsZero() {
            double median = statsHeightService.medianHeight(List.of());

            assertThat(median).isZero();
        }
    }


    /************ Testing with PlayerService Mocking calls *********/

    @Test
    void medianHeightAllCountries() {
        Player p1 = mock(Player.class, RETURNS_DEEP_STUBS);
        Player p2 = mock(Player.class, RETURNS_DEEP_STUBS);
        Player p3 = mock(Player.class, RETURNS_DEEP_STUBS);

        when(p1.data().height()).thenReturn(185.0);
        when(p2.data().height()).thenReturn(195.0);
        when(p3.data().height()).thenReturn(175.0);

        when(playerService.findAllPlayers()).thenReturn(List.of(p1, p2, p3));

        double median = statsHeightService.medianHeightAllCountries();

        assertThat(median).isEqualTo(185.0);
        verify(playerService).findAllPlayers();
    }

    @Test
    void medianHeightByCountry() {
        CountryCode srb = CountryCode.valueOf("SRB");

        Player p1 = mock(Player.class, RETURNS_DEEP_STUBS);
        Player p2 = mock(Player.class, RETURNS_DEEP_STUBS);

        when(p1.data().height()).thenReturn(180.0);
        when(p2.data().height()).thenReturn(200.0);

        Map<CountryCode, List<Player>> map = new EnumMap<>(CountryCode.class);
        map.put(srb, List.of(p1, p2));

        when(playerService.getAllPlayersGroupedByCountryCode()).thenReturn(map);

        double median = statsHeightService.medianHeightByCountry(srb);

        assertThat(median).isEqualTo(190.0);
        verify(playerService).getAllPlayersGroupedByCountryCode();
    }
}
