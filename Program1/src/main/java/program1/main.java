// Antoine Lynch & Dant'e Peaks
package program1;


public class main {
    public static void main(String[] args) { 
        
        //create new
        minHeap heap = new minHeap(100);
        
        //creating instance of flag watcher
        ProducerFlag flags = new ProducerFlag(); 
        
        //creating consumer threads
        ConsumerThread ct1 = new ConsumerThread(heap, flags);
        ConsumerThread ct2 = new ConsumerThread(heap, flags);
        
        //creating producer threads
        ProducerThread pt1 = new ProducerThread(heap, flags);
            
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

    }
}