import model.Polynomial;
import utils.io.PolynomialReader;
import utils.io.PolynomialWriter;
import utils.linkedList.LinkedList;
import utils.queue.MyBlockingQueue;
import utils.threading.AddRunnable;

import java.io.InputStream;
import java.util.Objects;

public class MainParallel {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int p = Integer.parseInt(args[0]);

        InputStream[] files = new InputStream[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            files[i - 1] = Objects.requireNonNull(MainSequential.class.getClassLoader().getResourceAsStream(args[i]));
        }

        Thread[] threads = new Thread[p];
        MyBlockingQueue queue = new MyBlockingQueue();
        Polynomial result = new Polynomial(new LinkedList<>());

        threads[0] = new Thread(() -> PolynomialReader.readParallel(queue, p, files));
        threads[0].start();

        for (int i = 1; i < p; i++) {
            threads[i] = new Thread(new AddRunnable(queue, result));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        PolynomialWriter.write("result.txt", result);

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
