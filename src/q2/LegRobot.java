package q2;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class LegRobot extends Thread{
    private final Object bodies = CatMaker.getBodies();
    private final FiniteBin hindLegs = CatMaker.getHindLegs();
    private final FiniteBin foreLegs = CatMaker.getForeLegs();
    private final FiniteBin bodiesWithTails = CatMaker.getBodiesWithTails();
    private final FiniteBin fullBodies = CatMaker.getFullBodies();
    private final FiniteBin bodiesWithLegs = CatMaker.getBodiesWithLegs();
    private final AtomicInteger cats = CatMaker.getCats();

    public void run() {
        while(cats.get() < CatMaker.getLimit()) {
            boolean bodyWithTailsFound = false;

            // try to get a body with a tail
            synchronized (bodiesWithTails) {
                if (bodiesWithTails.getCount() > 0) {
                    bodiesWithTails.decrement();
                    System.out.println("decremented bodiesWithTails to " + bodiesWithTails.getCount());
                    bodyWithTailsFound = true;
                }
            }
            // get an empty body if we could't find one with a tail
            if (!bodyWithTailsFound) {
                synchronized (bodies) {}
            }
            // get 2 hindlegs
            synchronized(hindLegs) {
                if (hindLegs.getCount() < 2) {
                    try {
                        hindLegs.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                hindLegs.decrement();
                System.out.println("decremented hindLegs to " + hindLegs.getCount());
                hindLegs.decrement();
                System.out.println("decremented hindLegs to " + hindLegs.getCount());
            }
            // get 2 forelegs
            synchronized(foreLegs) {
                if (foreLegs.getCount() < 2) {
                    try {
                        foreLegs.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                foreLegs.decrement();
                System.out.println("decremented foreLegs to " + foreLegs.getCount());
                foreLegs.decrement();
                System.out.println("decremented foreLegs to " + foreLegs.getCount());
            }
            // increment the appropriate bin
            if (bodyWithTailsFound) {
                synchronized (fullBodies) {
                    fullBodies.increment();
                    System.out.println("incremented fullBodies to " + fullBodies.getCount());
                    if (fullBodies.getCount() >= 1)
                        fullBodies.notify();
                }
            }
            else {
                synchronized (bodiesWithLegs) {
                    bodiesWithLegs.increment();
                    System.out.println("incremented bodiesWithLegs to " + bodiesWithLegs.getCount());
                }
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(30, 50));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
