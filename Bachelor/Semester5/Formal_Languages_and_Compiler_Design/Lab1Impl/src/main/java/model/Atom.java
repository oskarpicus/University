package model;

import java.util.Objects;

public class Atom {
    private String token;
    private AtomType type;
    private int code;

    public Atom(String token, AtomType type, int code) {
        this.token = token;
        this.type = type;
        this.code = code;
    }

    public Atom() {
    }

    public String getToken() {
        return token;
    }

    public AtomType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Atom{" +
                "token='" + token + '\'' +
                ", type=" + type + '\'' +
                ", code=" + code +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atom atom = (Atom) o;
        return token.equals(atom.token) && type == atom.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type);
    }

    public int getCode() {
        return code;
    }
}
