package ui;

import model.Grammar;
import model.Program;
import utils.GrammarFactory;
import utils.LeftRecursiveGrammarException;
import utils.ProgramFactory;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class UI {
    private Grammar currentGrammar;
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("1 - Load grammar");
            System.out.println("2 - Check sequence");
            System.out.println("3 - Check program");
            System.out.println("4 - Print grammar");
            System.out.println("0 - Exit");
            String command = scanner.nextLine();

            if (command.equals("1")) {
                loadGrammar();
            } else if (command.equals("2")) {
                checkSequence();
            } else if (command.equals("3")) {
                checkProgram();
            } else if (command.equals("4")) {
                printGrammar();
            } if (command.equals("0")) {
                break;
            }
        }
    }

    private void loadGrammar() {
        System.out.println("Give the path to the file");
        String file = scanner.nextLine();
        try {
            currentGrammar = GrammarFactory.get(new File(file));
            System.out.println("Grammar loaded successfully");
        } catch (IOException e) {
            System.out.println("Invalid file");
        }
    }

    private void checkSequence() {
        System.out.println("Give the sequence");
        String sequence = scanner.nextLine();
        try {
            var result = currentGrammar.check(sequence);
            if (result.isEmpty()) {
                System.out.println("Sequence not accepted");
            } else {
                result.forEach(System.out::println);
            }
        } catch (LeftRecursiveGrammarException e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkProgram() {
        System.out.println("Give path to program");
        String path = scanner.nextLine();
        try {
            Program program = ProgramFactory.get(new File(path));
            String programString = program.toString();
            System.out.println("Loaded: " + programString);
            var result = currentGrammar.check(programString);

            if (result.isEmpty()) {
                System.out.println("Sequence not accepted");
            } else {
                result.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.out.println("Invalid file");
        }
    }

    private void printGrammar() {
        currentGrammar.getRules().forEach(System.out::println);
    }
}
