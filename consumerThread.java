package program1;

import java.time.LocalDateTime;

/*
 * Class consumerThread: 
 * consumes tasks and allows them to execute; requests nodes from the heap and simulates their execution 
 * also reports statistics on the process 
 */
public class consumerThread implements Runnable {
   
        //sets the idle wait count in miliseconds
	private final int idleWait = 66;
	
        //total number of nodes consumed by a particular consumer Thread
	private int totalConsumed;

        //the heap the producer produces nodes to
	private minHeap minHeap;

	//flags to help the producer and consumer threads communicate with each other
	private ThreadFlags flags;

        //consumerID of the previous consumer thread
	private static int lastId = 0;
	
        //consumer thread's ID
	private int consumerID;

        //True if consumer thread is still able to continue consuming
	private boolean isRunning;

	//makes tabbing/formatting outputs easier
	private String tabsPrepend;

        //
	public consumerThread (minHeap heap, ThreadFlags TF) {

		this.minHeap = heap;
		this.consumerID = ++ lastId;
                this.flags = TF;
		this.totalConsumed = 0;
		this.isRunning = false;

                //to make tabbing outputs easier
		StringBuilder sb = new StringBuilder();
		for ( int i = 0; i < this.consumerID; i++ ) {
			sb.append( '\t' );
		}

		this.tabsPrepend = sb.toString();
	}

    //returns the consumerID of the current consumer thread
    public int getConsumerID () {
            return consumerID;
	}

    //returns the total consumed nodes by the current consumer threads
    public int getTotalConsumed () {
            return totalConsumed;
	}
    
            //function to handle reports on consumer thread statuses
	private void report (String message) {
		System.out.println( String.format("%sConsumer %d %s", this.tabsPrepend, this.getConsumerID(), message));
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
                //DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss");
                this.isRunning = true;
		while (this.isRunning) {
			try {
				Node nodeToProcess = this.requestNode();

				if (nodeToProcess == null) {
					continue;
				}
				nodeToProcess.run();

				LocalDateTime finishedProcessingTime = CurrentTime.getCurrentTime();

				String nodeStatistics = nodeToProcess.toString();
                                //Date dateFormat = new Date();
                                //SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss a zzz"); 
                                
				report(String.format("finished %s at %s", nodeStatistics, TimeFormat.formatDateTime(finishedProcessingTime)));
				this.totalConsumed++;

			} 
                        catch ( InterruptedException ex ) 
                        {
                            report("process was interrupted");
                        }
                }
               
                report("is done."); 
                report(String.format("consumed %d nodes.", this.totalConsumed));
        }
}
