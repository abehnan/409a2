package q2;

public class ToeBin {
    private static final ToeBin instance = new ToeBin();
    private ToeBin() {}
    public static ToeBin getInstance() {
        return instance;
    }
}
