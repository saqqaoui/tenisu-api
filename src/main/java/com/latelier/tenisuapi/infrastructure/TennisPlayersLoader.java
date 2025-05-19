package com.latelier.tenisuapi.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latelier.tenisuapi.domain.exception.NoPlayerFoundException;
import com.latelier.tenisuapi.domain.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TennisPlayersLoader implements ApplicationRunner {

    private final ObjectMapper mapper;
    private final Resource jsonResource;
    private List<Player> players = List.of();

    public TennisPlayersLoader(ObjectMapper mapper,
                               @Value("classpath:datasource/headtohead.json")
                               Resource jsonResource) {
        this.mapper = mapper;
        this.jsonResource = jsonResource;
    }

    /** Executed exactly once after the Spring context is created. */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        JsonNode root = mapper.readTree(jsonResource.getInputStream());
        JsonNode playersNode = root.get("players");

        /* ----------- STRUCTURAL VALIDATION (fail fast) ----------- */
        if (playersNode == null || !playersNode.isArray() || playersNode.isEmpty()) {
            throw new IllegalStateException("""
                    Invalid JSON structure: the 'players' array is missing or empty
                    → File: %s
                    """.formatted(jsonResource.getFilename()));
        }

        /* ----------- DATA CONVERSION ----------- */
        players = List.copyOf(
                JsonPlayerConverter.fromJsonPlayersToJava(jsonResource.getFile())
        );

        /* ----------- CONTENT VALIDATION (optional safety net) ----------- */
        if (players.isEmpty()) {
            log.error("""
                    JSON → Player conversion produced 0 players.
                    Please check the content of %s
                    """.formatted(jsonResource.getFilename()));

            throw new NoPlayerFoundException();
        }

        log.info("Loaded {} players from {}", players.size(), jsonResource.getFilename());
    }

    /** Read-only accessor used by repositories or services. */
    public List<Player> findAll() {
        return players;
    }
}
