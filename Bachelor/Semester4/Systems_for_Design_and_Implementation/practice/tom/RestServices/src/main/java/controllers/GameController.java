package controllers;

import domain.Player;
import dtos.GameRanking;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.GameRepository;
import repository.PlayerRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping(value = "games")
public class GameController {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    {
        var context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        gameRepository = context.getBean(GameRepository.class);
        playerRepository = context.getBean(PlayerRepository.class);
    }

    @GetMapping(value = "/{id}/ranking")
    public ResponseEntity<?> getRanking(@PathVariable Long id) {
        List<Object[]> results = gameRepository.getRanking(id);
        List<GameRanking> gameRankings = new ArrayList<>();
        for (Object[] x : results) {
            Long playerId = ((BigInteger)x[0]).longValue();
            int points = ((BigDecimal)x[1]).intValue();
            Optional<Player> player = playerRepository.find(playerId);
            player.ifPresent(value -> gameRankings.add(new GameRanking(value, points)));
        }
        return new ResponseEntity<>(gameRankings, HttpStatus.OK);
    }

}
