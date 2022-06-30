package state_machine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.State;
import model.StateType;
import model.Transition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class StateMachine {
    private Set<State> states;
    private Set<String> alphabet;
    private Set<Transition> transitions;

    public StateMachine() {
    }

    public Set<State> getStates() {
        return states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public Set<State> getFinalStates() {
        return states.stream()
                .filter(state -> state.getType().equals(StateType.FINAL))
                .collect(Collectors.toSet());
    }

    public static StateMachine from(String string) throws JsonProcessingException {
        return new ObjectMapper().readValue(string, StateMachine.class);
    }

    public static StateMachine from(File file) throws IOException {
        String content = new String(new FileInputStream(file).readAllBytes());
        return from(content);
    }

    public boolean isAccepted(String sequence) {
        // determine the initial state
        State currentState = states.stream()
                .filter(state -> state.getType().equals(StateType.INITIAL))
                .findFirst()
                .orElseThrow();

        String[] symbols = sequence.split("");
        Transition transition;

        for (String symbol : symbols) {
            State finalCurrentState = currentState;
            transition = transitions.stream()
                    .filter(t -> {
                        boolean fromBool = t.getFrom().equals(finalCurrentState); // &&
                        boolean symbolBool;
                        if (t.getSymbol() != null) {
                            symbolBool = symbol.equals(t.getSymbol());
                        } else {
                            symbolBool = t.getSymbols().contains(symbol);
                        }
                        return fromBool && symbolBool;
                    })
                    .findFirst()
                    .orElse(null);
            if (transition == null) {
                return false;
            }
            currentState = transition.getTo();
        }

        return currentState.getType().equals(StateType.FINAL);
    }

    public String longestAcceptedPrefix(String sequence) {
        // determine the initial state
        State currentState = states.stream()
                .filter(state -> state.getType().equals(StateType.INITIAL))
                .findFirst()
                .orElseThrow();

        StringBuilder result = new StringBuilder();
        StringBuilder intermediaryResult = new StringBuilder();
        String[] symbols = sequence.split("");
        Transition transition;
        boolean wentIntoFinalState = false;

        for (String symbol : symbols) {
            State finalCurrentState = currentState;
            transition = transitions.stream()
                    .filter(t -> {
                        boolean fromBool = t.getFrom().equals(finalCurrentState); // &&
                        boolean symbolBool;
                        if (t.getSymbol() != null) {
                            symbolBool = symbol.equals(t.getSymbol());
                        } else {
                            symbolBool = t.getSymbols().contains(symbol);
                        }
                        return fromBool && symbolBool;
                    })
                    .findFirst()
                    .orElse(null);
            if (transition == null) {
                break;
            }

            currentState = transition.getTo();
            intermediaryResult.append(symbol);
            if (currentState.getType().equals(StateType.FINAL)) {
                wentIntoFinalState = true;
                result.append(intermediaryResult);
                intermediaryResult.setLength(0);
            }
        }

        return wentIntoFinalState ? result.toString() : null;
    }
}
