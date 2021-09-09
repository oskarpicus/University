package controllers;

import domain.Game;
import domain.Player;
import dtos.GameDTO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.GameRepository;
import repository.PlayerRepository;

import java.util.Optional;

@RestController
@RequestMapping(value = "games")
public class GameController {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    {
        var context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        gameRepository = context.getBean(GameRepository.class);
        playerRepository = context.getBean(PlayerRepository.class);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getGame(@PathVariable Long id) {
        Optional<Game> game = gameRepository.find(id);
        if (game.isEmpty()) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        Long winnerId = gameRepository.findWinner(game.get());
        Optional<Player> player = playerRepository.find(winnerId);
        if (player.isEmpty()) {
            return new ResponseEntity<>("Winner not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new GameDTO(game.get(), player.get()), HttpStatus.OK);
    }
}
