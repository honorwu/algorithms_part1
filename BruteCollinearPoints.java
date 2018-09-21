import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> lineSegment = new ArrayList<>();
	private Point[] ps;

	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
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

		brute();
	}

	private void brute() {
		for (int a = 0; a<ps.length; a++) {
			for (int b=a+1; b<ps.length; b++) {
				for (int c=b+1; c<ps.length; c++) {
					for (int d=c+1; d<ps.length; d++) {
						if (ps[a].slopeTo(ps[b]) == ps[b].slopeTo(ps[c]) &&
							ps[b].slopeTo(ps[c]) == ps[c].slopeTo(ps[d])) {
							LineSegment ls = new LineSegment(ps[a], ps[d]);
							lineSegment.add(ls);
						}
					}
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
}