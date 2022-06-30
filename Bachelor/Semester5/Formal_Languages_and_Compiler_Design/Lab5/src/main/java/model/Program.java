package model;

import java.util.List;
import java.util.Optional;

public class Program {
    private List<Atom> atoms;
    private List<TsTuple> ts;
    private List<FipTuple> fip;

    public Program() {
    }

    public List<Atom> getAtoms() {
        return atoms;
    }

    public void setAtoms(List<Atom> atoms) {
        this.atoms = atoms;
    }

    public List<TsTuple> getTs() {
        return ts;
    }

    public void setTs(List<TsTuple> ts) {
        this.ts = ts;
    }

    public List<FipTuple> getFip() {
        return fip;
    }

    public void setFip(List<FipTuple> fip) {
        this.fip = fip;
    }

    private static class TsTuple {
        private int code;
        private String token;

        public TsTuple() {
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "TsTuple{" +
                    "code=" + code +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    private static class FipTuple {
        private int code;
        private int tsCode;
        public static final int INVALID_TS_CODE = -1;

        public FipTuple() {
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getTsCode() {
            return tsCode;
        }

        public void setTsCode(int tsCode) {
            this.tsCode = tsCode;
        }

        @Override
        public String toString() {
            return "FipTuple{" +
                    "code=" + code +
                    ", tsCode=" + tsCode +
                    '}';
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (FipTuple fipTuple : fip) {
            if (fipTuple.tsCode == FipTuple.INVALID_TS_CODE) {
                Optional<String> atomToken = getAtomToken(fipTuple.code);
                if (atomToken.isPresent()) {
                    result.append(atomToken.get());
                } else {
                    throw new IllegalArgumentException("FIP CODE: " + fipTuple.code);
                }
            } else {
                result.append(getTsToken(fipTuple.getTsCode()));
            }
        }

        return result.toString();
    }

    private Optional<String> getAtomToken(int code) {
        return atoms.stream()
                .filter(atom -> atom.getCode() == code)
                .map(Atom::getToken)
                .findFirst();
    }

    private String getTsToken(int tsCode) {
        Optional<TsTuple> tsTupleResult = ts.stream()
                .filter(tsTuple -> tsTuple.getCode() == tsCode)
                .findFirst();
        if (tsTupleResult.isEmpty()) {
            throw new IllegalArgumentException("TS CODE:" + tsCode);
        }

        return Atom.isConstant(tsTupleResult.get().getToken()) ? "constant" : "identifier";
    }
}
