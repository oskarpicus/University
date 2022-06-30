package model;

import java.util.Stack;

public class Configuration {
    private final Grammar grammar;
    private final String sequence;
    private State state;
    private int index;
    private final Stack<String> history = new Stack<>();
    private final Stack<String> input = new Stack<>();

    public Configuration(Grammar grammar, String sequence) {
        this.grammar = grammar;
        this.sequence = sequence;
        this.state = State.q;
        this.index = 1;
        this.input.push(grammar.getStartSymbol());
    }

    public void next() {
        if (canSucceed()) {
            succeed();
            System.out.println("succes");
        } else if (canExpand()) {
            expand();
            System.out.println("expandare");
        } else if (canAdvance()) {
            advance();
            System.out.println("avans");
        } else if (canPause()) {
            pause();
            System.out.println("insucces de moment");
        } else if (canReturn()) {
            doReturn();
            System.out.println("revenire");
        } else if (canTryAnotherRule()) {
            tryAnotherRule();
            System.out.println("alta incercare ramura 1");
        } else if (canTryMoveToError()) {
            moveToError();
            System.out.println("eroare");
        } else {
            undo();
            System.out.println("undo");
        }
    }

    private boolean canExpand() {
        if (input.isEmpty()) {
            return false;
        }

        String peak = this.input.peek();
        return grammar.isNonTerminal(peak) && state.equals(State.q);
    }

    private void expand() {
        String nonTerminal = input.pop();
        Rule rule = grammar.getNextRuleByLeft(nonTerminal, 0);

        history.push(nonTerminal + "1");
        pushElementsInInput(rule);
    }

    private boolean canAdvance() {
        if (input.isEmpty()) {
            return false;
        }

        String peak = this.input.peek();
        if (grammar.isNonTerminal(peak)) {
            return false;
        }

        if (index <= sequence.length()) {
            return String.valueOf(sequence.charAt(index - 1)).equals(peak) && state.equals(State.q);
        }
        return false;
    }

    private void advance() {
        index++;
        String terminal = input.pop();
        history.push(terminal);
    }

    private boolean canPause() {
        if (input.isEmpty() && index != sequence.length() + 1 && state.equals(State.q)) {
            return true;
        }
        if (state.equals(State.r)) {
            return false;
        }

        String peak = this.input.peek();
        if (grammar.isNonTerminal(peak)) {
            return false;
        }

        if (index <= sequence.length()) {
            return !String.valueOf(sequence.charAt(index - 1)).equals(peak) && state.equals(State.q);
        }

        return state.equals(State.q);
    }

    private void pause() {
        state = State.r;
    }

    private boolean canSucceed() {
        return state.equals(State.q) && index == sequence.length() + 1 && input.isEmpty();
    }

    private void succeed() {
        state = State.t;
    }

    private boolean canReturn() {
        return state.equals(State.r) && grammar.isTerminal(history.peek());
    }

    private void doReturn() {
        index--;
        String terminal = history.pop();
        input.push(terminal);
    }

    private boolean canTryAnotherRule() {
        if (input.isEmpty()) {
            return false;
        }

        String peek = history.peek();
        String[] elements = peek.split("");
        if (grammar.isTerminal(peek.substring(0, peek.length() - 1))) {
            return false;
        }

        int indexRule = Integer.parseInt(elements[elements.length - 1]);
        return state.equals(State.r) && grammar.getNextRuleByLeft(peek.substring(0, peek.length() - 1), indexRule) != null;
    }

    private void tryAnotherRule() {
        state = State.q;

        String last = history.pop();
        String nonTerminal = last.substring(0, last.length() - 1);
        String[] components = last.split("");
        int index = Integer.parseInt(components[components.length - 1]);
        int nextIndex = index + 1;

        history.push(nonTerminal + nextIndex);

        Rule ruleToUndo = grammar.getNextRuleByLeft(nonTerminal, index - 1);
        for (int i = 0; i < getNumberOfPops(ruleToUndo); i++) {
            input.pop();
        }

        Rule rule = grammar.getNextRuleByLeft(nonTerminal, index);
        pushElementsInInput(rule);
    }

    private boolean canTryMoveToError() {
        return input.isEmpty() || (state.equals(State.r) && index == 1 && grammar.getStartSymbol().equals(history.peek().substring(0, history.peek().length() - 1)) && history.size() == 1);
    }

    private void moveToError() {
        state = State.e;
        String last = history.pop();
        String nonTerminal = last.substring(0, last.length() - 1);
        String[] components = last.split("");
        Rule rule = grammar.getNextRuleByLeft(nonTerminal, Integer.parseInt(components[components.length - 1]) - 1);

        for (int i = 0; i < getNumberOfPops(rule); i++) {
            input.pop();
        }

        input.push(grammar.getStartSymbol());
    }

    private void undo() {
        String last = history.pop();
        String[] components = last.split("");
        String nonTerminal = last.substring(0, last.length() - 1);
        Rule rule = grammar.getNextRuleByLeft(nonTerminal, Integer.parseInt(components[components.length - 1]) - 1);

        for (int i = 0; i < getNumberOfPops(rule); i++) {
            input.pop();
        }

        input.push(nonTerminal);
    }

    public State getState() {
        return state;
    }

    public Stack<String> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "(" + state + "," + index + "," + history + "," + input + ")";
    }

    private int getNumberOfPops(Rule rule) {
        int result = 0;
        String[] words = rule.getRight().split(" ");
        for (String word : words) {
            if (grammar.isNonTerminal(word)) {
                result++;
            } else {
                result += word.length();
            }
        }

        result += words.length - 1;

        return result;
    }

    private void pushElementsInInput(Rule rule) {
        String[] words = rule.getRight().split(" ");
        for (int i = words.length - 1 ; i >= 0; i--) {
            if (grammar.isTerminal(words[i])) {
                byte[] bytes = new StringBuilder(words[i]).reverse().toString().getBytes();
                for (byte x : bytes) {
                    input.push(new String(new byte[]{x}));
                }
            } else {
                input.push(words[i]);
            }

            if (i != 0) {
                input.push(" ");
            }
        }
    }
}
