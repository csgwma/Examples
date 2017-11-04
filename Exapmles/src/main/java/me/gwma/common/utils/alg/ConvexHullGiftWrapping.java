package me.gwma.common.utils.alg;

public class ConvexHullGiftWrapping {

    class Point {

        double x;
        double y;

        public Point(double x, double y){
            super();
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point [x=" + x + ", y=" + y + "]";
        }
    }

    /**
     * Area of triangle P0P1P2 is (1/2) * det([[x0, x1, x2], [y0, y1, y2], [1, 1, 1]]). If the area is positive then the
     * points occur in anti-clockwise order and P2 is to the left of the line P0P1. Since we only care whether the area
     * is positive, we can discard multiplication by 0.5.
     * 
     * @param p0
     * @param p1
     * @param p2
     * @return
     */
    public static double triangleArea(Point p0, Point p1, Point p2) {
        double triangleArea = (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) - (p1.getY() - p0.getY())
                              * (p2.getX() - p0.getX());
        return triangleArea;
    }
}
