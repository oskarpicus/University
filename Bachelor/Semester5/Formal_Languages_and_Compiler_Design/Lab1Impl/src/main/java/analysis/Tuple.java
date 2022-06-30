package analysis;

import model.Atom;

public class Tuple {
    private final Atom atom;
    private final int tsCode;

    Tuple(Atom atom, int tsCode) {
        this.atom = atom;
        this.tsCode = tsCode;
    }

    public Atom getAtom() {
        return atom;
    }

    public int getTsCode() {
        return tsCode;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "atom=" + atom +
                ", tsCode=" + tsCode +
                '}';
    }
}
