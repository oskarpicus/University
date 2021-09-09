package service;

import domain.Card;
import repository.CardRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CardService {
    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> findAll() {
        Iterable<Card> cards = repository.findAll();
        return StreamSupport.stream(cards.spliterator(), false).collect(Collectors.toList());
    }
}
