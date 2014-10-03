

public class RectHV {

    private final Point2D topRight;
    private final Point2D bottomLeft;


    public RectHV(double xmin, double ymin,         
            double xmax, double ymax){       
        if (xmin > xmax || ymin > ymax) {
            throw new IllegalArgumentException();
        }
        topRight = new Point2D(xmax, ymax);
        bottomLeft = new Point2D(xmin,ymin);


    }

    public double xmin() {                           // minimum x-coordinate of rectangle
        return bottomLeft.x();
    }
    public double ymin() {                           // minimum y-coordinate of rectangle
        return bottomLeft.y();
    }
    public double xmax() {                           // maximum x-coordinate of rectangle
        return topRight.x();
    }
    public double ymax() {                          // maximum y-coordinate of rectangle
        return topRight.y();
    }
    /**
     * xmin <= x <= xmax && ymin <= y <= ymax
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {             // does this rectangle contain the point p (either inside or on boundary)?
        return (xInRange(p.x()) && yInRange(p.y()));
    }

    private boolean xInRange(double x) {
        return xmin() <= x && x <= xmax();
    }
    private boolean yInRange(double y) {
        return ymin() <= y && y <= ymax();
    }
    public boolean intersects(RectHV that) {          // does this rectangle intersect that rectangle (at one or more points)?
        // easy to reject      
        if (that.ymin() > this.ymax()) {
            return false;
        }
        if (that.ymax() < this.ymin()) {
            return false;
        }
        if (that.xmin() > this.xmax()) {
            return false;
        }
        if (that.xmax() < this.xmin()) {
            return false;
        }
        return true;
    }
    public double distanceTo(Point2D p) {            
        // Euclidean distance from point p to the closest point in rectangle
        if (contains(p)) {
            return 0.0;
        }
        double x = p.x();
        double y = p.y();

        if(xInRange(x)) {
            return Math.min(Math.abs(ymin() - y), Math.abs(y - ymax()));
        }
        else if(yInRange(y)) {
            return Math.min(Math.abs(xmin() - x), Math.abs(x - xmax()));
        }
        else {          
            return Math.min(topRight.distanceTo(p), bottomLeft.distanceTo(p));
        }
    }


    public double distanceSquaredTo(Point2D p) {
        // square of Euclidean distance from point p to closest point in rectangle
        double sqrtDistance = distanceTo(p);
        return sqrtDistance * sqrtDistance;
    }

    public void draw() {   
        // draw to standard draw
        Point2D topLeft = new Point2D(xmin(), ymax());
        Point2D bottomRight = new Point2D(xmax(), ymin());

        bottomLeft.drawTo(topLeft);
        bottomLeft.drawTo(bottomRight);
        topRight.drawTo(topLeft);
        topRight.drawTo(bottomRight);
        StdDraw.show(0);
    }

    @Override
    public String toString() {
        return "RectHV [xmin=" + xmin() + ", ymin=" + ymin() + ", xmax=" + xmax()
                + ", ymax=" + ymax() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RectHV other = (RectHV) obj;
        if (bottomLeft == null) {
            if (other.bottomLeft != null)
                return false;
        } else if (!bottomLeft.equals(other.bottomLeft))
            return false;
        if (topRight == null) {
            if (other.topRight != null)
                return false;
        } else if (!topRight.equals(other.topRight))
            return false;
        return true;
    }













    public static void main(String[] args) {
        checkDraw();
    }

    private static void checkDraw() {
        new RectHV(-10, -10, 20, 20).draw();        
    }




}