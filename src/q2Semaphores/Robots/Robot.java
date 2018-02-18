package q2Semaphores.Robots;

public abstract class Robot extends Thread {
    private long timeSpentWorking = 0;

    public long getTimeSpentWorking() {
        return timeSpentWorking;
    }

    void work(long time) {
        timeSpentWorking += time;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
