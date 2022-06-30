package repository;

import domain.Spectacol;
import domain.Vanzare;

import java.util.List;

public interface VanzareRepository extends Repository<Integer, Vanzare> {
    Iterable<Vanzare> getBySpectacol(Spectacol spectacol);

    List<Integer> getLocuriVandute(Vanzare vanzare);
}
