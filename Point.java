import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
	private int x;
	private int y;

	// constructs the point (x, y)
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// draws this point
	public void draw() {
		StdDraw.point(this.x, this.y);
	}

	// draws the line segment from this point to that point
	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// string representation
	public String toString() {
		String str = "x:" + this.x + " y:" + this.y;
		return str;
	}

	// compare two points by y-coordinates, breaking ties by x-coordinates
	public int compareTo(Point that) {
		if (this.y > that.y) return 1;
		if (this.y < that.y) return -1;
		if (this.x > that.x) return 1;
		if (this.x < that.x) return -1;

		return 0;
	}

	// the slope between this point and that point
	public double slopeTo(Point that) {
		if (this.x == that.x && this.y == that.y) {
			return Double.NEGATIVE_INFINITY;
		} else if (this.x == that.x) {
			return Double.POSITIVE_INFINITY;
		} else if (this.y == that.y) {
			return 0.0;
		} else {
			return (double)(this.y-that.y)/(this.x - that.x);
		}
	}

	// compare two points by slopes they make with this point
	public Comparator<Point> slopeOrder() {
		return new MySlopeOrder();
	}

	private class MySlopeOrder implements Comparator<Point> {
		@Override
		public int compare(Point o1, Point o2) {
			double s1 = slopeTo(o1);
			double s2 = slopeTo(o2);

			if (s1 < s2) {
				return -1;
			}

			if (s1 > s2){
				return 1;
			}
			return 0;
		}
	}
}