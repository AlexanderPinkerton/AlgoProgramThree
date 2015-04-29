package com.alexanderpinkerton;


import java.util.Arrays;

/**
 * Created by Alexander Pinkerton on 4/15/15.
 */

public class PriorityQ<U> {

    private static final int DEFAULT_CAPACITY = 50;

    protected int count;
    protected U[] heap;
    protected int modCount;


    public PriorityQ()
    {
        count = 0;
        heap = (U[]) new Object[DEFAULT_CAPACITY];
    }

    public PriorityQ(U element)
    {
        count = 1;
        heap = (U[]) new Object[DEFAULT_CAPACITY];
        heap[0] = element;
    }


    /**
     * Adds the specified element to this heap in the appropriate
     * position according to its key value.
     *
     * @param obj the element to be added to the heap
     */
    public void addElement(U obj)
    {
        if (count == heap.length)
            expandCapacity();

        heap[count] = obj;
        count++;
        modCount++;

        if (count > 1)
            heapifyAdd();
    }

    /**
     * Reorders this heap to maintain the ordering property after
     * adding a node.
     */
    private void heapifyAdd()
    {
        U temp;
        int next = count - 1;

        temp = heap[next];

        while ((next != 0) &&
                (((Comparable)temp).compareTo(heap[(next-1)/2]) < 0))
        {

            heap[next] = heap[(next-1)/2];
            next = (next-1)/2;
        }

        heap[next] = temp;
    }


    public U removeMin()
    {
        U minElement = heap[0];
        heap[0] = heap[count-1];
        heapifyRemove();
        count--;
        modCount--;

        return minElement;
    }

    /**
     * Reorders this heap to maintain the ordering property
     * after the minimum element has been removed.
     */
    private void heapifyRemove()
    {
        U temp;
        int node = 0;
        int left = 1;
        int right = 2;
        int next;

        if ((heap[left] == null) && (heap[right] == null))
            next = count;
        else if (heap[right] == null)
            next = left;
        else if (((Comparable) heap[left]).compareTo(heap[right]) < 0)
            next = left;
        else
            next = right;
        temp = heap[node];

        while ((next < count) &&
                (((Comparable) heap[next]).compareTo(temp) < 0))
        {
            heap[node] = heap[next];
            node = next;
            left = 2 * node + 1;
            right = 2 * (node + 1);
            if ((heap[left] == null) && (heap[right] == null))
                next = count;
            else if (heap[right] == null)
                next = left;
            else if (((Comparable) heap[left]).compareTo(heap[right]) < 0)
                next = left;
            else
                next = right;
        }
        heap[node] = temp;
    }

    /**
     * This method will ensure heap order from the given index.
     * @param i
     */
    public void minHeapify(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        int smallest = i;

        if (left < size() && lessThan(left, smallest))
            smallest = left;

        if (right < size() && lessThan(right, smallest))
            smallest = right;

        if (smallest != i) {
            swap(smallest, i);
            minHeapify(smallest);
        }
    }

    /**
     * Simple Swapping method.
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        U t = heap[i];
        heap[i] = heap[j];
        heap[j] = t;
    }

    public boolean lessThan(int i, int j) {
        Graph.Vertex v1 = (Graph.Vertex)heap[i];
        Graph.Vertex v2 = (Graph.Vertex)heap[j];

        return v1.compareTo(v2) < 0;
    }


    public U findMin()
    {
        return heap[0];
    }



    /**
     * Private method to expand capacity if full.
     */
    protected void expandCapacity()
    {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }


    public U getRootElement()
    {
        return heap[0];
    }

    /**
     * Returns true if this binary heap is empty and false otherwise.
     *
     * @return true if this binary heap is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return (count == 0);
    }

    /**
     * Returns the integer size of this binary heap.
     *
     * @return the integer size of this binary heap
     */
    public int size()
    {
        return count;
    }

    /**
     * Returns true if this heap contains an element that matches the
     * specified target element and false otherwise.
     *
     * @param targetElement the element being sought in the heap
     * @return true if the element is in this heap
     */
    public boolean contains(U targetElement)
    {
        U temp;
        boolean found = false;

        try
        {
            temp = find(targetElement);
            found = true;
        }
        catch (Exception ElementNotFoundException)
        {
            found = false;
        }

        return found;
    }


    public U find(U targetElement)
    {
        U temp = null;
        boolean found = false;

        for (int i = 0; i < heap.length && !found; i++)
            if (heap[i] != null)
                if (targetElement.equals(heap[i]))
                {
                    found = true;
                    temp = heap[i];
                }
        return temp;
    }


    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}


