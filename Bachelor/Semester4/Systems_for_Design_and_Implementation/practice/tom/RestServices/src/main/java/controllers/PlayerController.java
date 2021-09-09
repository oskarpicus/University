package controllers;

import domain.Answer;
import domain.Player;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.PlayerRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "players")
public class PlayerController {

    private final PlayerRepository playerRepository;

    {
        playerRepository = new ClassPathXmlApplicationContext("classpath:Spring.xml").getBean(PlayerRepository.class);
    }

    @GetMapping(value = "/{idPlayer}/games/{idGame}/answers")
    public ResponseEntity<?> getAnswers(@PathVariable Long idPlayer, @PathVariable Long idGame) {
        Optional<Player> player = playerRepository.find(idPlayer);
        if (player.isEmpty()) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        Set<Answer> answers = player.get().getAnswers().stream()
                .filter(answer -> answer.getGame().getId().equals(idGame))
                .collect(Collectors.toSet());
        if (answers.isEmpty()) {
            return new ResponseEntity<>("This player did not participate in this game", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }
}
