package program1;

import java.time.LocalDateTime;

/*
 * Class consumerThread consumes processes/nodes and allows them to execute.
 * @author Dante 
 */
public class consumerThread implements Runnable {

    // amount of time to be idle
    private final int idleWait = 66;

    // count of consumed processes
    private int consumedNodes;

    // Heap that stores processes
    private minHeap minHeap;

    // Signals if there are any more processes to consume
    private ThreadFlags flags;

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
    public consumerThread (minHeap heap, ThreadFlags TF) {

            this.minHeap = heap;
            this.consumerID = ++ lastId;
            this.flags = TF;
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

    //function to handle reports on consumer thread statuses
    private void report (String message) {
            System.out.println(String.format("%sConsumer %d %s", this.tab, this.getConsumerID(), message));
    }

    //tells the consumer to wait for the specified idleWait time
    private void idle () {
            try {
                    report( "is idling..." );
                    Thread.sleep(idleWait);

                    Thread.sleep(idleWait );
            } catch ( InterruptedException e ) {
                    report( "was interrupted." );
            }
    }


    //returns a node when the consumer requests them
    public Node requestNode () {
            report( "is requesting a new node." );

            while ( this.minHeap.isEmpty() ) {

                    report( "cannot find new node." );

                    if (flags.isProducerComplete()) {
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
                    return this.minHeap.removeHead();
            } 
            catch ( InterruptedException e ) 
            {
                    report("was interrupted.");
                    return null;
            }
    }

    /**
     * Consumes the processes in the queue while there are some to get.
     */
    @Override
    public void run () {
        
            this.isRunning = true;
            while (this.isRunning) {
                    try {
                            Node nodeToProcess = this.requestNode();

                            if (nodeToProcess == null) {
                                    continue;
                            }
                            nodeToProcess.run();

                            LocalDateTime finishedProcessingTime = Time.getCurrentTime();

                            String nodeStatistics = nodeToProcess.toString();

                            report(String.format("finished %s at %s", nodeStatistics, Time.formatDateTime(finishedProcessingTime)));
                            this.consumedNodes++;

                    } 
                    catch ( InterruptedException ex ) 
                    {
                        report("process was interrupted");
                    }
            }

            report("is done."); 
            report(String.format("consumed %d nodes.", this.consumedNodes));
    }
}
