package analysis;

import exceptions.AtomNotFoundException;
import model.Atom;
import model.AtomType;
import state_machine.StateMachine;
import utils.AtomFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzerFSM extends LexicalAnalyzer {
    private final StateMachine stateMachineId;
    private final StateMachine stateMachineIntegerConst;
    private final StateMachine stateMachineFloatConst;
    private final StateMachine stateMachineOperators;

    public LexicalAnalyzerFSM(StateMachine stateMachineId, StateMachine stateMachineIntegerConst, StateMachine stateMachineFloatConst, StateMachine stateMachineOperators) {
        this.stateMachineId = stateMachineId;
        this.stateMachineIntegerConst = stateMachineIntegerConst;
        this.stateMachineFloatConst = stateMachineFloatConst;
        this.stateMachineOperators = stateMachineOperators;
    }

    @Override
    protected List<Atom> detectAtoms(File file) throws FileNotFoundException, AtomNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        AtomFactory atomFactory = AtomFactory.getInstance();
        String content;
        try {
            content = Files.readString(file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String separators = atomFactory
                .getPossibleAtoms()
                .stream()
                .filter(atom -> atom.getType().equals(AtomType.SEPARATOR))
                .map(Atom::getToken)
                .reduce("", (x, y) -> x + y);

        String allSeparators = "[\r\t\n" + separators + "]";

        String regex = "((?=" + allSeparators + "))|((?<=" + allSeparators + "))";

        List<Atom> result = new ArrayList<>();
        while (!content.equals("")) {
            AtomType type = null;
            String prefix = stateMachineId.longestAcceptedPrefix(content);
            if (prefix == null) {  // was not an identifier
                prefix = stateMachineFloatConst.longestAcceptedPrefix(content);
                if (prefix == null) { // was not a float constant
                    prefix = stateMachineIntegerConst.longestAcceptedPrefix(content);
                    if (prefix == null) {  // was not an integer constant
                        prefix = stateMachineOperators.longestAcceptedPrefix(content);
                        if (prefix == null) {  // was not an operator
                            prefix = content.split(regex)[0];
                        }
                    } else {
                        type = AtomType.CONSTANT;
                    }
                } else {
                    type = AtomType.CONSTANT;
                }
            } else {
                type = AtomType.IDENTIFIER;
            }

            if (Character.isWhitespace(prefix.charAt(0))) {
                content = content.substring(prefix.length());
                continue;
            }

            Atom atom;
            if (type == null) {
                atom = AtomFactory.getInstance().detectAtom(prefix);
                if (atom == null) {
                    throw new AtomNotFoundException(prefix);
                }
            } else {
                switch (type) {
                    case CONSTANT -> atom = new Atom(prefix, type, AtomFactory.CONSTANT_CODE);
                    case IDENTIFIER -> atom = new Atom(prefix, type, AtomFactory.IDENTIFIER_CODE);
                    default -> throw new AtomNotFoundException(prefix);
                }
            }
            result.add(atom);
            content = content.substring(prefix.length());
        }
        return result;
    }
}
