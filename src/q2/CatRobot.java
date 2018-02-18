package q2;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CatRobot extends Thread {
    private final AtomicInteger cats = CatMaker.getCats();
    private final FiniteBin fullHeads = CatMaker.getFullHeads();
    private final FiniteBin fullBodies = CatMaker.getFullBodies();

    public void run() {
        while(cats.get() < CatMaker.getLimit()) {
            synchronized (fullBodies) {
                if (fullBodies.getCount() == 0) {
                    try {
                        fullBodies.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                fullBodies.decrement();
                System.out.println("decremented fullBodies to " + fullBodies.getCount());
            }
            synchronized (fullHeads) {
                if (fullHeads.getCount() == 0) {
                    try {
                        fullHeads.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                fullHeads.decrement();
                System.out.println("decremented fullHeads to " + fullHeads.getCount());
            }
            cats.incrementAndGet();
            System.out.println("incremented cats to: " + cats.get());
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(10, 20));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
