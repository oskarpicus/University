package utils.queue;

import model.Monomial;

import java.util.LinkedList;
import java.util.Queue;

public class MyBlockingQueue {
    private final Queue<Monomial> monomials;

    public MyBlockingQueue() {
        this.monomials = new LinkedList<>();
    }

    public synchronized boolean add(Monomial o) {
        boolean wasInserted = monomials.add(o);
        if (wasInserted) {
            this.notifyAll();
        }

        return wasInserted;
    }

    public synchronized Monomial poll() throws InterruptedException {
        while (monomials.isEmpty()) {
            this.wait();
        }

        return monomials.poll();
    }
}
