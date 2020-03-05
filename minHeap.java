package program1;

import java.util.ArrayList;

/*
/Class minHeap handles the priority queue of processes
*/
public class minHeap {

        //defining the final Object lock for synchronized processes
	private final Object lock;

        //define an ArrayList of nodes for the heap
	private ArrayList< Node > nodes;

        //set the initial capacity of the heap to the array list 
        //set the lock as an Object
	public minHeap (int initCapacity) {
		this.nodes = new ArrayList<>(initCapacity);
		this.lock = new Object();
	}
        
        //heapify function for the minHeap 
	private void heapify ( int n, int indexOfNode ) {
		int indexOfSmallestNode = indexOfNode;
		int indexOfLeftChild = 2 * indexOfNode + 1;
		int indexOfRightChild = 2 * indexOfNode + 2;
                
		// If left child is smaller than root
		if ( indexOfLeftChild < n && this.nodes.get(indexOfLeftChild).compareTo(this.nodes.get(indexOfSmallestNode)) > 0) {			
                    indexOfSmallestNode = indexOfLeftChild;
                }

		// If right child is smaller than the smallest so far
		if ( indexOfRightChild < n && this.nodes.get(indexOfRightChild).compareTo(this.nodes.get(indexOfSmallestNode)) > 0) {
			indexOfSmallestNode = indexOfRightChild;
		}

		// If the index of the smallest node is not the root index
		if ( indexOfSmallestNode != indexOfNode ) {
			// Swap them.
			Node swap = this.nodes.get( indexOfNode );
			this.nodes.set(indexOfNode, this.nodes.get(indexOfSmallestNode));
			this.nodes.set( indexOfSmallestNode, swap );

			// Recursively heapify the affected sub-tree
			heapify(n, indexOfSmallestNode);
		}
	}
        
        //
	public boolean add (Node newProcess) {
		boolean output = false;
		synchronized ( this.lock ) {
			output = this.nodes.add( newProcess );

			// Heapify at the root.
			this.sort();
		}
		return output;
	}

	public void clear () {
                //protects critical region of heap
		synchronized ( this.lock ) {
			this.nodes.clear();
			this.nodes.clear();
		}
	}

	public void sort () {
                //protects critical region of heap from being accessed by a process
		synchronized ( this.lock ) {
			//rearrange the array and build nodes
			for ( int i = this.nodes.size() / 2 - 1; i >= 0; i-- )
				heapify(this.nodes.size(), i );

			//extract an element from processes one at a time
			for ( int i = this.nodes.size() - 1; i >= 0; i-- ) {
				//move current root to end
				Node temp = this.nodes.get( 0 );
				this.nodes.set(0, this.nodes.get( i ) );
				this.nodes.set( i, temp );

				//call max heapify on the reduced processes
				heapify( i, 0 );
			}
		}
	}

        //removes the head from the heap
	public Node removeHead () throws InterruptedException {
                //protects critical region from being accessed by multiple processes
		synchronized ( this.lock ) {
			Node head = this.nodes.remove(0);
	
			this.sort();
			return head;
		}
	}
        
        //returns the size of the heap
	public int size () {
		return this.nodes.size();
	}
        
        //is True when the heap is empty
	public boolean isEmpty () {
		return this.size() == 0;
	}
}
