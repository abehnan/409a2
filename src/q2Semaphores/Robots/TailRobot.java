package q2Semaphores.Robots;

import q2Semaphores.CatMaker;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class TailRobot extends Robot {
    private final Semaphore bodiesWithLegs = CatMaker.getBodiesWithLegs();
    private final Semaphore bodiesWithTails = CatMaker.getBodiesWithTails();
    private final Semaphore fullBodies = CatMaker.getFullBodies();

    public void run() {
        while(CatMaker.produceCats) {
            boolean bodyWithLegsFound = bodiesWithLegs.tryAcquire();
            if (bodyWithLegsFound) {
                System.out.println("decremented bodiesWithLegs to " + bodiesWithLegs.availablePermits());
                fullBodies.release();
                System.out.println("incremented fullBodies to " + fullBodies.availablePermits());
            }
            else {
                bodiesWithTails.release();
                System.out.println("incremented bodiesWithTails to " + bodiesWithTails.availablePermits());
            }

            work(ThreadLocalRandom.current().nextLong(10, 20));

        }
    }

}
