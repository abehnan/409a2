package q1;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Random;

public class StarThread extends Thread {
    private int c;
    private static final LinkedList<Vertex> polygon = Star.getPolygon();
    private final Random rng = new Random();
    private int count = 0;
    private static final DecimalFormat numberFormat = new DecimalFormat("0.000");

    StarThread(int c) {
        this.c = c;
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public void run() {
        while (count < c) {

            Vertex A, B, C;

            // choose a random vertex from polygon
            int index = rng.nextInt(polygon.size());
            A = polygon.get(index);
            synchronized(A) {
                // get previous and next vertex
                if (index == 0) {
                    B = polygon.get(index + 1);
                    C = polygon.get(polygon.size()-1);
                }
                else if (index == polygon.size() - 1) {
                    B = polygon.get(0);
                    C = polygon.get(polygon.size() - 2);
                }
                else {
                    B = polygon.get(index + 1);
                    C = polygon.get(index - 1);
                }

                // modify that vertex's coordinates
                // code for finding a random point in a triangle was found on stackoverflow
                // source: https://stackoverflow.com/questions/19654251/random-point-inside-triangle-inside-java
                double r1 = rng.nextDouble();
                double r2 = rng.nextDouble();
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
                count++;
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
