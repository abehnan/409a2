package q1;

public class Vertex {
    private double x;
    private double y;

    Vertex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    // point 0,0 corresponds to (960, 540)
    public int getScaledX() {
        return (int) ((Star.getWidth()/2)+x*100);
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    // point 0,0 corresponds to (960, 540)
    public int getScaledY() {
        return (int) ((Star.getHeight()/2)+y*100);
    }

    public void setY(double y) {
        this.y = y;
    }
}
