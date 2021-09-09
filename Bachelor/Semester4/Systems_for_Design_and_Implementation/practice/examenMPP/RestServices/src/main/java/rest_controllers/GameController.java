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
@RequestMapping(value = "games")
public class GameController {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final GuessRepository guessRepository;

    {
        var context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        gameRepository = context.getBean(GameRepository.class);
        playerRepository = context.getBean(PlayerRepository.class);
        guessRepository = context.getBean(GuessRepository.class);
    }

    @GetMapping(value = "/{id}/words")
    public ResponseEntity<?> getWordsByGame(@PathVariable Long id) {
        Optional<Game> game = gameRepository.find(id);
        if (game.isEmpty()) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(game.get().getWords(), HttpStatus.OK);
    }

    @GetMapping(value = "/{idG}/players/{idP}/guesses")
    public ResponseEntity<?> getGuessesByPlayer(@PathVariable Long idG, @PathVariable Long idP) {
        Optional<Game> game = gameRepository.find(idG);
        if (game.isEmpty()) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        Optional<Player> player = playerRepository.find(idP);
        if (player.isEmpty()) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        List<Guess> guesses = guessRepository.getGuessesByPlayerGame(player.get(), game.get());
        return new ResponseEntity<>(guesses, HttpStatus.OK);
    }
}
