package restcontrollers;

import domain.Game;
import domain.Plane;
import domain.Player;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.PlayerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "players")
public class PlayerController {
    private final PlayerRepository playerRepository;

    {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        playerRepository = context.getBean(PlayerRepository.class);
    }

    @GetMapping(value = "/{id}/games")
    public ResponseEntity<?> getGamesByPlayer(@PathVariable Long id) {
        Optional<Player> player = playerRepository.find(id);
        if (player.isEmpty()) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        List<Game> games = player.get().getPlanes().stream()
                .map(Plane::getGame)
                .collect(Collectors.toList());
        return new ResponseEntity<>(games, HttpStatus.OK);
    }
}
