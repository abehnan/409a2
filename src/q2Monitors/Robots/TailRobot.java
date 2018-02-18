package q2Monitors.Robots;

import q2Monitors.CatMaker;
import q2Monitors.FiniteBin;

import java.util.concurrent.ThreadLocalRandom;

public class TailRobot extends Robot {
    private final Object bodies = CatMaker.getBodies();
    private final FiniteBin bodiesWithLegs = CatMaker.getBodiesWithLegs();
    private final FiniteBin bodiesWithTails = CatMaker.getBodiesWithTails();
    private final FiniteBin fullBodies = CatMaker.getFullBodies();
    private final Object tails = CatMaker.getTails();

    public void run() {
        while(CatMaker.produceCats) {
            boolean bodyWithLegsFound = false;
            synchronized (bodiesWithLegs) {
                if (bodiesWithLegs.getCount() > 0) {
                    bodiesWithLegs.decrement();
                    System.out.println("decremented bodiesWithLegs to " + bodiesWithLegs.getCount());
                    bodyWithLegsFound = true;
                }
            }
            if (!bodyWithLegsFound) {
                synchronized (bodies) {}
            }
            synchronized (tails) {}
            if (bodyWithLegsFound) {
                synchronized (fullBodies) {
                    fullBodies.increment();
                    System.out.println("incremented fullBodies to " + fullBodies.getCount());
                    if (fullBodies.getCount() >= 1)
                        fullBodies.notify();
                }
            }
            else {
                synchronized (bodiesWithTails) {
                    bodiesWithTails.increment();
                    System.out.println("incremented bodiesWithTails to " + bodiesWithTails.getCount());
                }
            }
            work(ThreadLocalRandom.current().nextLong(10, 20));

        }
    }

}
