package q1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

class StarThread extends Thread {
    private final int c;
    private static final ArrayList<Vertex> polygon = Star.getPolygon();
    private int count = 0;
    private static final DecimalFormat numberFormat = new DecimalFormat("0.###E0");
    private static final ReentrantLock[] locks = {
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()
    };

    StarThread(int c) {
        this.c = c;
    }

    public void run() {
        while (count < c) {

            Vertex A, B, C;

            int index = ThreadLocalRandom.current().nextInt(polygon.size());
            A = polygon.get(index);

            // get references, acquire locks in order by locking the lowest index one first
            if (index == 0) {
                B = polygon.get(index + 1);
                C = polygon.get(polygon.size()-1);
                locks[0].lock();
                locks[index + 1].lock();
                locks[polygon.size()-1].lock();

            }
            else if (index == polygon.size() - 1) {
                B = polygon.get(0);
                C = polygon.get(index - 1);
                locks[0].lock();
                locks[index -1].lock();
                locks[index].lock();

            }
            else {
                B = polygon.get(index + 1);
                C = polygon.get(index - 1);
                locks[index-1].lock();
                locks[index].lock();
                locks[index+1].lock();
            }

            // modify that vertex's coordinates
            // code for finding a random point in a triangle was found produceCats stackoverflow
            // source: https://stackoverflow.com/questions/19654251/random-point-inside-triangle-inside-java
            double r1 = ThreadLocalRandom.current().nextDouble();
            double r2 = ThreadLocalRandom.current().nextDouble();
            double x =
                    (1 - Math.sqrt(r1)) * A.getX() +
                    (Math.sqrt(r1) * (1 - r2)) * B.getX() +
                    (Math.sqrt(r1) *r2) * C.getX();
            double y =
                    (1 - Math.sqrt(r1)) * A.getY() +
                    (Math.sqrt(r1) * (1 - r2)) * B.getY() +
                    (Math.sqrt(r1) *r2) * C.getY();

            // debug
            System.out.println(
                "vertex (" + numberFormat.format(A.getX()) + ", " + numberFormat.format(A.getY()) +
                ") is being changed to (" + numberFormat.format(x) + ", " + numberFormat.format(y) + ")"
            );

            A.setX(x);
            A.setY(y);

            // unlock the locks
            if (index == 0) {
                locks[0].unlock();
                locks[index + 1].unlock();
                locks[polygon.size()-1].unlock();
            }
            else if (index == polygon.size() - 1) {
                locks[0].unlock();
                locks[index -1].unlock();
                locks[index].unlock();

            }
            else {
                locks[index-1].unlock();
                locks[index].unlock();
                locks[index+1].unlock();
            }

            count++;
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
