// Practical 5 by Aayan Atiq (Stduent no. 16203115)

package dsaii.common;

import java.util.ArrayList;
import java.util.Random;

import dsaii.sequence.LinkedSequence;
import dsaii.sequence.Sequence;
import dsaii.tree.Heap;

public class SortUtils {
	private static Random random = new Random();

	public static void main(String[] args) {
		Sequence<Integer> list = createSequence(new LinkedSequence<Integer>());
		heapSort(list, true);
		System.out.println("heapSort (asc): " + list);
		list = createSequence(new LinkedSequence<Integer>());
		heapSort(list, false);
		System.out.println("heapSort (desc): " + list);
		list = createSequence(new LinkedSequence<Integer>());
		ascQuickSort(list);
		System.out.println("ascQuickSort: " + list);
		list = createSequence(new LinkedSequence<Integer>());
		ascMergeSort(list);
		System.out.println("ascMergeSort: " + list);
		list = createSequence(new LinkedSequence<Integer>());
		mergeSort(list, true);
		System.out.println("mergeSort (asc): " + list);
		list = createSequence(new LinkedSequence<Integer>());
		mergeSort(list, false);
		System.out.println("mergeSort (desc): " + list);
		list = createSequence(new LinkedSequence<Integer>());
		quickSort(list, true);
		System.out.println("quickSort (asc): " + list);

		list = createSequence(new LinkedSequence<Integer>());
		quickSort(list, false);
		System.out.println("quickSort (desc): " + list);
	}

	/**
	 * We create a sequence rather than a list because sequences can be both rank
	 * and position based. This means we can use the most appropriate sequence
	 * implementation for the given sorting algorithm.
	 *
	 * @param list
	 * @return
	 */
	private static Sequence<Integer> createSequence(Sequence<Integer> list) {
		for (int i : new int[] { 12, 5, 7, 19, 13, 11, 6, 8, 2, 4, 25, 21, 14 }) {
			list.insertLast(i);
		}
		return list;
	}

	/**
	 * Heap Sort used position based operations, so a LinkedSequence is the best
	 * implementation to use.
	 *
	 * @param S
	 * @param asc
	 */
	public static <T extends Comparable<T>> void heapSort(Sequence<T> S, boolean asc) {
		Heap<T> heap = new Heap<T>();
		while (!S.isEmpty()) {
			heap.insert(S.remove(S.first()));
		}

		while (!heap.isEmpty()) {
			if (asc)
				S.insertLast(heap.remove());
			else
				S.insertFirst(heap.remove());
		}
	}

	public static <T extends Comparable<T>> void ascMergeSort(Sequence<T> S) {

		if (S.size() > 1) {
			Sequence<T> seqA = new LinkedSequence<>();
			Sequence<T> seqB = new LinkedSequence<>();

			int sectionA, sectionB;

			for (sectionA = 0; sectionA < S.size() / 2; sectionA++) {
				seqA.insertLast(S.elemAtRank(sectionA));
			}

			for (sectionB = S.size() / 2; sectionB < S.size(); sectionB++) {
				seqB.insertLast(S.elemAtRank(sectionB));
			}

			ascMergeSort(seqA);
			ascMergeSort(seqB);
			merge(S, seqA, seqB, true);
		}
	}

	/**
	 * Merge Sort also used position based operations, so again you should use a
	 * LinkedSequence
	 *
	 * @param S
	 * @param asc
	 */
	public static <T extends Comparable<T>> void mergeSort(Sequence<T> S, boolean asc) {
		if (S.size() > 1) {

			// this creates two new sequences to store the two parts of S

			Sequence<T> seqA = new LinkedSequence<>();
			Sequence<T> seqB = new LinkedSequence<>();

			int sectionA, sectionB;
			// Sections S into different parts

			for (sectionA = 0; sectionA < S.size() / 2; sectionA++) {
				seqA.insertLast(S.elemAtRank(sectionA));
			}

			for (sectionB = S.size() / 2; sectionB < S.size(); sectionB++) {
				seqB.insertLast(S.elemAtRank(sectionB));
			}

			// code for recursive calls
			ascMergeSort(seqA);
			ascMergeSort(seqB);

			// this merges two parts of S
			merge(S, seqA, seqB, asc);
		}
	}

	private static <T extends Comparable<T>> void merge(Sequence<T> S, Sequence<T> seqA, Sequence<T> seqB, boolean asc) {
		Sequence<T> Sfinal = new LinkedSequence<>();
		if (asc) {

			// this adds the elements into seqA and seqB

			while (!seqA.isEmpty() && !seqB.isEmpty()) {
				if (seqA.elemAtRank(0).compareTo(seqB.elemAtRank(0)) < 0) {
					Sfinal.insertLast(seqA.remove(seqA.first()));
				} else {
					Sfinal.insertLast(seqB.remove(seqB.first()));
				}
			}

			while (!seqA.isEmpty()) {
				Sfinal.insertLast(seqA.remove(seqA.first()));
			}
			while (!seqB.isEmpty()) {
				Sfinal.insertLast(seqB.remove(seqB.first()));
			}

			// this empties S

			while (!S.isEmpty()) {
				S.remove(S.first());
			}
			//this adds everything into S

			while (!Sfinal.isEmpty()) {
				S.insertLast(Sfinal.remove(Sfinal.first()));
			}

			// code for descending order

		} else {
			while (!seqA.isEmpty() && !seqB.isEmpty()) {
				if (seqA.elemAtRank(0).compareTo(seqB.elemAtRank(0)) < 0) {
					Sfinal.insertLast(seqA.remove(seqA.first()));
				} else {
					Sfinal.insertLast(seqB.remove(seqB.first()));
				}
			}

			while (!seqA.isEmpty()) {
				Sfinal.insertLast(seqA.remove(seqA.first()));
			}

			while (!seqB.isEmpty()) {
				Sfinal.insertLast(seqB.remove(seqB.first()));
			}

			while (!S.isEmpty()) {
				S.remove(S.first());
			}

			while (!Sfinal.isEmpty()) {
				S.insertLast(Sfinal.remove(Sfinal.last()));
			}
		}

	}

	/**
	 *
	 * @param S
	 */
	public static <T extends Comparable<T>> void ascQuickSort(Sequence<T> S) {
		if (S.size() > 1) {
			ArrayList<Sequence<T>> seq = new ArrayList<Sequence<T>>();

			// this partitions into two different parts
			seq = (section(S, S.atRank(random.nextInt(S.size()))));

			//Recursive calls
			ascQuickSort(seq.get(0));
			ascQuickSort(seq.get(1));
			merge(S, seq.get(0), seq.get(1), true);

		}

	}

	private static <T extends Comparable<T>> ArrayList<Sequence<T>> section(Sequence<T> S, Position p) {
		Sequence<T> L = new LinkedSequence<>();
		Sequence<T> E = new LinkedSequence<>();
		Sequence<T> G = new LinkedSequence<>();

		// this removes the pivot element from S

		T x = (T) S.remove(p);
		E.insertLast(x);
		while (!S.isEmpty()) {
			T y = (T) S.remove(S.first());
			if (y.compareTo(x) < 0) {
				L.insertLast(y);
			} else if (y.compareTo(x) == 0) {
				E.insertLast(y);
			} else if (y.compareTo(x) > 0) {
				G.insertLast(y);
			}

		}

		while (!E.isEmpty()) {
			L.insertLast(E.remove(E.first()));
		}
		ArrayList<Sequence<T>> seq = new ArrayList<Sequence<T>>();
		seq.add(L);
		seq.add(G);
		return seq;
	}

	/**
	 *
	 * @param S
	 * @param asc
	 */
	public static <T extends Comparable<T>> void quickSort(Sequence<T> S, boolean asc) {

		if (S.size() > 1) {
			ArrayList<Sequence<T>> seq = new ArrayList<Sequence<T>>();
			seq = (section(S, S.atRank(random.nextInt(S.size()))));
			quickSort(seq.get(0), asc);
			quickSort(seq.get(1), asc);

			// For ascending order

			if (asc) {
				while (!seq.get(0).isEmpty()) {
					S.insertLast(seq.get(0).remove(seq.get(0).first()));
				}
				while (!seq.get(1).isEmpty()) {
					S.insertLast(seq.get(1).remove(seq.get(1).first()));
				}

				// For descending order
			}
			else {
				while (!seq.get(1).isEmpty()) {
					S.insertLast(seq.get(1).remove(seq.get(1).first()));
				}
				while (!seq.get(0).isEmpty()) {
					S.insertLast(seq.get(0).remove(seq.get(0).first()));
				}
			}

		}

	}

}
