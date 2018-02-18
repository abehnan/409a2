package q2;

import java.util.concurrent.ThreadLocalRandom;

class WhiskerRobot extends Thread {
    private final Object heads = CatMaker.getHeads();
    private final Object whiskers = CatMaker.getWhiskers();
    private final FiniteBin headsWithEyes = CatMaker.getHeadsWithEyes();
    private final FiniteBin headsWithWhiskers = CatMaker.getHeadsWithWhiskers();
    private final FiniteBin fullHeads = CatMaker.getFullHeads();

    public void run() {
        while (CatMaker.produceCats) {
            boolean headWithEyesFound = false;
            synchronized(headsWithEyes) {
                if (headsWithEyes.getCount() > 0) {
                    headsWithEyes.decrement();
                    System.out.println("decremented headsWithEyes to " + headsWithEyes.getCount());
                    headWithEyesFound = true;
                }
            }
            if (!headWithEyesFound) {
                synchronized (heads) {}
            }
            synchronized (whiskers){}
            if (headWithEyesFound) {
                synchronized (fullHeads) {
                    fullHeads.increment();
                    System.out.println("incremented fullHeads to " + fullHeads.getCount());
                    if (fullHeads.getCount() >= 1)
                        fullHeads.notify();
                }
            }
            else {
                synchronized (headsWithWhiskers) {
                    synchronized (headsWithWhiskers) {
                        headsWithWhiskers.increment();
                        System.out.println("incremented headsWithWhiskers to " + headsWithWhiskers.getCount());
                    }
                }
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(20, 60));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
