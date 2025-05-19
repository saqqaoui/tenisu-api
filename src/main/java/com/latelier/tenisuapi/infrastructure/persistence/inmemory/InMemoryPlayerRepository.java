package com.latelier.tenisuapi.infrastructure.persistence.inmemory;

import com.latelier.tenisuapi.domain.model.Player;
import com.latelier.tenisuapi.domain.repository.PlayerRepository;
import com.latelier.tenisuapi.infrastructure.TennisPlayersLoader;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("!test")
public class InMemoryPlayerRepository implements PlayerRepository {

    private final TennisPlayersLoader loader;

    public InMemoryPlayerRepository(TennisPlayersLoader loader) {
        this.loader = loader;
    }

    @Override
    public List<Player> findAll() {
        return loader.findAll();
    }

    @Override
    public Optional<Player> findById(Integer id) {
        return loader.findAll()
                .stream()
                .filter(p -> p.id() == id)
                .findFirst();
    }
}
