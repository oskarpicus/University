package repository;

import domain.Spectacol;

import java.util.List;

public interface SpectacolRepository extends Repository<Integer, Spectacol> {
    List<Integer> getLocuriVandute(Spectacol spectacol);
}
