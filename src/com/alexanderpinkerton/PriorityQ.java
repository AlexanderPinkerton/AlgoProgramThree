package com.alexanderpinkerton;


import java.util.Arrays;

public class PriorityQ<U> {

    private static final int DEFAULT_CAPACITY = 50;

    protected int count;
    protected U[] tree;
    protected int modCount;


    public PriorityQ()
    {
        count = 0;
        tree = (U[]) new Object[DEFAULT_CAPACITY];
    }

    public PriorityQ(U element)
    {
        count = 1;
        tree = (U[]) new Object[DEFAULT_CAPACITY];
        tree[0] = element;
    }


    /**
     * Adds the specified element to this heap in the appropriate
     * position according to its key value.
     *
     * @param obj the element to be added to the heap
     */
    public void addElement(U obj)
    {
        //PrioritizedObject<T> obj = new PrioritizedObject<T>(object, priority);
        if (count == tree.length)
            expandCapacity();

        tree[count] = obj;
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

        temp = tree[next];

        while ((next != 0) &&
                (((Comparable)temp).compareTo(tree[(next-1)/2]) < 0))
        {

            tree[next] = tree[(next-1)/2];
            next = (next-1)/2;
        }

        tree[next] = temp;
    }


    public U removeMin()
    {
        U minElement = tree[0];
        tree[0] = tree[count-1];
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

        if ((tree[left] == null) && (tree[right] == null))
            next = count;
        else if (tree[right] == null)
            next = left;
        else if (((Comparable)tree[left]).compareTo(tree[right]) < 0)
            next = left;
        else
            next = right;
        temp = tree[node];

        while ((next < count) &&
                (((Comparable)tree[next]).compareTo(temp) < 0))
        {
            tree[node] = tree[next];
            node = next;
            left = 2 * node + 1;
            right = 2 * (node + 1);
            if ((tree[left] == null) && (tree[right] == null))
                next = count;
            else if (tree[right] == null)
                next = left;
            else if (((Comparable)tree[left]).compareTo(tree[right]) < 0)
                next = left;
            else
                next = right;
        }
        tree[node] = temp;
    }


    public U findMin()
    {
        return tree[0];
    }



    /**
     * Private method to expand capacity if full.
     */
    protected void expandCapacity()
    {
        tree = Arrays.copyOf(tree, tree.length * 2);
    }


    public U getRootElement()
    {
        return tree[0];
    }

    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return (count == 0);
    }

    /**
     * Returns the integer size of this binary tree.
     *
     * @return the integer size of this binary tree
     */
    public int size()
    {
        return count;
    }

    /**
     * Returns true if this tree contains an element that matches the
     * specified target element and false otherwise.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the element is in this tree
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

        for (int i = 0; i < tree.length && !found; i++)
            if (tree[i] != null)
                if (targetElement.equals(tree[i]))
                {
                    found = true;
                    temp = tree[i];
                }
        return temp;
    }


    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}


