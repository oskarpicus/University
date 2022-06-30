package analysis;

import data_structures.hash.HashTable;
import data_structures.hash.HashTableSeparateChaining;
import exceptions.AtomNotFoundException;
import model.Atom;
import model.AtomType;
import utils.AtomFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LexicalAnalyzer {
    private List<Tuple> fip;
    private HashTable<Atom> ts;

    private static final int DEFAULT_FIP_CODE = -1;

    public void analyse(File file) throws FileNotFoundException, AtomNotFoundException {
        List<Atom> atoms = detectAtoms(file);
        fip = new ArrayList<>();
        ts = new HashTableSeparateChaining<>();

        for (Atom atom : atoms) {
            if (!atom.getType().equals(AtomType.IDENTIFIER) && !atom.getType().equals(AtomType.CONSTANT)) {
                fip.add(new Tuple(atom, DEFAULT_FIP_CODE));
            } else {
                int index = ts.find(atom);
                if (index == -1) {  // the atom does not exist in TS
                    index = ts.add(atom);
                }
                fip.add(new Tuple(atom, index));
            }
        }
    }

    protected List<Atom> detectAtoms(File file) throws FileNotFoundException, AtomNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        AtomFactory atomFactory = AtomFactory.getInstance();
        String separators = atomFactory
                .getPossibleAtoms()
                .stream()
                .filter(atom -> atom.getType().equals(AtomType.SEPARATOR))
                .map(Atom::getToken)
                .reduce("", (x, y) -> x + y);

        List<String> otherSeparators = Arrays.asList("\r", "\t", "\n", "", " ");
        String allSeparators = "[\r\t\n" + separators + "]";

        String regex = "((?=" + allSeparators + "))|((?<=" + allSeparators + "))";

        try {
            String content = Files.readString(file.toPath());
            String[] atoms = content.split(regex);
            return Arrays.stream(atoms)
                    .filter(s -> !otherSeparators.contains(s))
                    .map(token -> {
                        Atom atom = atomFactory.detectAtom(token);
                        if (atom == null) {
                            throw new AtomNotFoundException(token);
                        }
                        return atom;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tuple> getFip() {
        return fip;
    }

    public HashTable<Atom> getTs() {
        return ts;
    }
}
