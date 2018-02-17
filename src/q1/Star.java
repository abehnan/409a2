package q1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Star {
    private static final int width = 1920;
    private static final int height = 1080;
    private static LinkedList<Vertex> polygon = new LinkedList<>();
    private static BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
    private static Graphics2D g2d = img.createGraphics();

    // draws the polygon to the image
    private static void draw() {
        g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(5));
        for (int i = 0; i < polygon.size()-1; i++) {
            g2d.drawLine(polygon.get(i).getScaledX(), polygon.get(i).getScaledY(), polygon.get(i+1).getScaledX(), polygon.get(i+1).getScaledY());
        }
        g2d.drawLine(polygon.getLast().getScaledX(), polygon.getLast().getScaledY(), polygon.getFirst().getScaledX(), polygon.getFirst().getScaledY());

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

        // draw our polygon
        initializeImage();
        draw();

    }
}
