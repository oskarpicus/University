package utils;

import model.Grammar;
import model.Rule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GrammarFactory {
    public static Grammar get(File file) throws IOException {
        List<Rule> rules = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] components = line.split("\\$");
                rules.add(new Rule(components[0], components[1]));
            }

            return new Grammar(rules);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
