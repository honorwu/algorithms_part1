import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

	private ArrayList<LineSegment> lineSegment = new ArrayList<>();
	private Point[] ps;

	// finds all line segments containing 4 points
	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new java.lang.IllegalArgumentException();
		}

		ps = new Point[points.length];

		for (int i=0; i<points.length; i++) {
			if (points[i] == null) {
				throw new java.lang.IllegalArgumentException();
			}

			ps[i] = points[i];
		}

		Arrays.sort(ps);

		for (int i=0; i<ps.length-1; i++) {
			if (ps[i].compareTo(ps[i+1]) == 0) {
				throw new java.lang.IllegalArgumentException();
			}
		}

		fast();
	}

	private void fast() {
		for (int i=0; i<ps.length; i++) {
			int copyIndex = 0;
			Point[] copyPoints = new Point[ps.length-1];
			for (int j=0; j<ps.length; j++) {
				if (i != j) {
					copyPoints[copyIndex++] = ps[j];
				}
			}

			Arrays.sort(copyPoints, ps[i].slopeOrder());

			if (copyIndex == 0) {
				continue;
			}

			double lastSlope = ps[i].slopeTo(copyPoints[0]);
			int k=1;

			int j=1;
			for (j=1; j<copyPoints.length; j++) {
				double newSlope = ps[i].slopeTo(copyPoints[j]);

				if (newSlope == lastSlope) {
					k++;
				} else {
					if (k>=3) {
						Point[] result = new Point[3];
						result[0] = ps[i];
						result[1] = copyPoints[j-k];
						result[2] = copyPoints[j-1];
						Arrays.sort(result);

						if (ps[i].compareTo(result[0]) == 0) {
							LineSegment ls = new LineSegment(result[0], result[2]);
							lineSegment.add(ls);
						}
					}

					lastSlope = newSlope;
					k=1;
				}
			}

			if (k>=3) {
				Point[] result = new Point[3];
				result[0] = ps[i];
				result[1] = copyPoints[j-k];
				result[2] = copyPoints[j-1];
				Arrays.sort(result);

				if (ps[i].compareTo(result[0]) == 0) {
					LineSegment ls = new LineSegment(result[0], result[2]);
					lineSegment.add(ls);
				}
			}
		}
	}

	// the number of line segments
	public int numberOfSegments() {
		return lineSegment.size();
	}

	// the line segments
	public LineSegment[] segments() {
		LineSegment ls[] = new LineSegment[lineSegment.size()];
		for (int i=0; i<lineSegment.size(); i++){
			ls[i] = lineSegment.get(i);
		}
		return ls;
	}

	public static void main(String[] args) {
		Point[] a = new Point[8];
		a[0] = new Point(10000,0);
		a[1] = new Point(0,10000);
		a[2] = new Point(3000,7000);
		a[3] = new Point(7000,3000);
		a[4] = new Point(20000,21000);
		a[5] = new Point(3000,4000);
		a[6] = new Point(14000,15000);
		a[7] = new Point(6000,7000);


		FastCollinearPoints fast = new FastCollinearPoints(a);
		for (int i=0; i<fast.segments().length; i++) {
			System.out.println(fast.segments()[i].toString());
		}
	}
}