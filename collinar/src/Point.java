import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Point implements Comparable<Point> {

    private static final double POSITIVE_ZERO = 0.0 / 1;
    /**
     * compare points by slope to this point
     */
    public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();

    private final int x;
    private final int y;

    public Point(int x, int y) {
        // construct the point (x, y)
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(x, y, that.x, that.y);
    }

    public String toString() { // string representation
        return "(" + x + "," + y + ")";
    }

    /**
     * // is this point lexicographically smaller than that point?
     */
    public int compareTo(Point that) {
        if (y < that.y) {
            return -1;
        }
        if (y > that.y) {
            return 1;
        }
        if (x < that.x) {
            return -1;
        }
        if (x > that.x) {
            return 1;
        }
        return 0;
    }

    public double slopeTo(Point that) {
        // the slope between this point and that point
        double nom = y - that.y;
        double den = x - that.x;
        if (nom == 0 && den == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (den == 0) {
            return Double.POSITIVE_INFINITY;
        }
        if (nom == 0) {
            return POSITIVE_ZERO;
        }

        return nom / den;
    }

    private class SlopeComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);
            if (slope1 > slope2)
                return 1;
            if (slope1 < slope2) {
                return -1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        assertPoints();
        assertComparator();
    }

    private static void assertPoints() {
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(3, 3);
        Point d = new Point(4, 4);

        Point ax = new Point(1, 5);
        Point ay = new Point(5, 1);

        Point p = new Point(0, 5);
        Point q = new Point(0, 0);

        assert (a.slopeTo(a) == Double.NEGATIVE_INFINITY);
        assert (a.compareTo(a) == 0);

        assert (a.slopeTo(ax) == Double.POSITIVE_INFINITY);
        assert (a.compareTo(ax) == -1);

        assert (a.slopeTo(ay) == 0.0);
        assert (a.compareTo(ay) == -1);

        assert (a.slopeTo(b) == 1.00);
        assert (a.compareTo(b) == -1);
        assert (a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(c) == a.slopeTo(d));

        assert (a.slopeTo(p) == -4.00);
        assert (a.compareTo(p) == -1);
        assert (a.compareTo(q) == 1);

    }

    private static void assertComparator() {
        List<Point> points = Arrays.asList(new Point(0, 0), new Point(1, 1),
                new Point(3, 5), new Point(2, 5), new Point(1, 0), new Point(5,
                        1));

        Point relative = points.get(1); // 1,1
        Collections.sort(points, relative.SLOPE_ORDER);

        List<Point> expectedPoints = Arrays.asList(new Point(1, 1), new Point(
                5, 1), new Point(0, 0), new Point(3, 5), new Point(2, 5),
                new Point(1, 0));

        for (int i = 0; i < points.size(); i++) {
            Point actual = points.get(i);
            Point expected = expectedPoints.get(i);
            assert (actual.compareTo(expected) == 0);
        }

    }
}
