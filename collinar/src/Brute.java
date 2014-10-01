import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Brute {

    public static void main(String[] args) {
        setupDrawing();
        List<Point> points = parseArgs(args);
        if (points.size() < 4) {
            return;
        }
        printFourPointsLines(points);
        StdDraw.show(0);
    }

    private static List<Point> parseArgs(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        List<Point> points = new ArrayList<Point>(N);
        for (int i = 0; i < N; i++) {
            points.add(new Point(in.readInt(), in.readInt()));
        }
        return points;
    }

    private static String formatSegment(List<Point> segment) {

        StringBuilder sb = new StringBuilder();
        int nMinus1 = segment.size() - 1;
        for (int i = 0; i < nMinus1; i++) {
            sb.append(segment.get(i)).append(" -> ");
        }
        sb.append(segment.get(nMinus1));
        return sb.toString();
    }

    private static void outputSegment(List<Point> segment) {
        Collections.sort(segment);
        String formattedSegment = formatSegment(segment);
        System.out.println(formattedSegment);
        drawLine(segment);
    }

    private static void drawLine(List<Point> segment) {
        Point first = segment.get(0);
        Point last = segment.get(segment.size() - 1);
        first.drawTo(last);
    }

    private static void setupDrawing() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
    }

    private static void printFourPointsLines(List<Point> points) {

        int N = points.size();

        for (int i = 0; i < N; i++) {
            Point a = points.get(i);
            a.draw();
            for (int j = (i + 1); j < N; j++) {
                Point b = points.get(j);
                double slopeAb = a.slopeTo(b);
                for (int k = (j + 1); k < N; k++) {
                    Point c = points.get(k);
                    double slopeAc = a.slopeTo(c);
                    if (slopeAb != slopeAc) {
                        continue;
                    }
                    for (int l = (k + 1); l < N; l++) {
                        Point d = points.get(l);
                        double slopeAd = a.slopeTo(d);
                        if (slopeAd != slopeAc) {
                            continue;
                        }
                        List<Point> segment = Arrays.asList(a, b, c, d);
                        outputSegment(segment);
                    }
                    
                }
            }
        }
    }
    
    private static List<Point> check6() {
        return Arrays.asList(
                new Point(19000 , 10000),           
                new Point(18000 , 10000),                
                new Point(32000 , 10000),             
                new Point(21000 , 10000),              
                new Point(1234 , 5678),            
                new Point(14000 , 10000)
                );
    }
    private static List<Point> check8() {
        return Arrays.asList(
                new Point(10000 , 0),           
                new Point(0 , 10000),                
                new Point(3000 , 7000),             
                new Point(7000 , 3000),              
                new Point(20000 , 21000),            
                new Point(3000 , 4000),
                new Point(14000 , 15000),            
                new Point(6000 , 7000)
                );
    }
}
