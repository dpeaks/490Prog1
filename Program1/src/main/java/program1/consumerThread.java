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
        this.consumerID++;
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

    /**
     * Returns wether or not the consumer is running.
     * @return true if consumer is running. False otherwise
     */
    public boolean getIsRunning(){
        return this.isRunning;
    }
    
    /**
     * Applies tabs to message and tells which consumer consumed a process.
     * @param message additional information to attach to Consumer ID
     */
    private void report (String message) {
        System.out.println(String.format("%sConsumer %d %s", this.tab, this.getConsumerID(), message));
    }

    /**
     * Requires the consumer to wait for a specified amount of time.
     */
    private void idle () {
        try {
                report( "Idling..." );
                Thread.sleep(idleWait);

                Thread.sleep(idleWait );
        } catch ( InterruptedException e ) {
                report( "was interrupted." );
        }
    }


    /**
     * Grab the next process/node for consumption.
     * @return a node for consumption. If one cannot be obtained then the thread has either been interrupted
     * or the consumer is done consuming.
     */
    public Node grabNextNode () {
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
                return this.minHeap.consumeHead();
        } 
        catch ( InterruptedException e ) 
        {
                report("was interrupted.");
                return null;
        }
    }

    /**
     * Consume process until there are no more to consume.
     */
    @Override
    public void run () {
        
        this.isRunning = true;
        while (this.isRunning) {
                try {
                        Node nodeToProcess = this.grabNextNode();

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
