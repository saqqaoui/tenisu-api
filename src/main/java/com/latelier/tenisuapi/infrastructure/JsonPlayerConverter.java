package com.latelier.tenisuapi.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latelier.tenisuapi.domain.model.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonPlayerConverter {

    private JsonPlayerConverter() {}

    public static List<Player> fromJsonPlayersToJava(File file) {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root;
        try {
            root = mapper.readTree(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Player> players = new ArrayList<>();
        for (JsonNode node : root.get("players")) {
            Player p = fromJsonPlayerToJava(mapper, node);
            players.add(p);
        }
        return players;
    }

    private static Player fromJsonPlayerToJava(ObjectMapper mapper, JsonNode node) {
        Player p;
        try {
            p = mapper.treeToValue(node, Player.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
}
