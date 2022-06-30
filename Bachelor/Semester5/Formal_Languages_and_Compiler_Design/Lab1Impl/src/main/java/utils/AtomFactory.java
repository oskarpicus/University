package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Atom;
import model.AtomType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;

public class AtomFactory {
    private static final AtomFactory instance = new AtomFactory();
    private List<Atom> atoms;
    private Pattern patternIdentifier;
    private Pattern patternConstant;

    private static final String ATOMS_JSON = "atoms.json";
    private static final String PATTERNS_PROPERTIES = "patterns.properties";

    public static int IDENTIFIER_CODE;
    public static int CONSTANT_CODE;

    private AtomFactory() {
        loadAtoms();
        loadPatterns();
    }

    public static AtomFactory getInstance() {
        return instance;
    }

    public Atom detectAtom(String token) {
        Optional<Atom> optionalAtom =  atoms.stream()
                .filter(atom -> atom.getToken().equals(token))
                .findFirst();
        if (optionalAtom.isPresent()) {
            return optionalAtom.get();
        }

        // check if it is an identifier or a constant via regex
        if (patternConstant.matcher(token).matches()) {
            return new Atom(token, AtomType.CONSTANT, IDENTIFIER_CODE);
        }
        if (patternIdentifier.matcher(token).matches()) {
            return new Atom(token, AtomType.IDENTIFIER, CONSTANT_CODE);
        }

        return null;
    }

    private void loadAtoms() {
        try {
            URL url = getClass().getClassLoader().getResource(ATOMS_JSON);
            atoms = new ObjectMapper().readValue(url, new TypeReference<>() {
            });
            IDENTIFIER_CODE = atoms.size();
            CONSTANT_CODE = IDENTIFIER_CODE + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPatterns() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(PATTERNS_PROPERTIES));
            patternIdentifier = Pattern.compile(properties.getProperty("identifier_pattern"));
            patternConstant = Pattern.compile(properties.getProperty("constant_pattern"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Atom> getPossibleAtoms() {
        return atoms;
    }
}
