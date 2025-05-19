package com.latelier.tenisuapi.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
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

import java.io.InputStream;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Player> loaded;
        try (InputStream in = jsonResource.getInputStream()) {

            JsonNode root        = mapper.readTree(in);
            JsonNode playersNode = root.path("players");

            /* ----------- VALIDATION STRUCTURE ----------- */
            if (!playersNode.isArray() || playersNode.isEmpty()) {
                throw new IllegalStateException(
                        "Invalid JSON: array 'players' is missing or empty in %s"
                                .formatted(jsonResource.getFilename()));
            }

            /* ----------- CONVERSION ----------- */
            loaded = mapper.convertValue(
                    playersNode,
                    new TypeReference<List<Player>>() {}
            );
        }

        /* ----------- VALIDATION CONTENU ----------- */
        if (loaded.isEmpty()) {
            log.error("JSON → Player conversion produced 0 players (file: {})",
                    jsonResource.getFilename());
            throw new NoPlayerFoundException();
        }

        players = List.copyOf(loaded);        // copie immuable en mémoire
        log.info("Loaded {} players from {}", players.size(), jsonResource.getFilename());
    }

    /** Read-only accessor used by repositories or services. */
    public List<Player> findAll() {
        return players;
    }
}
