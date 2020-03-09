package program1;

import java.util.ArrayList;

/**
* Class minHeap orders, removes, and adds processes/nodes to heap.
* @author Dante & Antoine
*/
public class minHeap {

    // A lock to ensure synchronization of threads
    private final Object lock;

    // Array of processes (processes)
    private ArrayList< Node > processes;

    /**
     * Initialize the heap to a defined size
     * @param initCapacity the amount of processes to be dealt with
     */
    public minHeap (int initCapacity) {
            this.processes = new ArrayList<>(initCapacity);
            this.lock = new Object();
    }

    /**
     * Apply the algorthm to heapify the array
     * @param n the size of the array
     * @param index the index of the last element in the array
     */ 
    private void heapify ( int n, int index ) {
            int smallest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            // If left child is smaller than root
            if ( left < n && this.processes.get(left).compareTo(this.processes.get(smallest)) > 0) {			
                smallest = left;
            }

            // If right child is smaller than the smallest so far
            if ( right < n && this.processes.get(right).compareTo(this.processes.get(smallest)) > 0) {
                    smallest = right;
            }

            // If the index of the smallest node is not the root index
            if ( smallest != index ) {
                    // Swap nodes (lowest number value priority at the top)
                    Node swap = this.processes.get(index );
                    this.processes.set(index, this.processes.get(smallest));
                    this.processes.set(smallest, swap );

                    // Recursively heapify the affected sub-tree
                    heapify(n, smallest);
            }
    }
    
    /**
     * Provide boundaries for the heapify algorithm to be applied on.
     */
    public void sort () {
            
        synchronized ( this.lock ) {

                for ( int i = this.processes.size() / 2 - 1; i >= 0; i-- )
                    heapify(this.processes.size(), i );

                for ( int i = this.processes.size() - 1; i >= 0; i-- ) {

                    // Move current root to end
                    Node temp = this.processes.get( 0 );
                    this.processes.set(0, this.processes.get( i ) );
                    this.processes.set( i, temp );

                    heapify( i, 0 );
                }
            }
    }
    
    /**
     * Remove the first entry in the array.
     * @return the node to be consumed
     * @throws InterruptedException thrown if the array is empty
     */
    public Node consumeHead () throws InterruptedException {
            
        synchronized ( this.lock ) {
            Node head = this.processes.remove(0);

            this.sort();
            return head;
        }
    }

    /**
     * Add a new node to the array.
     * @param newProcess new process to be added to the heap
     * @return true if it was able to successfully addNode a process.
     */
    public boolean addNode (Node newProcess) {
        boolean output = false;
        synchronized ( this.lock ) {
                output = this.processes.add( newProcess );
                this.sort();
        }
        return output;
    }

    /** 
     * Remove all the processes from the array.
     */
    public void clear () {
            
        synchronized ( this.lock ) {
                this.processes.clear();
                this.processes.clear();
        }
    }

    /**
     * Return the amount of processes.
     * @return size of the process array
     */
    public int size () {
        return this.processes.size();
    }

    /**
     * Determines if the array is empty or not.
     * @return true if the array is 0.
     */
    public boolean isEmpty () {
        return this.size() == 0;
    }
}
