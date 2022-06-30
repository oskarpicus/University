package model;

import utils.LeftRecursiveGrammarException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Grammar {
    private final List<Rule> rules;

    public Grammar(List<Rule> rules) {
        this.rules = rules;
    }

    public String getStartSymbol() {
        return rules.get(0).getLeft();
    }

    public boolean isTerminal(String text) {
        return text.toLowerCase().equals(text);
    }

    public boolean isNonTerminal(String text) {
        return Character.isUpperCase(text.charAt(0)) && Character.isAlphabetic(text.charAt(0));
    }

    public Rule getNextRuleByLeft(String left, int index) {
        List<Rule> result = new ArrayList<>();
        for (Rule rule : rules) {
            if (rule.getLeft().equals(left)) {
                result.add(rule);
            }
        }

        try {
            return result.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public boolean isLeftRecursive() {
        return rules
                .stream()
                .anyMatch(rule -> rule.getLeft().equals(String.valueOf(rule.getRight().split(" ")[0])));
    }

    public List<Rule> check(String sequence) {
        if (isLeftRecursive()) {
            throw new LeftRecursiveGrammarException("Grammar is left recursive");
        }

        Configuration start = new Configuration(this, sequence);
        System.out.println(start);
        while (true) {
            start.next();
            System.out.println(start);
            if (start.getState().equals(State.e)) {
                return Collections.emptyList();
            }
            if (start.getState().equals(State.t)) {

                return start.getHistory()
                        .stream()
                        .filter(symbol -> !this.isTerminal(symbol))
                        .map(symbol -> {
                            String[] components = symbol.split("");
                            String nonTerminal = symbol.substring(0, symbol.length() - 1);
                            return this.getNextRuleByLeft(nonTerminal, Integer.parseInt(components[components.length - 1]) - 1);
                        })
                        .collect(Collectors.toList());
            }
        }
    }

    public List<Rule> getRules() {
        return rules;
    }
}
