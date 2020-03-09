package program1;

/*
 * Class producerThread that produces nodes for the consumer threads to consume.
 */
public class producerThread implements Runnable {

    // the amount of time a process is idle
    private final long idleWait = 33;

    // maximum number of nodes to produce
    private final int maxNodes = 75;

    // total amount of nodes created
    private int totalNodesCreated;

    // counter for how many times a process has been awaken
    private int wakeCount;

    // heap for processes/ nodes to get arranged into
    private minHeap minHeap;

    // 
    private ThreadFlags flags;

    //producerThread sets the minHeap and the ThreadFlags
    public producerThread (minHeap heap, ThreadFlags TF) 
    {
            this.minHeap = heap;
            this.flags = TF;

            this.wakeCount = 0;
            this.totalNodesCreated = 0;
    }

    //gets the thread flags
    public ThreadFlags getFlags () {
            return flags;
    }

    //gets the current node count
    public int getNodeCount () {
            return totalNodesCreated;
    }

    //returns the wake up count
    public int getWake () {
            return wakeCount;
    }

    //creates an instance of a Node 
    public Node createNode () {
            Node node = new Node(getRandomNumber(1, 5), getRandomNumber(10, 30));
            totalNodesCreated++;
            node.setProcessID(totalNodesCreated);
            return node;
    }

    //determines whether or not the producer is finished creating nodes
    public boolean isFinished() {
            return this.totalNodesCreated >= this.maxNodes;
    }

    //gets the remaining nodes in the heap
    private int getRemainingNodes() {
            return this.maxNodes - this.totalNodesCreated;
    }

    public static int getRandomNumber(int min, int max) {
            return (int)(Math.random() * (max - min) + min);
    }
    
    //generate a random number of notes for the producer to create
    private int getRandomNodes () {
            int possibility = getRandomNumber(8, this.maxNodes / 4 );
            int nodesRemaining = getRemainingNodes(); 
            if (possibility > nodesRemaining) {
                possibility = nodesRemaining;
            }
            return possibility;
    }

    //makes the thread wait for the given time in miliseconds
    private void idle() 
    {
            try 
            {
                    Thread.sleep(idleWait);
            } catch(InterruptedException e) 
            {
                    System.out.println( "Error: Producer was interrupted." );
            }
    }

    //add nodes to heap as it runs
    @Override
    public void run() {

            //run the function until the Producer is finished creating nodes
            while (!isFinished()) 
            {
                    //set the number of nodes to produce to random
                    int nodeToAdd = getRandomNodes();
                    for (int i = 0; i < nodeToAdd; i++) {
                            Node producedNode = createNode();
                            this.minHeap.add( producedNode );
                    }

                    //prints out information related to the producer thread 
                    //such as how many nodes it's produced during the current cycle 
                    //as well as whether or not the thread is in an idle state
                    System.out.println(String.format("Producer has produced ~%d new nodes.", nodeToAdd));
                    System.out.println("Producer is idling...");
                    idle();
            }

            //prints out that the producer has completed work on the heap (producing nodes)
            System.out.println("Producer has completed its tasks.");

            //set producer complete flag
            flags.setProducerComplete(true);
    }
}