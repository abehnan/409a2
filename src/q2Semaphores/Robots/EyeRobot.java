package q2Semaphores.Robots;

import q2Semaphores.CatMaker;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class EyeRobot extends Robot {

    private final Semaphore headsWithEyes = CatMaker.getHeadsWithEyes();
    private final Semaphore headsWithWhiskers = CatMaker.getHeadsWithWhiskers();
    private final Semaphore fullHeads = CatMaker.getFullHeads();

    public void run() {
        while (CatMaker.produceCats) {
            boolean headWithWhiskersFound = headsWithWhiskers.tryAcquire();

            if (headWithWhiskersFound) {
                System.out.println("decremented headsWithWhiskers to " + headsWithWhiskers.availablePermits());
                fullHeads.release();
                System.out.println("incremented fullHeads to " + fullHeads.availablePermits());
            }
            else {
                headsWithEyes.release();
                System.out.println("incremented headsWithEyes to " + headsWithEyes.availablePermits());
            }
            work(ThreadLocalRandom.current().nextLong(10, 30));
        }
    }
}
