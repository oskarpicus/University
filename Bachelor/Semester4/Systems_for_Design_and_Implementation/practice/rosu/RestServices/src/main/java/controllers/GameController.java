package controllers;

import domain.Game;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.GameRepository;

import java.util.Optional;

@RestController
@RequestMapping(value = "games")
public class GameController {

    private final GameRepository repository;

    {
        repository = new ClassPathXmlApplicationContext("classpath:Spring.xml").getBean(GameRepository.class);
    }

    @GetMapping(value = "/{id}/initialisations")
    public ResponseEntity<?> startCards(@PathVariable Long id) {
        Optional<Game> game = repository.find(id);
        if (game.isEmpty()) {
            return new ResponseEntity<>("Game does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(game.get().getInitialisations(), HttpStatus.OK);
    }

}
