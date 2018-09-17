import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item [] s;
	private int count;
	private int capacity;
	private static int InitialCapacity = 16;

	private class ArrayIterator implements Iterator<Item> {
		private int length;
		private Item [] array;

		ArrayIterator() {
			array = (Item[]) new Object[count];
			for (int i=0; i<count; i++) {
				array[i] = s[i];
			}

			length = count;
		}

		@Override
		public boolean hasNext() {
			return length > 0;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			int index = StdRandom.uniform(0, length);
			Item ret = array[index];
			array[index] = array[length-1];
			array[length-1] = null;

			length--;

			return ret;
		}
	}

	// construct an empty randomized queue
	public RandomizedQueue() {
		capacity = InitialCapacity;
		count = 0;
		s = (Item [])new Object[capacity];
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return count == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return count;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}

		s[count] = item;
		count++;

		if (count >= capacity) {
			capacity = capacity * 2;
			resize(capacity);
		}
	}

	// remove and return a random item
	public Item dequeue() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		int index = StdRandom.uniform(0, count);

		Item ret = s[index];
		s[index] = s[count-1];
		s[count-1] = null;
		count--;

		if (capacity > InitialCapacity && count <= capacity / 4) {
			capacity = capacity / 2;
			resize(capacity);
		}

		return ret;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		int index = StdRandom.uniform(0, count);
		return s[index];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new ArrayIterator();
	}

	private void resize(int size) {
		Item copy[] = (Item []) new Object [size];
		for (int i=0; i<count; i++) {
			copy[i] = s[i];
		}
		s = copy;
	}

	// unit testing (optional)
	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<>();

		rq.enqueue(1);
		rq.enqueue(2);
		rq.enqueue(3);
		rq.enqueue(4);
		rq.enqueue(5);

		Iterator<Integer> a = rq.iterator();
		while (a.hasNext()) {
			System.out.println(a.next());
		}

	}
}

