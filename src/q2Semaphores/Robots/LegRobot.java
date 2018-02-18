package q2Semaphores.Robots;

import q2Semaphores.CatMaker;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class LegRobot extends Robot {
    private final Semaphore hindLegs = CatMaker.getHindLegs();
    private final Semaphore foreLegs = CatMaker.getForeLegs();
    private final Semaphore bodiesWithTails = CatMaker.getBodiesWithTails();
    private final Semaphore fullBodies = CatMaker.getFullBodies();
    private final Semaphore bodiesWithLegs = CatMaker.getBodiesWithLegs();

    public void run() {
        while(CatMaker.produceCats) {
            try {
                hindLegs.acquire(2);
                System.out.println("decremented hindLegs to " + hindLegs.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                foreLegs.acquire(2);
                System.out.println("decremented foreLegs to " + foreLegs.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean bodyWithTailsFound = bodiesWithTails.tryAcquire();

            if (bodyWithTailsFound) {
                System.out.println("decremented bodiesWithTails to " + bodiesWithTails.availablePermits());
                fullBodies.release();
                System.out.println("incremented fullBodies to " + fullBodies.availablePermits());
            }
            else {
                bodiesWithLegs.release();
                System.out.println("incremented bodiesWithLegs to " + bodiesWithLegs.availablePermits());
            }
            work(ThreadLocalRandom.current().nextLong(30, 50));
        }
    }
}
