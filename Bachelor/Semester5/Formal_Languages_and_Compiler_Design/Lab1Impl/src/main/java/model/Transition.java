package model;

import java.util.Set;

public class Transition {
    private State from;
    private State to;
    private String symbol;
    private Set<String> symbols;

    public Transition() {
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "from=" + from +
                ", to=" + to +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    public Set<String> getSymbols() {
        return symbols;
    }
}
