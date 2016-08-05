package me.gwma.common.utils.alg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

class Point implements Comparable<Point> {

    double x, y;

    public int compareTo(Point p) {
        if (this.x == p.x) {
            if (this.y - p.y > 0) {
                return 1;
            } else if (this.y - p.y < 0) {
                return -1;
            } else {
                return 0;
            }
        } else {
            if (this.x - p.x > 0) {
                return 1;
            } else if (this.x - p.x < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

}

public class ConvexHull {

    public static double cross(Point O, Point A, Point B) {
        return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }

    public static Point[] convexHullPoints(Point[] P) {

        if (P.length > 1) {
            int n = P.length, k = 0;
            Point[] H = new Point[2 * n];

            Arrays.sort(P);

            // Build lower hull
            for (int i = 0; i < n; ++i) {
                while (k >= 2 && cross(H[k - 2], H[k - 1], P[i]) <= 0)
                    k--;
                H[k++] = P[i];
            }

            // Build upper hull
            for (int i = n - 2, t = k + 1; i >= 0; i--) {
                while (k >= t && cross(H[k - 2], H[k - 1], P[i]) <= 0)
                    k--;
                H[k++] = P[i];
            }
            if (k > 1) {
                H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a
                                                     // duplicate
            }
            return H;
        } else if (P.length <= 1) {
            return P;
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader f = new BufferedReader(new FileReader("hull.in")); // "hull.in" Input Sample => size x y x y x y
                                                                          // x y
        StringTokenizer st = new StringTokenizer(f.readLine());
        Point[] p = new Point[Integer.parseInt(st.nextToken())];
        for (int i = 0; i < p.length; i++) {
            p[i] = new Point();
            p[i].x = Integer.parseInt(st.nextToken()); // Read X coordinate
            p[i].y = Integer.parseInt(st.nextToken()); // Read y coordinate
        }

        Point[] hull = convexHullPoints(p).clone();

        for (int i = 0; i < hull.length; i++) {
            if (hull[i] != null) System.out.print(hull[i]);
        }
    }

}
