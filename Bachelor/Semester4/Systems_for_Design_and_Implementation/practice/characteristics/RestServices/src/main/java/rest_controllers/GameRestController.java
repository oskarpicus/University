package rest_controllers;

import domain.Game;
import dtos.AnswerDTO;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.GameRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "games")
public class GameRestController {

    private final GameRepository gameRepository;

    {
        var context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        gameRepository = context.getBean(GameRepository.class);
    }

    @GetMapping(value = "/{id}/words")
    public ResponseEntity<?> getWordsByGame(@PathVariable Long id) {
        Optional<Game> game = gameRepository.find(id);
        if (game.isEmpty()) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        List<AnswerDTO> answerDTOS = game.get().getAnswers().stream()
                .map(answer -> new AnswerDTO(answer.getIndexRound(), answer.getWord().getName()))
                .distinct()
                .collect(Collectors.toList());
        return new ResponseEntity<>(answerDTOS, HttpStatus.OK);
    }

}
