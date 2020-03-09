package program1;

/*
 * Class producerThread produces nodes for the consumer threads to consume.
 * @author Dante
 */
public class producerThread implements Runnable {

    // the amount of time a process is rest
    private final long restTime = 33;

    // maximum number of nodes to produce
    private final int maxNodes = 75;

    // total amount of nodes created
    private int totalNodesCreated;

    // heap for processes/ nodes to get arranged into
    private minHeap minHeap;

    // indicates wether or not all the processes have been produced.
    private ThreadFlags flags;

    /**
     * Initialize process producer.
     * @param heap the heap that the producer will produce to.
     * @param TF is the producer done producing? (T/F)
     */
    public producerThread (minHeap heap, ThreadFlags TF) 
    {
        this.minHeap = heap;
        this.flags = TF;
        this.totalNodesCreated = 0;
    }

    /**
     * Return the status of the producerComplete flag
     * @return true if producer has completed, false otherwise
     */
    public boolean getFlags () {
        return flags.isProducerComplete();
    }
    
    /**
     * Set the producer completeFlag
     * @param flag true if producer has completed, false otherwise
     */
    public void setFlags(boolean flag) {
        this.flags.setProducerComplete(flag);
    }

    /**
     * Return the count of processes created.
     * @return the amount of created processes
     */
    public int getTotalNodeCount () {
        return totalNodesCreated;
    }

    /**
     * Produce a random number.
     * @param min lower bound.
     * @param max upper bound
     * @return a random number between the min and max
     */
    public static int getRandomNumber(int min, int max) {
        return (int)(Math.random() * (max - min) + min);
    }
    
    /** 
     * Creates an instance of a Node
     * @return 
     */
    public Node createNode () {
        Node node = new Node(getRandomNumber(1, 5), getRandomNumber(10, 30));
        totalNodesCreated++;
        node.setProcessID(totalNodesCreated);
        return node;
    }

    /**
     * Return if the producer is finished producing the max amount of nodes.
     * @return true if the max number of nodes has been created. False otherwise
     */
    public boolean isFinished() {
        return this.totalNodesCreated >= this.maxNodes;
    }

    /**
     * Calculate the remaining amount of nodes.
     * @return the amount of processes left to be created
     */
    private int getRemainingNodes() {
        return this.maxNodes - this.totalNodesCreated;
    }
    
    /**
     * Generate a random number of nodes for the producer to create. 
     */
    
    private int getRandomNodes () {
        int choice = getRandomNumber(8, this.maxNodes );
        int nodesRemaining = getRemainingNodes(); 
        if (choice > nodesRemaining) {
            choice = nodesRemaining;
        }
        return choice;
    }

    /**
     * Make the producer rest for the specified amount of time.
     */
    private void rest() 
    {
        try{
            Thread.sleep(restTime);
        } catch(InterruptedException e){
            System.out.println( "ERROR: Producer interrupted." );
        }
    }

    /**
     * Add nodes to the heap.
     */
    @Override
    public void run() {

        while (!isFinished()) 
        {
                int nodesToAdd = getRandomNodes();
                
                // add the processes to the heap
                for (int i = 0; i < nodesToAdd; i++) {
                        Node producedNode = createNode();
                        this.minHeap.add( producedNode );
                }

                System.out.println(String.format("Producer has produced %d new nodes.", nodesToAdd));
                System.out.println("Idling...");
                rest();
        }

        System.out.println("Producer has completed its tasks...");

        setFlags(true);
    }
}