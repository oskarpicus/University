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

        int p1 = Integer.parseInt(args[0]);
        int p2 = Integer.parseInt(args[1]);

        InputStream[] files = new InputStream[args.length - 2];
        for (int i = 2; i < args.length; i++) {
            files[i - 2] = Objects.requireNonNull(MainSequential.class.getClassLoader().getResourceAsStream(args[i]));
        }

        Thread[] threads = new Thread[p1 + p2];
        MyBlockingQueue queue = new MyBlockingQueue();
        Polynomial result = new Polynomial(new LinkedList<>());

        int quotientWorkers = p2 / p1, remainderWorkers = p2 % p1;
        int quotientFiles = files.length / p1, remainderFiles = files.length % p1, startFiles, endFiles = 0;

        for (int i = 0; i < p1; i++) {
            int nrWorkersToFinish = quotientWorkers + (remainderWorkers > 0 ? 1 : 0);
            remainderWorkers--;

            startFiles = endFiles;
            endFiles = startFiles + quotientFiles + (remainderFiles > 0 ? 1 : 0);
            remainderFiles--;

            int finalStartFiles = startFiles;
            int finalEndFiles = endFiles;
            threads[i] = new Thread(() -> PolynomialReader.readParallel(queue, nrWorkersToFinish, finalStartFiles, finalEndFiles, files));
            threads[i].start();
        }

        for (int i = p1; i < p1 + p2; i++) {
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
