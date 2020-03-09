//NAMES: Adam McFry & Alexandra Noreiga
//DATE: 3/9/20
//CLASS: CS 490-01
//PURPOSE: this program simulates synchronization of processes and threading in Java
//         featuring the Producer/Consumer problem 

package program1;

/*
/Class main: 
/initializes and runs the main functionality of the program
*/
public class main {
    public static void main(String[] args) { 
        
        //creating new heap
        minHeap heap = new minHeap(75);
        
        //creating instance of flag watcher
        ThreadFlags flags = new ThreadFlags(); 
        
        //creating consumer threads
        consumerThread ct1 = new consumerThread(heap, flags);
        consumerThread ct2 = new consumerThread(heap, flags);
        
        //creating producer threads
        producerThread pt1 = new producerThread(heap, flags);
            
        //initialize consumer threads
        Thread consume1 = new Thread(ct1);
        Thread consume2 = new Thread(ct2);
        
        //starting consumer threads
        consume1.start();
        consume2.start();
        
        //initializing producer thread 
        Thread produce1 = new Thread(pt1);
        
        produce1.start();
        
        try {
            produce1.join(); //joining producer thread
            
            //joining consumer threads
            consume1.join();
            consume2.join();
        } 
        catch (InterruptedException ex) { 
            System.out.println("Error: Threads were interrupted."); 
        }
        
        //print out how many nodes the producer created in the heap.
        System.out.println(String.format("Producer created %d nodes in the heap.", pt1.getNodeCount()));
        
        //print out how many nodes are remaining in the heap. 
        System.out.println(String.format("Producer thinks there are %d nodes remaining...", heap.size()));
        }
    }