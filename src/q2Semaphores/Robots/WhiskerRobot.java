package q2Semaphores.Robots;

import q2Semaphores.CatMaker;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class WhiskerRobot extends Robot {
    private final Semaphore headsWithEyes = CatMaker.getHeadsWithEyes();
    private final Semaphore headsWithWhiskers = CatMaker.getHeadsWithWhiskers();
    private final Semaphore fullHeads = CatMaker.getFullHeads();

    public void run() {
        while (CatMaker.produceCats) {
            boolean headWithEyesFound = headsWithEyes.tryAcquire();
            if (headWithEyesFound) {
                System.out.println("decremented headsWithEyes, to " + headsWithEyes.availablePermits());
                fullHeads.release();
                System.out.println("incremented fullHeads to " + fullHeads.availablePermits());
            }
            else {
                headsWithWhiskers.release();
                System.out.println("incremented headsWithWhiskers to " + headsWithWhiskers.availablePermits());
            }
            work(ThreadLocalRandom.current().nextLong(20, 60));

        }
    }
}
