/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program1;

import java.time.LocalDateTime;

/**
 *
 * @author Dante
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
    public int getProcessID() {
        return processID;
    }
    
    /**
     * Set the process ID.
     * @param idNum the id that a process will have
     */
    public void setProcessID(int idNum) {
        this.processID = idNum;
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
        return String.format("Process: %d with priority %d (start %s)", this.getProcessID(), this.getPriority(), TimeFormat.formatDateTime(this.getStart()));
   
    }
    
    /*
    /Function Node:
    /stores the timeSlice, priority, and processID of a Node
    */
    public Node(int priority, int timeSlice) {
        this.processID++;
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
        this.setStart(TimeFormat.getCurrentTime());
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
