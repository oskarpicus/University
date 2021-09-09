package controllers;

import domain.Game;
import domain.Player;
import dtos.GameDto;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.GameRepository;
import repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "players")
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        playerRepository = context.getBean(PlayerRepository.class);
        gameRepository = context.getBean(GameRepository.class);
    }

    @GetMapping(value = "/{id}/games")
    public ResponseEntity<?> getGames(@PathVariable Long id) {
        Optional<Player> player = playerRepository.find(id);
        if (player.isEmpty()) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        List<Game> games = gameRepository.getGamesByPlayer(player.get());
        List<GameDto> gameDtos = new ArrayList<>();
        games.forEach(game -> {
            Long idWinner = gameRepository.getWinner(game);
            Optional<Player> winner = playerRepository.find(idWinner);
            gameDtos.add(new GameDto(game, winner.orElse(null)));
        });
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }

}
