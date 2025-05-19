package com.latelier.tenisuapi.domain.service;

import com.latelier.tenisuapi.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsBmiServiceTest {

    @Mock
    private PlayerService playerService;

    private StatsBmiService statsBmiService;

    @BeforeEach
    void setUp() {
        statsBmiService = new StatsBmiService(playerService);
    }

    @Test
    void returnsAverageBmiForValidPlayers() {

        Player p1 = mock(Player.class, RETURNS_DEEP_STUBS);
        when(p1.data().height()).thenReturn(180.0);
        when(p1.data().weight()).thenReturn(81.0);

        Player p2 = mock(Player.class, RETURNS_DEEP_STUBS);
        when(p2.data().height()).thenReturn(170.0);
        when(p2.data().weight()).thenReturn(68.0);

        when(playerService.findAllPlayers()).thenReturn(List.of(p1, p2));

        double bmiMean = statsBmiService.averageBmi();

        assertThat(bmiMean).isEqualTo(24.265);
        verify(playerService).findAllPlayers();
    }


    @Test
    void ignoresPlayersWithInvalidMetrics() {
        // Player ok
        Player valid = mock(Player.class, RETURNS_DEEP_STUBS);
        when(valid.data().height()).thenReturn(190.0);
        when(valid.data().weight()).thenReturn(80.0);

        // Player nok (weight absent)
        Player invalidWeight = mock(Player.class, RETURNS_DEEP_STUBS);
        when(invalidWeight.data().height()).thenReturn(185.0);
        when(invalidWeight.data().weight()).thenReturn(0.0);

        // Player nok (height absent)
        Player invalidHeight = mock(Player.class, RETURNS_DEEP_STUBS);
        when(invalidHeight.data().height()).thenReturn(-175.0);

        when(playerService.findAllPlayers()).thenReturn(List.of(valid, invalidWeight, invalidHeight));

        double bmiMean = statsBmiService.averageBmi();

        // Only valid player is considerated
        assertThat(bmiMean).isEqualTo(22.161);
    }

    @Nested
    class NoValidPlayers {

        @Test
        void returnsZeroForEmptyList() {
            when(playerService.findAllPlayers()).thenReturn(List.of());

            double bmiMean = statsBmiService.averageBmi();

            assertThat(bmiMean).isZero();
        }

        @Test
        void returnsZeroWhenAllPlayersInvalid() {
            Player invalid = mock(Player.class, RETURNS_DEEP_STUBS);
            when(invalid.data().height()).thenReturn(0.0);

            when(playerService.findAllPlayers()).thenReturn(List.of(invalid));

            double bmiMean = statsBmiService.averageBmi();

            assertThat(bmiMean).isZero();
        }
    }
}
