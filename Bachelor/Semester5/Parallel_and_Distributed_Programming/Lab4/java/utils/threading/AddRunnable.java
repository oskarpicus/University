package utils.threading;

import model.Monomial;
import model.Polynomial;
import utils.linkedList.LinkedList;
import utils.queue.MyBlockingQueue;

public class AddRunnable implements Runnable{
    private final MyBlockingQueue queue;
    private final Polynomial result;

    public AddRunnable(MyBlockingQueue queue, Polynomial result) {
        this.queue = queue;
        this.result = result;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Monomial monomial = queue.poll();
                if (monomial == Monomial.INVALID_MONOMIAL) {
                    break;
                }

                LinkedList<Monomial> linkedList = new LinkedList<>();
                linkedList.add(monomial);

                result.add(new Polynomial(linkedList));
            } catch (InterruptedException e) {
            }
        }
    }
}
