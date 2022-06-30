package ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import state_machine.StateMachine;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class UIStateMachine {
    private StateMachine stateMachine;
    private final Scanner scanner;

    public UIStateMachine() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean ok = true;
        while (ok) {
            displayMenu();
            String option = scanner.next();
            try {
                switch (option) {
                    case "1" -> readStateMachineConsole();
                    case "2" -> readStateMachineFile();
                    case "3" -> displayStateSet();
                    case "4" -> displayAlphabet();
                    case "5" -> displayTransitions();
                    case "6" -> displayFinalStates();
                    case "7" -> displayAcceptance();
                    case "8" -> displayLongestAcceptedPrefix();
                    default -> ok = false;
                }
            } catch (JsonProcessingException e) {
                System.out.println("Invalid state machine format");
            } catch (IOException e) {
                System.out.println("Invalid file");
            } catch (NullPointerException e) {
                System.out.println("There is no valid state machine memorised");
            }
        }
    }

    private void displayMenu() {
        System.out.println("1 - Read State Machine from Console");
        System.out.println("2 - Read State Machine from File");
        System.out.println("3 - Display state set for the current State Machine");
        System.out.println("4 - Display alphabet for the current State Machine");
        System.out.println("5 - Display transitions for the current State Machine");
        System.out.println("6 - Display final states for the current State Machine");
        System.out.println("7 - Verify a sequence for the current State Machine");
        System.out.println("8 - Determine longest prefix in a sequence for the current State Machine");
        System.out.println("0 - Exit\n");
    }

    private void readStateMachineConsole() throws JsonProcessingException {
        System.out.println("Type the contents of the state machine");
        String input = scanner.next();
        stateMachine = StateMachine.from(input);
        System.out.println("Read State Machine successfully");
    }

    private void readStateMachineFile() throws IOException {
        System.out.println("Give the path to the file");
        File file = new File(scanner.next());
        stateMachine = StateMachine.from(file);
        System.out.println("Read State Machine successfully");
    }

    private void displayStateSet() throws NullPointerException {
        stateMachine.getStates().forEach(System.out::println);
    }

    private void displayAlphabet() throws NullPointerException {
        stateMachine.getAlphabet().forEach(System.out::println);
    }

    private void displayTransitions() throws NullPointerException {
        stateMachine.getTransitions()
                .stream()
                .map(transition -> transition.getFrom().getSymbol() + "->" + transition.getTo().getSymbol() + " (" + transition.getSymbol() + ")")
                .forEach(System.out::println);
    }

    private void displayFinalStates() throws NullPointerException {
        stateMachine.getFinalStates().forEach(System.out::println);
    }

    private void displayAcceptance() {
        System.out.println("Give the sequence");
        String sequence = scanner.next();
        boolean result = stateMachine.isAccepted(sequence);
        System.out.println(result ? "It is accepted" : "It is not accepted");
    }

    private void displayLongestAcceptedPrefix() {
        System.out.println("Give the sequence");
        String sequence = scanner.next();
        String result = stateMachine.longestAcceptedPrefix(sequence);
        System.out.println(result == null ? "Does not exist" : result);
    }
}
