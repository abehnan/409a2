package q2Monitors.Robots;

import q2Monitors.CatMaker;
import q2Monitors.FiniteBin;

import java.util.concurrent.ThreadLocalRandom;

public class ToeRobot extends Robot {
    private final Object legs = CatMaker.getLegs();
    private final Object toes = CatMaker.getToes();
    private final FiniteBin hindLegs = CatMaker.getHindLegs();
    private final FiniteBin foreLegs = CatMaker.getForeLegs();

    public void run() {
        while (CatMaker.produceCats) {
            // get a leg
            synchronized(legs){}
            // get some 4-5 toes (randomly)
            synchronized(toes){}
            float rng = ThreadLocalRandom.current().nextFloat();
            // add a foreleg to the bin if we got 4 toes
            if (rng < 0.5) {
                synchronized (foreLegs) {
                    foreLegs.increment();
                    System.out.println("incremented foreLegs to " + foreLegs.getCount());
                    if (foreLegs.getCount() >= 2)
                        foreLegs.notify();
                }
            }
            // add a hindleg to the bin if we got 5 toes
            else {
                synchronized (hindLegs) {
                    hindLegs.increment();
                    System.out.println("incremented hindLegs to " + hindLegs.getCount());
                    if (hindLegs.getCount() >= 2)
                        hindLegs.notify();
                }
            }
            work(ThreadLocalRandom.current().nextLong(10, 20));

        }

        synchronized (foreLegs) {
            if (foreLegs.getCount() < 2) {
                foreLegs.increment();
                System.out.println("incremented foreLegs to " + foreLegs.getCount());
                if (foreLegs.getCount() >= 2)
                    foreLegs.notify();
            }
        }
        synchronized (hindLegs) {
            if (hindLegs.getCount() < 2) {
                hindLegs.increment();
                System.out.println("incremented hindLegs to " + hindLegs.getCount());
                if (hindLegs.getCount() >= 2)
                    hindLegs.notify();
            }
        }
    }
}
