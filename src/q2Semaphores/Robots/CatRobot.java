package q2Semaphores.Robots;

import q2Semaphores.CatMaker;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class CatRobot extends Robot {
    private final Semaphore fullHeads = CatMaker.getFullHeads();
    private final Semaphore fullBodies = CatMaker.getFullBodies();
    private final int limit = CatMaker.getLimit();
    private int cats = 0;

    public void run() {
        while(cats < limit) {
            try {
                fullBodies.acquire();
                System.out.println("decremented fullBodies to " + fullBodies.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                fullHeads.acquire();
                System.out.println("decremented fullHeads to " + fullHeads.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cats++;
            System.out.println("\n~~~~~~~~ incremented cats to " + cats + " ~~~~~~~~\n");
            work(ThreadLocalRandom.current().nextLong(10, 20));
        }
        CatMaker.produceCats = false;
    }
}
