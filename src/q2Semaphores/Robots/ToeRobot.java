package q2Semaphores.Robots;

import q2Semaphores.CatMaker;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class ToeRobot extends Robot {
    private final Semaphore hindLegs = CatMaker.getHindLegs();
    private final Semaphore foreLegs = CatMaker.getForeLegs();

    public void run() {
        while (CatMaker.produceCats) {

            float rng = ThreadLocalRandom.current().nextFloat();
            // 50% chance to create foreleg
            if (rng < 0.5) {
                foreLegs.release();
                System.out.println("incremented foreLegs to " + foreLegs.availablePermits());

            }
            // 50% chance to create hindleg
            else {
                hindLegs.release();
                System.out.println("incremented hindLegs to " + hindLegs.availablePermits());
            }
            work(ThreadLocalRandom.current().nextLong(10, 20));

        }
        if (foreLegs.hasQueuedThreads()) {
            foreLegs.release(2);
        }
        if (hindLegs.hasQueuedThreads()) {
            hindLegs.release(2);
        }

    }
}
