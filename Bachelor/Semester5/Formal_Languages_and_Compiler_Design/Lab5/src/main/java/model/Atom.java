package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Atom {
    private String token;
    private int code;
    private static final Pattern patternIdentifier = Pattern.compile("[a-zA-Z_][a-zA-Z0-9]{0,249}");
    private static final Pattern patternConst = Pattern.compile("([1-9][0-9]*)|0|(\".+\")");

    public Atom(String token, int code) {
        this.token = token;
        this.code = code;
    }

    public Atom() {
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Atom{" +
                "token='" + token + '\'' +
                ", code=" + code +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atom atom = (Atom) o;
        return token.equals(atom.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    public int getCode() {
        return code;
    }

    public static boolean isIdentifier(String token) {
        return patternIdentifier.matcher(token).matches();
    }

    public static boolean isConstant(String token) {
        return patternConst.matcher(token).matches();
    }
}
