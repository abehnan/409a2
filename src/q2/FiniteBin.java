package q2;

public class FiniteBin {
    private int count;

    FiniteBin(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
