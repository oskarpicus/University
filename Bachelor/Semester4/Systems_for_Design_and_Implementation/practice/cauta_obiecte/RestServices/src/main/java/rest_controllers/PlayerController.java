package rest_controllers;

import domain.Game;
import domain.Guess;
import domain.Player;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.GameRepository;
import repository.GuessRepository;
import repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "players")
public class PlayerController {

    private final GuessRepository guessRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    {
        var context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        guessRepository = context.getBean(GuessRepository.class);
        playerRepository = context.getBean(PlayerRepository.class);
        gameRepository = context.getBean(GameRepository.class);
    }

    @GetMapping(value = "/{idP}/games/{idG}/guesses")
    public ResponseEntity<?> getGuesses(@PathVariable Long idP, @PathVariable Long idG) {
        Optional<Player> player = playerRepository.find(idP);
        if (player.isEmpty()) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        Optional<Game> game = gameRepository.find(idG);
        if (game.isEmpty()) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        List<Guess> guesses = guessRepository.getGuessesByGamePlayer(game.get(), player.get());
        return new ResponseEntity<>(guesses, HttpStatus.OK);
    }
}
