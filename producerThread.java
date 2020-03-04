package program1;

/**
 * Thread that produces nodes for the consumer threads to consume and process.
 */
public class producerThread implements Runnable {
	/**
	 * The maximum number of nodes to produce.
	 */
	private final int MAX_NUM_OF_NODES_TO_PRODUCE = 75;
	/**
	 * The time to wait in idle.
	 */
	private final int IDLE_TIME_IN_MILLISECONDS = 66;
	/**
	 * The counter for the amount of nodes created.
	 */
	private int nodeCount;
	/**
	 * The counter for the amount of times producer thread has awoken.
	 */
	private int wakeUpCount;
	/**
	 * The queue for the processes to produce to.
	 */
	private minHeap minHeap;

	/**
	 * Communicates the flags between producer and consumer threads.
	 */
	private ThreadFlags flags;

	/**
	 * Creates a new producer thread with the given shared resource.
	 *
	 * @param processQueue The queue that holds all processes to add to.
	 * @param fc           The way for threads to communicate the flags they share.
	 */
	public producerThread ( minHeap minHeap, ThreadFlags tf ) {
		this.minHeap = minHeap;
		this.flags = tf;

		this.wakeUpCount = 0;
		this.nodeCount = 0;

	}

	/**
	 * @return The flags that threads use to communicate between each other.
	 */
	public ThreadFlags getFlags () {
		return flags;
	}

	/**
	 * Gets the amount of nodes created.
	 *
	 * @return the node count.
	 */
	public int getNodeCount () {
		return nodeCount;
	}

	/**
	 * Gets the amount of times producer has awoken.
	 *
	 * @return the wake up count.
	 */
	public int wakeUpCount () {
		return wakeUpCount;
	}

	/**
	 * Creates new instance of node.
	 * Generates random numbers to set priority, process id, and time slice
	 *
	 * @return the created node.
	 */
	public Node createNode () {
		Node node = new Node( Functions.getNuminRange( 1, 100 ), Functions.getNuminRange( 10, 30 ) );
		nodeCount++;
		return node;
	}

	/**
	 * @return true if the number of nodes generated is >= to the max number of nodes the producer can make. False otherwise.
	 */
	public boolean isFinished () {
		return this.nodeCount >= this.MAX_NUM_OF_NODES_TO_PRODUCE;
	}

	/**
	 * Gets the remaining nodes to produce, based off of the max number of nodes and the current count of nodes generated.
	 *
	 * @return
	 */
	private int getRemainingNodesToProduce () {
		return this.MAX_NUM_OF_NODES_TO_PRODUCE - this.nodeCount;
	}

	/**
	 * Gets a random number of nodes to produce.
	 * <p>
	 * Handles clamping, thus it's in its own function.
	 *
	 * @return a clamped random number of nodes to produce.
	 */
	private int getRandomNumOfNodesToProduce () {
		int possibility = Functions.getNuminRange( 8, this.MAX_NUM_OF_NODES_TO_PRODUCE / 4 );
		int remainingCount = getRemainingNodesToProduce();
		if ( possibility > remainingCount ) {
			possibility = remainingCount;
		}
		return possibility;
	}

	/**
	 * Waits for the given time in ms.
	 */
	private void idle () {
		try {
			Thread.sleep( IDLE_TIME_IN_MILLISECONDS );
		} catch ( InterruptedException e ) {
			System.out.println( "Producer was interrupted!" );
		}
	}

	/**
	 * Adds nodes to the {@link #minHeap} as it runs.
	 */
	@Override
	public void run () {

		while ( ! isFinished() ) {
			int nodesToProduce = getRandomNumOfNodesToProduce();
			for ( int i = 0; i < nodesToProduce; i++ ) {
				Node producedNode = createNode();
				this.minHeap.add( producedNode );
			}
			System.out.println( String.format( "Producer has produced ~%d new nodes.", nodesToProduce ) );
			System.out.println( "Producer is idling..." );
			idle();
		}

		System.out.println( "Producer has completed its tasks." );
		// Notify consumers that producer has finished.
		flags.producerComplete(true);
	}
}