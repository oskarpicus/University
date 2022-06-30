package model;

import java.util.Objects;

public class State {
    private String symbol;
    private StateType type;

    public State() {

    }

    public String getSymbol() {
        return symbol;
    }

    public StateType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "State{" +
                "symbol='" + symbol + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(symbol, state.symbol) && type == state.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, type);
    }
}
