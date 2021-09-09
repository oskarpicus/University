package rest_controllers;

import domain.Game;
import domain.Player;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.GameRepository;
import repository.PlayerRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "games")
public class GameRestController {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    {
        var context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        gameRepository = context.getBean(GameRepository.class);
        playerRepository = context.getBean(PlayerRepository.class);
    }

    @GetMapping(value = "/{id}/ranking")
    public ResponseEntity<?> getRanking(@PathVariable Long id) {
        Optional<Game> game = gameRepository.find(id);
        if (game.isEmpty()) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        List<Long> playerId = gameRepository.getRanking(game.get());
        List<Player> players = playerId.stream()
                .map(idL -> playerRepository.find(idL).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
}
