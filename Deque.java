import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private class Node {
		public Item value;
		Node prev;
		Node next;
	}

	private class ListIterator implements Iterator<Item> {
		private Node current = head;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}

			Item ret = current.value;
			current = current.next;
			return ret;
		}
	}

	private Node head;
	private Node tail;

	private int count;

	// construct an empty deque
	public Deque() {
		count = 0;
		head = null;
		tail = null;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return count == 0;
	}

	// return the number of items on the deque
	public int size() {
		return count;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}

		Node node = new Node();
		node.value = item;
		Node p = head;
		head = node;
		head.next = p;

		if (p != null) {
			p.prev = head;
		} else {
			tail = head;
		}

		count++;
	}

	// add the item to the end
	public void addLast(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}

		Node node = new Node();
		node.value = item;
		Node p = tail;
		tail = node;
		tail.prev = p;

		if (p != null) {
			p.next = tail;
		} else {
			head = tail;
		}

		count++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		count--;
		Node first = head;
		head = head.next;
		if (head == null) {
			tail = null;
		} else {
			head.prev = null;
		}
		return first.value;
	}

	// remove and return the item from the end
	public Item removeLast() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		count--;

		Node last = tail;
		tail = tail.prev;
		if (tail == null) {
			head = null;
		} else {
			tail.next = null;
		}
		return last.value;
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	// unit testing (optional)
	public static void main(String[] args) {
		Deque<Integer> s = new Deque<Integer>();

		s.addFirst(1);
		s.addLast(2);
		s.removeFirst();
		s.removeLast();

		Iterator<Integer> iter = s.iterator();

		while (iter.hasNext()) {
			System.out.println(iter.next());
		}

	}
}