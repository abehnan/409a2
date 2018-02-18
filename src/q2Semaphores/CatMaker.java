package q2Semaphores;

import q2Semaphores.Robots.*;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class CatMaker {
    public static volatile boolean produceCats = true;
    private static final int limit = 250;

    // our finite bins
    private static final Semaphore hindLegs = new Semaphore(0);
    private static final Semaphore foreLegs = new Semaphore(0);
    private static final Semaphore headsWithWhiskers = new Semaphore(0);
    private static final Semaphore headsWithEyes = new Semaphore(0);
    private static final Semaphore bodiesWithTails = new Semaphore(0);
    private static final Semaphore bodiesWithLegs = new Semaphore(0);
    private static final Semaphore fullBodies = new Semaphore(0);
    private static final Semaphore fullHeads = new Semaphore(0);

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
        System.out.println("Name, Time \"Working\" (thread sleeping), Time Idle (thread active), % Working, % Idle");
        float percent;
        for (Robot r : robots) {
            System.out.print(r.getClass().getName() + ", " );
            System.out.print(r.getTimeSpentWorking() + ", ");
            System.out.print(totalTime - r.getTimeSpentWorking() + ", ");
            percent = r.getTimeSpentWorking() / (float)totalTime * 100;
            System.out.print(String.format("%.0f%%",percent) + ", ");
            percent = (totalTime - r.getTimeSpentWorking()) / (float)totalTime * 100;
            System.out.print(String.format("%.0f%%",percent) + "\n");
        }
    }

    public static int getLimit() {
        return limit;
    }

    public static Semaphore getHindLegs() {
        return hindLegs;
    }

    public static Semaphore getForeLegs() {
        return foreLegs;
    }

    public static Semaphore getHeadsWithWhiskers() {
        return headsWithWhiskers;
    }

    public static Semaphore getHeadsWithEyes() {
        return headsWithEyes;
    }

    public static Semaphore getBodiesWithTails() {
        return bodiesWithTails;
    }

    public static Semaphore getBodiesWithLegs() {
        return bodiesWithLegs;
    }

    public static Semaphore getFullBodies() {
        return fullBodies;
    }

    public static Semaphore getFullHeads() {
        return fullHeads;
    }
}
