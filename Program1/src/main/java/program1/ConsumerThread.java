package program1;

import java.time.LocalDateTime;

/*
 * Class ConsumerThread consumes processes/nodes and allows them to execute.
 * @author Dante & Antoine
 */
public class ConsumerThread implements Runnable {

    // amount of time to be idle
    private final int idleWait = 66;

    // count of consumed processes
    private int consumedNodes;

    // Heap that stores processes
    private minHeap minHeap;

    // Signals if there are any more processes to consume
    private ProducerFlag producerFlag;

    //consumerID of the previous consumer thread
    private static int lastId = 0;

    //consumer thread's ID
    private int consumerID;

    //True if consumer thread is able to continue consuming
    private boolean isRunning;

    // used for indention of output
    private String tab;

    /**
     * Initialize consumer thread instance and set identation.
     * @param heap the heap that the producer consumes from.
     * @param TF true if the producer is done producing. False otherwise
     */
    public ConsumerThread (minHeap heap, ProducerFlag TF) {

            this.minHeap = heap;
            this.consumerID = ++ lastId;
            this.producerFlag = TF;
            this.consumedNodes = 0;
            this.isRunning = false;

            StringBuilder sb = new StringBuilder();
            for ( int i = 0; i < this.consumerID; i++ ) {
                    sb.append( '\t' );
            }

            this.tab = sb.toString();
    }

    /**
     * Return the ID of a consumer.
     * @return the ID of a consumer
     */
    public int getConsumerID () {
            return consumerID;
        }

    /**
     * Returns the amount of processes/nodes consumed.
     * @return the amount of nodes/processes consumed
     */
    public int getTotalConsumed () {
            return consumedNodes;
        }

    /**
     * Is the consumer running or not?
     * @return true if consumer is running. False otherwise
     */
    public boolean getIsRunning(){
        return this.isRunning;
    }
    
    /**
     * Set the consumer to running or not running.
     * @param running true if the process is running, false otherwise.
     */
    public void setIsRunning(boolean running){
        this.isRunning = running;
    }
    
    /**
     * Print data about a consumer and an additional message
     * @param message additional info about consumer
     */
    private void report (String message) {
            System.out.println(String.format("%sConsumer %d %s", this.tab, this.getConsumerID(), message));
    }

    /**
     * Make the consumer pause for a specified amount of time.
     */
    private void idle () {
            try {
                report( "Idling..." );
                Thread.sleep(idleWait);

            } catch ( InterruptedException e ) {
                report( "was interrupted." );
            }
    }


    /**
     * Grab another node from the heap if possible.
     * @return the node at the top of the heap or null if there are no more nodes to grab.
     */
    public Node getNextNode () {
        while ( this.minHeap.isEmpty() ) {

                report( "cannot find new node." );

                if (producerFlag.isProducerComplete()) {
                    report("thinks there aren't any nodes left on the heap.");
                    this.isRunning = false; 
                    return null; 
                }
                else
                {
                    idle();
                }
        }
        try {
                return this.minHeap.consumeHead();
        }catch ( InterruptedException e ) 
        {
                report("was interrupted.");
                return null;
        }
    }

    /**
     * Consume process and display information about the consumed process.
     */
    @Override
    public void run () {

        setIsRunning(true);
        while (this.isRunning) {
                try {
                    Node nextNode = this.getNextNode();

                    if (nextNode == null) {
                            continue;
                    }
                    nextNode.run();

                    LocalDateTime finishedProcessingTime = Time.getCurrentTime();

                    String nodeStatistics = nextNode.nodeReport();

                    report(String.format("finished %s at %s", nodeStatistics, Time.formatDateTime(finishedProcessingTime)));
                    this.consumedNodes++;

                }catch ( InterruptedException ex ){
                    report("process was interrupted");
                }
        }

        report("is done."); 
        report(String.format("consumed %d nodes.", this.consumedNodes));
    }
}
