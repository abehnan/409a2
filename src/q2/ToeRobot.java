package q2;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class ToeRobot extends Thread {
    private final Object legs = CatMaker.getLegs();
    private final Object toes = CatMaker.getToes();
    private final FiniteBin hindLegs = CatMaker.getHindLegs();
    private final FiniteBin foreLegs = CatMaker.getForeLegs();
    private final AtomicInteger cats = CatMaker.getCats();

    public void run() {
        while (cats.get() < CatMaker.getLimit()) {
            // get a leg
            synchronized(legs){}
            // get some 4-5 toes (randomly)
            synchronized(toes){}
            int numToes = ThreadLocalRandom.current().nextInt(4, 6);
            // add a foreleg to the bin if we got 4 toes
            if (numToes == 4) {
                synchronized (foreLegs) {
                    foreLegs.increment();
                    System.out.println("incremented foreLegs to " + foreLegs.getCount());
                    if (foreLegs.getCount() >= 2)
                        foreLegs.notify();
                }
            }
            // add a hindleg to the bin if we got 5 toes
            else if (numToes == 5) {
                synchronized (hindLegs) {
                    hindLegs.increment();
                    System.out.println("incremented hindLegs to " + hindLegs.getCount());
                    if (hindLegs.getCount() >= 2)
                        hindLegs.notify();
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
