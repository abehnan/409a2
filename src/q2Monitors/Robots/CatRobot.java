package q2Monitors.Robots;

import q2Monitors.CatMaker;
import q2Monitors.FiniteBin;

import java.util.concurrent.ThreadLocalRandom;

public class CatRobot extends Robot {
    private final FiniteBin fullHeads = CatMaker.getFullHeads();
    private final FiniteBin fullBodies = CatMaker.getFullBodies();
    private final int limit = CatMaker.getLimit();
    private int cats = 0;

    public void run() {
        while(cats < limit) {
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
            cats++;
            System.out.println("\n~~~~~~~~ incremented cats to " + cats + " ~~~~~~~~\n");
            work(ThreadLocalRandom.current().nextLong(10, 20));
        }
        CatMaker.produceCats = false;
    }
}
