package q1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class Star {
    private static final int width = 1920;
    private static final int height = 1080;
    private static final LinkedList<Vertex> polygon = new LinkedList<>();
    private static final BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
    private static final Graphics2D g2d = img.createGraphics();

    // draws the polygon to the buffered image and outputs to output.png
    private static void draw() {

        g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(5));

        for (int i = 0; i < polygon.size()-1; i++) {
            g2d.drawLine(
                    polygon.get(i).getScaledX(),
                    polygon.get(i).getScaledY(),
                    polygon.get(i+1).getScaledX(),
                    polygon.get(i+1).getScaledY()
            );
        }
        g2d.drawLine(
                polygon.getLast().getScaledX(),
                polygon.getLast().getScaledY(),
                polygon.getFirst().getScaledX(),
                polygon.getFirst().getScaledY()
        );

        // write out image
        File output = new File("output.png");
        try {
            ImageIO.write(img, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // initializes our image with the axis representing a graph.
    private static void initializeImage() {
        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());

        g2d.setColor(Color.black);
        // vertical axis
        g2d.drawLine(img.getWidth()/2, 0, img.getWidth()/2, img.getHeight());
        // horizontal axis
        g2d.drawLine(0, img.getHeight()/2, img.getWidth(), img.getHeight()/2);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void main(String[] args) {

        // hard coded polygon
        polygon.add(new Vertex(-1.0, 5.0));
        polygon.add(new Vertex(1.0, 2.0));
        polygon.add(new Vertex(5.0, 0.0));
        polygon.add(new Vertex(1.0, -2.0));
        polygon.add(new Vertex(-4.0, -4.0));
        polygon.add(new Vertex(-3.0, -1.0));

        // debug print all vertices
        for (Vertex v : polygon) {
            System.out.println("x: " + v.getScaledX() + " y: " + v.getScaledY());
        }
        System.out.println();

        // single-threaded test
        Random rng = new Random();
        Vertex A, B, C;
        int iterations = 10;

        for (int i = 0; i < iterations; i++) {

            int index = rng.nextInt(polygon.size());
            A = polygon.get(index);
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
            double r1 = rng.nextDouble();
            double r2 = rng.nextDouble();
            // code for finding a random point in a triangle was found on stackoverflow
            // source: https://stackoverflow.com/questions/19654251/random-point-inside-triangle-inside-java
            double x = (1 - Math.sqrt(r1)) * A.getX() +
                    (Math.sqrt(r1) * (1 - r2)) * B.getX() +
                    (Math.sqrt(r1) *r2) * C.getX();
            double y = (1 - Math.sqrt(r1)) * A.getY() +
                    (Math.sqrt(r1) * (1 - r2)) * B.getY() +
                    (Math.sqrt(r1) *r2) * C.getY();
            System.out.println("vertex (" + A.getX() + ", " + A.getY() + ") is being changed to (" + x + ", " + y +")");
            A.setX(x);
            A.setY(y);
        }

        // debug print all vertices
        System.out.println("\nfinal vertices:");
        for (Vertex v : polygon) {
            System.out.println("x: " + v.getScaledX() + " y: " + v.getScaledY());
        }
        initializeImage();
        draw();

    }
}
