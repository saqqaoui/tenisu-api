package com.latelier.tenisuapi.presentation;

import com.latelier.tenisuapi.domain.model.Gender;
import com.latelier.tenisuapi.domain.model.Player;
import com.latelier.tenisuapi.domain.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tennis-player")
public class TennisPlayerController {
    private final PlayerService playerService;

    public TennisPlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/all")
    public List<Player> getAllPlayers() {
        return playerService.getAllSortedByRankAsc();
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable int id) {
        return playerService.getOneById(id);
    }
    @GetMapping("/all-by-gender")
    public List<Player> getPlayersByGender(@RequestParam Gender gender) {
        return playerService.getAllByGender(gender);
    }

}
