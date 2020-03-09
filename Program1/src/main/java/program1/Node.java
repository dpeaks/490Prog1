/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program1;

import java.time.LocalDateTime;

/**
 * Class Node holds all the information about a process/node and executes them.
 * @author Dante& Antoine
 */
class Node implements Comparable {
    
    // Process identifier
    private int processID;
    
    // Priority of a process
    private int priority;
    
    // Execution time of a process
    private int timeSlice;
    
    // Time that a process was started
    private LocalDateTime nodeStart;
    
    /**
     * Initialize a node.
     * @param priority the priority of a process
     * @param timeSlice the amount fo time it takes for a process to execute
     */
    public Node(int priority, int timeSlice) {
        this.processID++;
        this.priority = priority; 
        this.timeSlice = timeSlice;
    }
    
    /** 
     * Return the process ID of a process
     * @return process ID
     */
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
    
    
    /**
     * Return the priority of a process
     * @return a process's priority
     */
    public int getPriority() {
        return priority;
    }
    
    /**
     * Set the priority of a process.
     * @param priority the priority to set this process to.
     */
    public void setPriority(int priority) { 
        this.priority = priority;
    }
    
    /**
     * Returns the amount of time to execute a process.
     * @return the amount of time to execute a process
     */
    public int getTime() {
        return timeSlice;
    }
    
    /**
     * Set the amount of time for a node to execute.
     * @param timeSlice the amount of time to execute a process
     */
    public void setTime(int timeSlice) { 
        this.timeSlice = timeSlice;
    }
    
    /**
     * Returns the start time of a process.
     * @return the start time of a process
     */
    public LocalDateTime getStart() { 
        return nodeStart;
    }
    
    /**
     * Sets the the start time of a process
     * @param nodeStart the start time of a process
     */
    public void setStart(LocalDateTime nodeStart) {
        this.nodeStart = nodeStart;
    }
    
    /**
     * Report information about a process.
     * @return string containing process information
     */
    public String nodeReport() {
        return String.format("Process: %d with priority %d (start %s)", this.getProcessID(), this.getPriority(), Time.formatDateTime(this.getStart()));
    }
    
    /**
     * Execute a process by inducing its wait time.
     * @throws InterruptedException 
     */
    public void run() throws InterruptedException {
        this.setStart(Time.getCurrentTime());
        Thread.sleep(this.timeSlice);
    
    }
    
    /**
     * Compare the priority of two nodes in a heap.
     * @param o another node to compare the current node to
     * @return negative if the result is false, positive if the result is true (two nodes have the same priority)
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
