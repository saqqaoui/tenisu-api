package com.latelier.tenisuapi.stub;

import com.latelier.tenisuapi.domain.model.Player;
import com.latelier.tenisuapi.domain.repository.PlayerRepository;
import com.latelier.tenisuapi.infrastructure.JsonPlayerConverter;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Profile("test")
@Repository
public class StubPlayerRepository implements PlayerRepository {

    private List<Player> players;

    public StubPlayerRepository() throws IOException {
        load();
    }

    private List<Player> load() throws IOException {

        players = List.copyOf(
                JsonPlayerConverter.fromJsonPlayersToJava(new ClassPathResource("players-test.json").getFile())
        );
        return players;
    }
    @Override
    public List<Player> findAll() {
        return players;
    }

    @Override
    public Optional<Player> findById(Integer id) {
        return players.stream()
                .filter(p -> p.id() == id)
                .findFirst();
    }
}
