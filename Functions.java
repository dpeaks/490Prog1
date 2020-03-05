
package program1;

//import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

/*
/Class ThreadFlags will handle communication between 
/the producer and consumer threads.
*/
class ThreadFlags {
  
        //boolean to set the producer as done with its tasks
        private boolean producerComplete; 

        public ThreadFlags() 
        {
            this.producerComplete = false; 
        }

        public boolean isProducerComplete() {
           return producerComplete;
        }
        
        //updates flag when the producer is done 
        public synchronized void setProducerComplete (boolean producerComplete) {
            this.producerComplete = producerComplete;
        }

    } 

/*
/Class RandomNumber
*/
class RandomNumber {
    //generating a random number to use in node creation 
    public static int getRandomNumber(int min, int max) {
            return (int)(Math.random() * (max - min) + min);
    }
}


/*
/Class Node will handle properties related to a Node/Process creation 
/as well as forthe individual properties of nodes
*/
class Node implements Comparable {
    
    //integer processID to store the specific Node's processID
    private int processID;
    
    //integer priority to store the specific Node's priority
    private int priority;
    
    //integer timeSlice to store the time slice (or run time) of a Node
    private int timeSlice;
    
    //delcaring LocalDateTime as nodeStart to store the local time of a processes' start time
    private LocalDateTime nodeStart;
    
    //integer to store the Node's previous ID
    private int prevID = 0; 
    
    //getting the processID
    public int getprocessID() {
        return processID;
    }
    
    //getting the priority of a process
    public int getPriority() {
        return priority;
    }
    
    //setting the priority of a process
    public void setPriority(int priority) { 
        this.priority = priority;
    }
    
    //getting the time slice (or run time) of a process
    public int getTime() {
        return timeSlice;
    }
    
    //setting the time of a process
    public void setTime(int timeSlice) { 
        this.timeSlice = timeSlice;
    }
    
    //getting the current local time of the start of a process
    public LocalDateTime getStart() { 
        return nodeStart;
    }
    
    //setting the current local time of the start of a process
    public void setStart(LocalDateTime nodeStart) {
        this.nodeStart = nodeStart;
    }
    
    //displaying and formatting a string containing information about a process
    public String toString() {
        //return processID + "\t" + priority + "\t" + run_time;
        //SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss a zzz"); 
        return String.format("Process: %d with priority %d (start %s)", this.getprocessID(), this.getPriority(), TimeFormat.formatDateTime(this.getStart()));
   
    }
    
    /*
    /Function Node:
    /stores the timeSlice, priority, and processID of a Node
    */
    public Node(int priority, int timeSlice) {
        this.processID = ++ prevID;
        this.priority = priority; 
        this.timeSlice = timeSlice;
    }
    
    /*
    /Function run: 
    /throws InterruptedException
    /sets the start time of a process
    /tells the node to sleep for their run time
    */
    public void run() throws InterruptedException {
        this.setStart(CurrentTime.getCurrentTime());
        Thread.sleep(this.timeSlice);
    
    }
    
    /*
    /compareTo: used to compare Nodes in the minHeap
    */
    public int compareTo(Object o) {
       //@Override
        if(o instanceof Node) { 
            Node other = (Node) o; 
            return Integer.compare(this.getPriority(), other.getPriority());
    }
        return - 1; 
    }
}
 
/*
/Class TimeFormat sets the time/date format
*/
class TimeFormat {

    public static String formatDateTime(LocalDateTime dateTime) {
        //hh:mm:ss a zzz
        return dateTime.format(DateTimeFormatter.ofPattern( "hh:mm:ss:nnnnnnnnnnnnnnn"));
    }
}

/*
/Class CurrentTime gets the current local date and time from the computer
*/ 
class CurrentTime {
    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}

/*
/Class CurrentTimeFormatted formats the current local date and time from the computer
*/
class CurrentTimeFormatted {
    public static String getCurrentTimeFormatted() {
        return TimeFormat.formatDateTime(CurrentTime.getCurrentTime());
    }
} 