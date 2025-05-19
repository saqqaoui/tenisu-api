package com.latelier.tenisuapi.domain.service;

import com.latelier.tenisuapi.domain.exception.NoPlayerFoundException;
import com.latelier.tenisuapi.domain.model.CountryCode;
import com.latelier.tenisuapi.domain.model.Gender;
import com.latelier.tenisuapi.domain.model.Player;
import com.latelier.tenisuapi.domain.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAllPlayers() {
        List<Player> allPlayers = playerRepository.findAll();
        if (allPlayers.isEmpty()) {
            throw new NoPlayerFoundException();
        }
        return new ArrayList<>(allPlayers);
    }

    public List<Player> getAllSortedByRankAsc() {
        List<Player> players = findAllPlayers();

        // The rule is : Order by ascending rank,
        // if two players have the same rank,
        // put the one with the most points first.
        final Comparator<Player> playersOrder =
                Comparator.comparingInt((Player p) -> p.data().rank())
                        .thenComparing(Comparator.comparingInt((Player p) -> p.data().points())
                                .reversed());

        players.sort(playersOrder);
        return players;
    }

    public List<Player> getAllByGender(Gender gender) {
        List<Player> playersByGender = findAllPlayers().stream().filter(p -> p.sex() == gender).toList();
        if (playersByGender.isEmpty()) throw new NoPlayerFoundException();
        return playersByGender;
    }

    public Player getOneById(Integer id) {
        return playerRepository.findById(id).orElseThrow(() -> new NoPlayerFoundException(id));
    }

    public Map<CountryCode, List<Player>> getAllPlayersGroupedByCountryCode() {
        return findAllPlayers().stream()
                .filter(p -> p.country() != null)
                .collect(Collectors.groupingBy(
                        p -> p.country().code(),
                        () -> new EnumMap<>(CountryCode.class),
                        Collectors.toList()
                ));
    }

}
