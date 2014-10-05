import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PointSET {
    SET<Point2D> set;

    public PointSET()  {                           
        // constructs an empty set of points
        set = new SET<Point2D>();
    }  
    public boolean isEmpty()  {                     
        // is the set empty?
        return set.isEmpty();
    }
    public int size()   {                            
        // number of points in the set
        return set.size();
    }
    public void insert(Point2D p){                   
        // add the point p to the set (if it is not already in the set)
        set.add(p);
    }
    public boolean contains(Point2D p)  {           
        // does the set contain the point p?
        return set.contains(p);
    }
    public void draw()  {                  
        // draw all of the points to standard draw
        for (Point2D p : set) {
            p.draw();
        }
        StdDraw.show();      
    }
    public Iterable<Point2D> range(RectHV rect) {     
        // all points in the set that are inside the rectangle
        List<Point2D> iterable = new LinkedList<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                iterable.add(p);
            }
        }
        return iterable;
    }
    public Point2D nearest(Point2D p) {  
        // a nearest neighbor in the set to p; null if set is empty
        Point2D nearesetPoint = null;
        double closestDistance = -1;
        for (Point2D point : set) {
            double distance = point.distanceTo(p);
            if (closestDistance < 0) {
                // this is always true for the first point
                closestDistance = distance;
                nearesetPoint = point;
            }
            else if (distance < closestDistance) {
                closestDistance = distance;
                nearesetPoint = point;
            }
        }
        return nearesetPoint;     
    }



    public static void main(String[] args) {
        testSize();
        testContains();
        testNearest();
        checkDrawPoints();
    }
    private static void testNearest() {
        Point2D p = new Point2D(0, 0);

        PointSET ps = new PointSET();
        // empty is null
        assert ps.nearest(p) == null;
        
        // only one , so it is returned
        Point2D pp = new Point2D(0.9, 0.9);
        ps.insert(pp);
        assert ps.nearest(p) == pp;
        
        
        // new point is closer
        pp = new Point2D(0.5, 0.5);
        ps.insert(pp);
        assert ps.nearest(p) == pp;
        
        // two same points, so any is returned
        pp = new Point2D(0.5, 0.5);
        ps.insert(pp);
        assert ps.nearest(p).equals(new Point2D(0.5, 0.5));

    }
    private static void checkDrawPoints() {
        int N = 100;
        Random r = new Random();
        PointSET ps = new PointSET();
        for (int i = 0; i < N; i++) {
            ps.insert(new Point2D(r.nextDouble(), r.nextDouble()));
        }
        //ps.draw();
    }

    private static void testSize() {

        PointSET ps = new PointSET();

        assert ps.isEmpty();


        int N = 100;
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            ps.insert(new Point2D(r.nextDouble(), r.nextDouble()));
        }

        assert ps.size() == N;
    }

    private static void testContains() {
        PointSET ps = new PointSET();

        List<Point2D> inside = Arrays.asList(
                new Point2D (1,0),
                new Point2D (0,1),
                new Point2D (1,1),
                new Point2D (0,0)                               
                );

        List<Point2D> outside = Arrays.asList(
                new Point2D (0.1,0),
                new Point2D (0,0.1),
                new Point2D (0.1,0.1),
                new Point2D (0.11,0.11)                               
                );

        for (Point2D p : inside) {
            ps.insert(p);
        }

        for (Point2D p : inside) {
            assert ps.contains(p);
        }

        for (Point2D p : outside) {
            assert !ps.contains(p);
        }
    }
}