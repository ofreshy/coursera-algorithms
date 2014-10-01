import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Fast {   
    private final List<Point> points;
    private final int N;
    private final int maxIndex;
    private final Set<PointSlope> pointSlopes = new HashSet<PointSlope>();
    
    private Fast(List<Point> points) {
        this.points = points;
        Collections.sort(points);
        this.N = points.size();
        this.maxIndex = N - 3;
    }
    
    public static void main(String[] args) {
        setupDrawing();
        int execution = 0;
        List<Point> points;
        if (execution == 6) {
            points = check6();
        }
        else if (execution == 8) {
            points = check8();
        }
        else {
            points = parseArgs(args);
        }
        if (points.size() < 4) {
            return;
        }
        Fast fast = new Fast(points);
        fast.printFourPointsLines();
        StdDraw.show(0);
    }

    private void printFourPointsLines() {
        for (int i = 0; i < maxIndex; i++) {
            Point first = points.get(i);
            List<Point> sortedByFirst = new ArrayList<Point>(points);
            Collections.sort(sortedByFirst, first.SLOPE_ORDER);
            printFromSorted(sortedByFirst, first);
            first.draw();
        }
       
        for (int i = maxIndex; i < N; i++) {
            points.get(i).draw();
        }
        
    }
    
    private void printFromSorted(List<Point> sortedByFirst, Point first) {
        Point p = sortedByFirst.get(1);
        double prevSlope = first.slopeTo(p);
        Segment seg = new Segment(prevSlope, first, p);

        for (int i = 2; i < N; i++) {
            p = sortedByFirst.get(i);
            double currentSlope = first.slopeTo(p);
            if (currentSlope == Double.NEGATIVE_INFINITY) {
                continue;
            }
           
            if (prevSlope == currentSlope) {
                seg.add(p);
                continue;
            } 
            // we got a new slope,so print segement if valid
            printSegmentIfValid(seg);
            prevSlope = currentSlope;
            seg = new Segment(currentSlope, first, p);   
        }   
        printSegmentIfValid(seg);
    }
    
    private void printSegmentIfValid(Segment seg) {
        if (!seg.valid()) {
            return;
        }
        seg.sort();
        PointSlope ps = new PointSlope(seg.slope, seg.ps.getFirst());
        if (!pointSlopes.add(ps)) {
            return;
        }
        
       
        
        System.out.println(seg.toString());
        seg.draw();
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
   
    private static void setupDrawing() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
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
    
    private static class PointSlope {
        private final double slope;
        private final Point point;
        public PointSlope(double slope, Point point) {
            this.slope = slope;
            this.point = point;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + point.hashCode();
            long temp;
            temp = Double.doubleToLongBits(slope);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PointSlope other = (PointSlope) obj;
            if (point == null) {
                if (other.point != null)
                    return false;
            } 
            
            if (point.compareTo(other.point) != 0) {
                return false;
            }
            if (Double.doubleToLongBits(slope) != Double
                    .doubleToLongBits(other.slope))
                return false;
            return true;
        }
        
    }
    
    private class Segment {
        private final double slope;
        private final LinkedList<Point> ps = new LinkedList<Point>();
        
        Segment(double slope, Point... points) {
            this.slope = slope;
            for (Point p : points)
                add(p);
        }
        
        public void draw() {
            ps.getFirst().drawTo(ps.getLast());     
        }

        void sort() {
            Collections.sort(ps);
        }
        
        void add(Point p) {
            ps.add(p);
        }
        
        boolean valid() {
            return ps.size() > 3;
        }
        
        public String toString() {
            StringBuilder sb = new StringBuilder();
            int nMinus1 = ps.size() - 1;
            for (int i = 0; i < nMinus1; i++) {
                sb.append(ps.get(i)).append(" -> ");
            }
            sb.append(ps.get(nMinus1));
            return sb.toString();
        }
        
    }
}
