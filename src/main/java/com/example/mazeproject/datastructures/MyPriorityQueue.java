package com.example.mazeproject.datastructures;

import java.util.ArrayList;
import java.util.Comparator;

public class MyPriorityQueue<T> {
    private ArrayList<T> heap;
    private Comparator<T> comparator;

    public MyPriorityQueue(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public void add(T item) {
        heap.add(item);
        siftUp(heap.size() - 1); // Move it up to correct position
    }

    public T poll() {
        if (isEmpty()) return null;

        T result = heap.get(0);

        T lastItem = heap.remove(heap.size() - 1);
        if (!isEmpty()) {
            heap.set(0, lastItem);
            siftDown(0);
        }

        return result;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public boolean contains(T item) {
        return heap.contains(item);
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T item = heap.get(index);
            T parent = heap.get(parentIndex);

            if (comparator.compare(item, parent) < 0) {
                heap.set(index, parent);
                heap.set(parentIndex, item);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int size = heap.size();
        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && comparator.compare(heap.get(leftChild), heap.get(smallest)) < 0) {
                smallest = leftChild;
            }
            if (rightChild < size && comparator.compare(heap.get(rightChild), heap.get(smallest)) < 0) {
                smallest = rightChild;
            }

            if (smallest != index) {
                T temp = heap.get(index);
                heap.set(index, heap.get(smallest));
                heap.set(smallest, temp);
                index = smallest;
            } else {
                break;
            }
        }
    }
}

