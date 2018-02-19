package q1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

class Star {
    private static final DecimalFormat numberFormat = new DecimalFormat("0.###E0");
    private static final int width = 1920;
    private static final int height = 1080;
    private static final ArrayList<Vertex> polygon = new ArrayList<>(6);
    private static final BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
    private static final Graphics2D g2d = img.createGraphics();

    // updates the vertices before drawing
    private static void updateDrawValues() {

        // align the values with the image
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
        for (Vertex v : polygon) {
            v.setX(v.getX() - xMin);
            v.setY(v.getY() - yMin);
        }

        // debug
        System.out.println("aligned vertices: ");
        printPolygon();

        // choose smallest scaling value
        double xDiff = xMax - xMin;
        double yDiff = yMax - yMin;
        double yScaling = (double) height / yDiff;
        double xScaling = (double) width / xDiff;
        double scalingFactor = Math.min(xScaling, yScaling);



        // scale up the vertices
        for (Vertex v : polygon) {
            v.setX(v.getX() * scalingFactor);
            v.setY(v.getY() * scalingFactor);
        }

        // debug
        System.out.println("xDiff: " + numberFormat.format(xDiff));
        System.out.println("yDiff: " + numberFormat.format(yDiff));
        System.out.println("xScaling: " + numberFormat.format(xScaling));
        System.out.println("yScaling: " + numberFormat.format(yScaling));
        System.out.println("scalingFactor: " + numberFormat.format(scalingFactor));
        System.out.println("\nscaled-up vertices: ");
        printPolygon();
    }

    // draws the polygon to the buffered image and outputs to output.png
    private static void draw() {
        updateDrawValues();
        g2d.setColor(Color.lightGray);
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

    public static ArrayList<Vertex> getPolygon() {
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

        // debug
        System.out.println("initial vertices: ");
        printPolygon();

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

        // debug
        System.out.println("\nmodified vertices: ");
        printPolygon();

        // output
        draw();

    }

    private static void printPolygon() {
        for (Vertex v : polygon) {
            System.out.printf("x = %-10s y = %-10s\n", numberFormat.format(v.getX()), numberFormat.format(v.getY()));
        }
        System.out.println();
    }
}
