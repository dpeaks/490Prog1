/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program1;

class ThreadFlags {
        boolean producerComplete = false; 

        public ThreadFlags(boolean producerComplete) 
        {
            this.producerComplete = true; 
        }

        public boolean producerComplete(boolean b) {
            return this.producerComplete;
        }

    } 

//generating a random number to use in node creation 
class RandomNumber {
    private static int getNuminRange(int min, int max) {
            return (int)(Math.random() * ((max - min) + 1)) + min;
    }
}


//node data structure 
class Node implements Comparable {
    private String processID;
    private int priority;
    private int run_time;
    
    public Node(String processID, int priority, int runTime) {
        this.processID = processID;
        this.priority = priority; 
        this.run_time = run_time;
    }
    
    public String getprocessID() {
        return processID;
    }
    
    public int getTime() {
        return run_time;
    }
    
    public int getRunTime() {
        return run_time;
    }
    
    public String toString() {
        return processID + "\t" + priority + "\t" + run_time;
    }
    
    public int compareTo(Object o) {
        return this.priority - ((Node)o).priority;
    }
}
