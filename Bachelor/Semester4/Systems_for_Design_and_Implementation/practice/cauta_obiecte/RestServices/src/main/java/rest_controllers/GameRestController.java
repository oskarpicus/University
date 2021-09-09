package rest_controllers;

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
public class GameRestController {

    private final GameRepository gameRepository;

    {
        var context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        gameRepository = context.getBean(GameRepository.class);
    }

    @GetMapping(value = "/{id}/items")
    public ResponseEntity<?> getItemsByGame(@PathVariable Long id) {
        Optional<Game> game = gameRepository.find(id);
        if (game.isEmpty()) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(game.get().getItems(), HttpStatus.OK);
    }

}
