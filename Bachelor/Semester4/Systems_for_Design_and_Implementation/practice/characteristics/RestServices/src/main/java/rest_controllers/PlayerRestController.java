package rest_controllers;

import domain.Answer;
import domain.Game;
import domain.Player;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.AnswerRepository;
import repository.GameRepository;
import repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "players")
public class PlayerRestController {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final AnswerRepository answerRepository;

    {
        var context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        playerRepository = context.getBean(PlayerRepository.class);
        gameRepository = context.getBean(GameRepository.class);
        answerRepository = context.getBean(AnswerRepository.class);
    }

    @GetMapping(value = "/{id}/games/{idG}/answers")
    public ResponseEntity<?> getAnswersByPlayer(@PathVariable Long id, @PathVariable Long idG) {
        Optional<Player> player = playerRepository.find(id);
        if (player.isEmpty()) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        Optional<Game> game = gameRepository.find(idG);
        if (game.isEmpty()) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        List<Answer> answers = answerRepository.getAnswersByGameAndPlayer(game.get(), player.get());
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

}
