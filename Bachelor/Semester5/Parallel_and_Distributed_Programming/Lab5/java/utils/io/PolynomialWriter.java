package utils.io;

import model.Monomial;
import model.Polynomial;
import utils.linkedList.Node;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class PolynomialWriter {
    public static void write(String destination, Polynomial polynomial) {
        try (PrintWriter writer = new PrintWriter(destination)){//, StandardCharsets.UTF_8)) {
            Iterator<Node<Monomial>> iterator = polynomial.getMonomials().getIterator();
            while (iterator.hasNext()) {
                Node<Monomial> monomialNode = iterator.next();

                writer.write(monomialNode.getElement().getCoefficient() + " " + monomialNode.getElement().getExponential() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
