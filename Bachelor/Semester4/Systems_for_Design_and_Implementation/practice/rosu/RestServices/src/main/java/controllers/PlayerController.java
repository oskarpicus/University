package controllers;

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

    @GetMapping(value = "/{idP}/games/{idG}/rounds")
    public ResponseEntity<?> getCards(@PathVariable Long idP, @PathVariable Long idG) {
        Optional<Player> player = playerRepository.find(idP);
        if (player.isEmpty()) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        Set<Round> rounds = player.get().getRounds().stream()
                .filter(round -> round.getGame().getId().equals(idG))
                .collect(Collectors.toSet());
        if (rounds.isEmpty()) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rounds, HttpStatus.OK);
    }
}
