package utils.io;

import model.Monomial;
import model.Polynomial;
import utils.linkedList.LinkedList;
import utils.queue.MyBlockingQueue;

import java.io.InputStream;
import java.util.Scanner;

public class PolynomialReader {
    public static Polynomial read(InputStream file) {
        try (Scanner scanner = new Scanner(file)) {
            LinkedList<Monomial> monomials = new LinkedList<>();

            while (scanner.hasNext()) {
                String[] data = scanner.nextLine().split(" ");
                Monomial monomial = new Monomial(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                monomials.add(monomial);
            }

            return new Polynomial(monomials);
        }
    }

    public static void readParallel(MyBlockingQueue queue, int p, int start, int end, InputStream[] files) {
        for (int i = start; i < end; i++) {
            InputStream file = files[i];
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    String[] data = scanner.nextLine().split(" ");
                    Monomial monomial = new Monomial(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                    queue.add(monomial);
                }
            }
        }

        for (int i = 0; i < p; i++) {
            queue.add(Monomial.INVALID_MONOMIAL);  // mark end of reading
        }
    }
}
