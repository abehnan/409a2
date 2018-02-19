package q2Monitors;

import q2Monitors.Robots.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CatMaker {
    public static volatile boolean produceCats = true;
    private static final int limit = 250;

    // our infinite bins
    private static final Object legs = new Object();
    private static final Object toes = new Object();
    private static final Object eyes = new Object();
    private static final Object whiskers = new Object();
    private static final Object tails = new Object();
    private static final Object bodies = new Object();
    private static final Object heads = new Object();

    // our finite bins
    private static final FiniteBin hindLegs = new FiniteBin();
    private static final FiniteBin foreLegs = new FiniteBin();
    private static final FiniteBin headsWithWhiskers = new FiniteBin();
    private static final FiniteBin headsWithEyes = new FiniteBin();
    private static final FiniteBin bodiesWithTails = new FiniteBin();
    private static final FiniteBin bodiesWithLegs = new FiniteBin();
    private static final FiniteBin fullBodies = new FiniteBin();
    private static final FiniteBin fullHeads = new FiniteBin();

    public static void main(String args[]) {

        ToeRobot toeRobot1 = new ToeRobot();
        ToeRobot toeRobot2 = new ToeRobot();
        LegRobot legRobot1 = new LegRobot();
        LegRobot legRobot2 = new LegRobot();
        TailRobot tailRobot1 = new TailRobot();
        TailRobot tailRobot2 = new TailRobot();
        EyeRobot eyeRobot1 = new EyeRobot();
        EyeRobot eyeRobot2 = new EyeRobot();
        WhiskerRobot whiskerRobot1 = new WhiskerRobot();
        WhiskerRobot whiskerRobot2 = new WhiskerRobot();
        CatRobot catRobot = new CatRobot();

        long startTime = System.currentTimeMillis();
        toeRobot1.start();
        toeRobot2.start();
        legRobot1.start();
        legRobot2.start();
        tailRobot1.start();
        tailRobot2.start();
        eyeRobot1.start();
        eyeRobot2.start();
        whiskerRobot1.start();
        whiskerRobot2.start();
        catRobot.start();

        try {
            toeRobot1.join();
            toeRobot2.join();
            legRobot1.join();
            legRobot2.join();
            tailRobot1.join();
            tailRobot2.join();
            eyeRobot1.join();
            eyeRobot2.join();
            whiskerRobot1.join();
            whiskerRobot2.join();
            catRobot.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long totalTime = System.currentTimeMillis() - startTime;

        // output
        ArrayList<Robot> robots = new ArrayList<>();
        robots.add(toeRobot1);
        robots.add(toeRobot2);
        robots.add(legRobot1);
        robots.add(legRobot2);
        robots.add(tailRobot1);
        robots.add(eyeRobot1);
        robots.add(eyeRobot2);
        robots.add(whiskerRobot1);
        robots.add(whiskerRobot2);
        robots.add(catRobot);
        printOutput(robots, totalTime);

    }

    private static void printOutput(ArrayList<Robot> robots, long totalTime) {
        System.out.println("\n\ntotalTime (ms), " + totalTime);
        System.out.println("Name,Time \"Working\" (thread sleeping),Time Idle (thread active),Working %,Idle %");
        float percent;
        DecimalFormat percentFormat = new DecimalFormat("#0.00");
        for (Robot r : robots) {
            System.out.print(r.getClass().getName() + "," );
            System.out.print(r.getTimeSpentWorking() + ",");
            System.out.print(totalTime - r.getTimeSpentWorking() + ",");
            percent = r.getTimeSpentWorking() / (float)totalTime * 100;
            System.out.print(percentFormat.format(percent) + ",");
            percent = (totalTime - r.getTimeSpentWorking()) / (float)totalTime * 100;
            System.out.print(percentFormat.format(percent) + "\n");
        }
    }

    public static int getLimit() {
        return limit;
    }

    public static Object getLegs() {
        return legs;
    }

    public static Object getToes() {
        return toes;
    }

    public static Object getEyes() {
        return eyes;
    }

    public static Object getWhiskers() {
        return whiskers;
    }

    public static Object getTails() {
        return tails;
    }

    public static Object getBodies() {
        return bodies;
    }

    public static Object getHeads() {
        return heads;
    }

    public static FiniteBin getHindLegs() {
        return hindLegs;
    }

    public static FiniteBin getForeLegs() {
        return foreLegs;
    }

    public static FiniteBin getHeadsWithWhiskers() {
        return headsWithWhiskers;
    }

    public static FiniteBin getHeadsWithEyes() {
        return headsWithEyes;
    }

    public static FiniteBin getBodiesWithTails() {
        return bodiesWithTails;
    }

    public static FiniteBin getBodiesWithLegs() {
        return bodiesWithLegs;
    }

    public static FiniteBin getFullBodies() {
        return fullBodies;
    }

    public static FiniteBin getFullHeads() {
        return fullHeads;
    }
}
