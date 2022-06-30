import model.Polynomial;
import utils.io.PolynomialReader;
import utils.io.PolynomialWriter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainSequential {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        List<Polynomial> polynomials = Arrays.stream(args)
                .map(filename -> Objects.requireNonNull(MainSequential.class.getClassLoader().getResourceAsStream(filename)))
                .map(PolynomialReader::read)
                .collect(Collectors.toList());

        // add each polynomial
        Polynomial polynomial = polynomials.get(0);
        for (int i = 1; i < polynomials.size(); i++) {
            polynomial.add(polynomials.get(i));
        }

        PolynomialWriter.write("result.txt", polynomial);

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
