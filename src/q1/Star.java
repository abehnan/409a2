package q1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class Star {
    private static final DecimalFormat numberFormat = new DecimalFormat("0.0##");
    private static final int width = 1920;
    private static final int height = 1080;
    private static final LinkedList<Vertex> polygon = new LinkedList<>();
    private static final BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
    private static final Graphics2D g2d = img.createGraphics();
    private static double scalingFactor = 1;

    // updates scaling factor and midpoints for drawing
    private static void updateDrawValues() {
        double yMin = polygon.get(0).getY();
        double xMin = polygon.get(0).getX();
        double yMax = polygon.get(0).getY();
        double xMax = polygon.get(0).getX();
        for (int i = 1; i < polygon.size(); i++) {
            if (polygon.get(i).getY() < yMin)
                yMin = polygon.get(i).getY();
            if (polygon.get(i).getY() > yMax)
                yMax = polygon.get(i).getY();
            if (polygon.get(i).getX() < xMin)
                xMin = polygon.get(i).getX();
            if (polygon.get(i).getX() > xMax)
                xMax = polygon.get(i).getX();
        }
        if (xMin < 0) {
            for (Vertex v : polygon) {
                v.setX(v.getX() - xMin);
            }
        }
        if (yMin < 0) {
            for (Vertex v : polygon) {
                v.setY(v.getY() - yMin);
            }
        }

        System.out.println("aligned vertices: ");
        for (Vertex v : polygon) {
            System.out.println("x: " + numberFormat.format(v.getX()) + " y: " + numberFormat.format(v.getY()));
        }
        System.out.println();

        double xDiff = xMax - xMin;
        double yDiff = yMax - yMin;
        double yScaling = (double) height / yDiff;
        double xScaling = (double) width / xDiff;

        if (xScaling < yScaling)
            scalingFactor = xScaling;
        else
            scalingFactor = yScaling;

        for (Vertex v : polygon) {
            v.setX(v.getX() * scalingFactor);
            v.setY(v.getY() * scalingFactor);
        }
        // debug print all vertices
        System.out.println("scaled vertices: ");
        for (Vertex v : polygon) {
            System.out.println("x: " + numberFormat.format(v.getX()) + " y: " + numberFormat.format(v.getY()));
        }
        System.out.println();
        yMin = polygon.get(0).getY();
        xMin = polygon.get(0).getX();
        yMax = polygon.get(0).getY();
        xMax = polygon.get(0).getX();
        for (int i = 1; i < polygon.size(); i++) {
            if (polygon.get(i).getY() < yMin)
                yMin = polygon.get(i).getY();
            if (polygon.get(i).getY() > yMax)
                yMax = polygon.get(i).getY();
            if (polygon.get(i).getX() < xMin)
                xMin = polygon.get(i).getX();
            if (polygon.get(i).getX() > xMax)
                xMax = polygon.get(i).getX();
        }
        if (xMax > width) {
            double divider = (xMax / width);
            for (Vertex v : polygon) {
                v.setX(v.getX() / divider);
            }
        }
        if (yMax > height) {
            double  divider =  (yMax / height);
            for (Vertex v : polygon) {
                v.setY(v.getY() / divider);
            }
        }
        // debug print all vertices
        System.out.println("adjusted vertices: ");
        for (Vertex v : polygon) {
            System.out.println("x: " + numberFormat.format(v.getX()) + " y: " + numberFormat.format(v.getY()));
        }
        System.out.println();
        //debug
//        System.out.println("xDiff: " + xDiff);
//        System.out.println("yDiff: " + yDiff);
//        System.out.println("yScaling: " + yScaling);
//        System.out.println("xScaling: " + xScaling);
        System.out.println("scalingFactor: " + scalingFactor);
        // debug print all vertices
        System.out.println("scaled vertices: ");
        for (Vertex v : polygon) {
            System.out.println("x: " + numberFormat.format(v.getX()) + " y: " + numberFormat.format(v.getY()));
        }
//        System.out.println("\nscaled vertices: ");
//        for (Vertex v : polygon) {
//            System.out.println("x: " + numberFormat.format(v.getX()*scalingFactor) + " y: " + numberFormat.format(v.getY()*scalingFactor));
//        }
        System.out.println();
    }

    // draws the polygon to the buffered image and outputs to output.png
    private static void draw() {
        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.darkGray);
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for(int i = 0; i < polygon.size(); i++) {
            xPoints[i] = (int) (polygon.get(i).getX());
            yPoints[i] = (int) (polygon.get(i).getY());
        }

        g2d.fillPolygon(xPoints, yPoints, 6);
        // write out image
        File output = new File("output.png");
        try {
            ImageIO.write(img, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        int multiplier = 10;
        for (Vertex v : polygon) {
            v.setX(v.getX() * multiplier);
            v.setY(v.getY() * multiplier);
        }


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
        updateDrawValues();
        draw();

    }
}
