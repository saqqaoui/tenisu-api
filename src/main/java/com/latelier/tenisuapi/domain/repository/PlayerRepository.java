package com.latelier.tenisuapi.domain.repository;

import com.latelier.tenisuapi.domain.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    List<Player> findAll();
    Optional<Player> findById(Integer id);
}