package q2;

class CatMaker {
    static volatile boolean produceCats = true;
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
            catRobot.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
