import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {
	private SearchNode goal;

	private class SearchNode implements Comparable<SearchNode> {
		public int step = 0;
		public SearchNode prev = null;
		public Board board;
		public boolean isTwin;
		public int manhattan = -1;

		public SearchNode(Board b, boolean isTwin) {
			board = b;
			this.isTwin = isTwin;
			if (manhattan == -1) {
				manhattan = board.manhattan();
			}
		}

		public int priority() {
			return manhattan + step;
		}

		public Iterable<SearchNode> neighbors() {
			ArrayList<SearchNode> searchNodeArrayList = new ArrayList<>();
			for (Board board : board.neighbors()) {
				SearchNode searchNode = new SearchNode(board, isTwin);
				searchNode.step = this.step+1;
				searchNode.prev = this;

				searchNodeArrayList.add(searchNode);
			}
			return searchNodeArrayList;
		}

		@Override
		public int compareTo(SearchNode o) {
			if (priority() == o.priority()) {
				return this.manhattan - o.manhattan;
			} else  {
				return priority() - o.priority();
			}
		}
	}

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		if (initial == null) {
			throw new java.lang.IllegalArgumentException();
		}

		MinPQ<SearchNode> pq = new MinPQ<>();

		pq.insert(new SearchNode(initial, false));
		pq.insert(new SearchNode(initial.twin(), true));
		while (true) {
			SearchNode min = pq.delMin();
			if (min.board.isGoal()) {
				goal = min;
				break;
			}

			for (SearchNode neighbour : min.neighbors()) {
				if (!IsExistPrevious(neighbour, min)) {
					pq.insert(neighbour);
				}
			}

			if (pq.isEmpty()) {
				break;
			}
		}
	}

	private boolean IsExistPrevious(SearchNode n, SearchNode parent) {
		SearchNode p = parent;
		if (parent.prev == null || !n.board.equals(parent.prev.board)) {
			return false;
		}

		return true;
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		if (goal.isTwin == true) {
			return false;
		}
		return true;
	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		if (!isSolvable()) {
			return -1;
		}
		return goal.step;
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (!isSolvable()) {
			return null;
		}
		Stack<SearchNode> s = new Stack<>();
		ArrayList<Board> arr = new ArrayList<>();

		SearchNode node = goal;
		while (node != null) {
			s.push(node);
			node = node.prev;
		}

		while (!s.empty()) {
			arr.add(s.pop().board);
		}
		return arr;
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {
		// create initial board from file
		In in = new In("a.txt");
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
