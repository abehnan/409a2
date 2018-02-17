package q1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

public class Star {
    private static final DecimalFormat numberFormat = new DecimalFormat("0.000");
    private static final int width = 1920;
    private static final int height = 1080;
    private static final LinkedList<Vertex> polygon = new LinkedList<>();
    private static final BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
    private static final Graphics2D g2d = img.createGraphics();

    // draws the polygon to the buffered image and outputs to output.png
    private static void draw() {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.red);
        colors.add(Color.green);
        colors.add(Color.orange);
        colors.add(Color.cyan);
        colors.add(Color.blue);
        colors.add(Color.magenta);
        int j = 0;
        g2d.setStroke(new BasicStroke(5));
        for (int i = 0; i < polygon.size()-1; i++) {
            g2d.setColor(colors.get(j++));
            g2d.drawLine(
                    polygon.get(i).getScaledX(),
                    polygon.get(i).getScaledY(),
                    polygon.get(i+1).getScaledX(),
                    polygon.get(i+1).getScaledY()
            );
        }
        g2d.setColor(colors.get(5));
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

    public static LinkedList<Vertex> getPolygon() {
        return polygon;
    }

    public static void main(String[] args) {
        int c = -1, m = -1;
        try {
            if (args.length<2)
                throw new Exception("Missing arguments, only "+args.length+" were specified!");
            m = Integer.parseInt(args[0]);
            c = Integer.parseInt(args[1]);
            if (m > 6 || m < 1)
                throw new Exception("m must be an integer between 1 and 6");
            else if (c < 0)
                throw new Exception("c must be a positive integer");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // hard coded polygon
        polygon.add(new Vertex(-1.0, 5.0));
        polygon.add(new Vertex(1.0, 2.0));
        polygon.add(new Vertex(5.0, 0.0));
        polygon.add(new Vertex(1.0, -2.0));
        polygon.add(new Vertex(-4.0, -4.0));
        polygon.add(new Vertex(-3.0, -1.0));

        // debug print all vertices
        System.out.println("initial vertices: ");
        for (Vertex v : polygon) {
            System.out.println("x: " + numberFormat.format(v.getX()) + " y: " + numberFormat.format(v.getY()));
        }
        System.out.println();

        // start and join the threads
        StarThread[] threads = new StarThread[m];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new StarThread(c);
            threads[i].start();
        }
        for (StarThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // debug print all vertices
        System.out.println("\nfinal vertices: ");
        for (Vertex v : polygon) {
            System.out.println("x: " + numberFormat.format(v.getX()) + " y: " + numberFormat.format(v.getY()));
        }
        System.out.println();

        // output
        initializeImage();
        draw();

    }
}
