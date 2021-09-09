package rest_controllers;

import domain.Player;
import domain.Round;
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
        var context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        playerRepository = context.getBean(PlayerRepository.class);
    }

    @GetMapping(value = "/{idP}/games/{idG}/rounds")
    public ResponseEntity<?> getRounds(@PathVariable Long idP, @PathVariable Long idG) {
        Optional<Player> player = playerRepository.find(idP);
        if (player.isEmpty()) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        List<Round> rounds = player.get().getRounds().stream()
                .filter(round -> round.getGame().getId().equals(idG))
                .collect(Collectors.toList());
        return new ResponseEntity<>(rounds, HttpStatus.OK);
    }
}
