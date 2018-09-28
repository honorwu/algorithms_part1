import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
	private short [] blocks;
	private int dimension;
	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		dimension = blocks.length;
		this.blocks = new short [dimension*dimension];
		for (int i=0; i<blocks.length; i++) {
			for (int j=0; j<blocks[0].length; j++) {
				this.blocks[i*dimension+j] = (short)blocks[i][j];
			}
		}
	}
	// board dimension n
	public int dimension() {
		return dimension;
	}

	// number of blocks out of place
	public int hamming() {
		int hamming = 0;
		for (int i=0; i<dimension*dimension; i++) {
			if (blocks[i] == 0) {
				continue;
			}

			if (blocks[i] != i+1) {
				hamming++;
			}
		}
		return hamming;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int manhattan = 0;
		for (int i=0; i<dimension*dimension; i++) {
			if (blocks[i] == 0) {
				continue;
			}

			int row = (blocks[i]-1) / dimension;
			int col = (blocks[i]-1) % dimension;

			int x = Math.abs(row-i/dimension) + Math.abs(col-i%dimension);
			manhattan += x;
		}

		return manhattan;
	}

	// is this board the goal board?
	public boolean isGoal() {
		if (hamming() == 0) {
			return true;
		}

		return false;
	}

	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		int blank=0;
		int first = 0;
		int second = 0;

		for (int i=0; i<dimension*dimension; i++) {
			if (blocks[i] == 0) {
				blank = i;
				break;
			}
		}

		for (int i=0; i<dimension*dimension; i++) {
			if (i != blank) {
				first = i;
				break;
			}
		}

		for (int i=0; i<dimension*dimension; i++) {
			if (i != blank && i != first) {
				second = i;
				break;
			}
		}

		short t = blocks[first];
		blocks[first] = blocks[second];
		blocks[second] = t;

		Board b = constructBoard(blocks);

		t = blocks[first];
		blocks[first] = blocks[second];
		blocks[second] = t;

		return b;
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (y == this) {
			return true;
		}

		if (y == null) {
			return false;
		}

		if (this.getClass() != y.getClass()) {
			return false;
		}

		Board b = (Board) y;
		if (this.dimension != b.dimension) {
			return false;
		}

		return Arrays.equals(blocks, b.blocks);
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		ArrayList<Board> n = new ArrayList<>();

		int blank = 0;
		for (int i=0; i<dimension*dimension; i++) {
			if (blocks[i] == 0) {
				blank = i;
				break;
			}
		}

		// up
		if (blank >= dimension) {
			blocks[blank] = blocks[blank-dimension];
			blocks[blank-dimension] = 0;
			n.add(constructBoard(blocks));
			blocks[blank-dimension] = blocks[blank];
			blocks[blank] = 0;
		}


		// down
		if (blank + dimension < dimension*dimension) {
			blocks[blank] = blocks[blank+dimension];
			blocks[blank+dimension] = 0;
			n.add(constructBoard(blocks));
			blocks[blank+dimension] = blocks[blank];
			blocks[blank] = 0;
		}


		// left
		if (blank % dimension > 0) {
			blocks[blank] = blocks[blank-1];
			blocks[blank-1] = 0;
			n.add(constructBoard(blocks));
			blocks[blank-1] = blocks[blank];
			blocks[blank] = 0;
		}

		// right
		if (blank % dimension < dimension-1 ) {
			blocks[blank] = blocks[blank+1];
			blocks[blank+1] = 0;
			n.add(constructBoard(blocks));
			blocks[blank+1] = blocks[blank];
			blocks[blank] = 0;
		}

		return n;
	}

	// string representation of this board (in the output format specified below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(dimension + "\n");
		for (int i = 0; i < dimension * dimension; i++) {
			s.append(String.format("%2d ", blocks[i]));
			if (i % dimension == dimension-1) {
				s.append("\n");
			}
		}
		return s.toString();
	}

	private Board constructBoard(short [] blocks) {
		int [][] b = new int [dimension][dimension];
		for (int i=0; i<dimension*dimension; i++) {
			b[i/dimension][i%dimension] = blocks[i];
		}
		return new Board(b);
	}

	// unit tests (not graded)
	public static void main(String[] args) {
		In in = new In("a.txt");
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		System.out.println(initial.hamming());
		System.out.println(initial.manhattan());

		ArrayList<Board> boards = (ArrayList<Board>) initial.neighbors();
		for (Board b : boards) {
			System.out.println(b.toString());
		}
	}
}
