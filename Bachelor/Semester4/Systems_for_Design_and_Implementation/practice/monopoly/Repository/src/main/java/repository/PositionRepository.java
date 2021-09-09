package repository;

import domain.Game;
import domain.Position;

public interface PositionRepository extends Repository<Long, Position> {
    Position getPositionByIndex(Game game, Integer index);
}
