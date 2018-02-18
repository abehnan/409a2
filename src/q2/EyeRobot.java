package q2;

import java.util.concurrent.ThreadLocalRandom;

class EyeRobot extends Thread {
    private final Object heads = CatMaker.getHeads();
    private final Object eyes = CatMaker.getEyes();
    private final FiniteBin headsWithEyes = CatMaker.getHeadsWithEyes();
    private final FiniteBin headsWithWhiskers = CatMaker.getHeadsWithWhiskers();
    private final FiniteBin fullHeads = CatMaker.getFullHeads();

    public void run() {
        while (CatMaker.produceCats) {
            boolean headWithWhiskersFound = false;
            synchronized (headsWithWhiskers) {
                if (headsWithWhiskers.getCount() > 0) {
                    headsWithWhiskers.decrement();
                    headWithWhiskersFound = true;
                }
            }
            if (!headWithWhiskersFound) {
                synchronized (heads) {}
            }
            synchronized (eyes) {}
            if (headWithWhiskersFound) {
                synchronized (fullHeads) {
                    fullHeads.increment();
                    System.out.println("incremented fullHeads to " + fullHeads.getCount());
                    if (fullHeads.getCount() >= 1)
                        fullHeads.notify();
                }
            }
            else {
                synchronized (headsWithEyes) {
                    headsWithEyes.increment();
                    System.out.println("incremented headsWithEyes to " + headsWithEyes.getCount());
                }
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(10, 30));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
