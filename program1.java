package program1;


import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Random;

public class program1 {
    public static void main(String args[]) throws InterruptedException {
        //Object of a class that has both produce and consume methods
        //Random rand = new Random();
        
        final ProduceConsume pc = new ProduceConsume();
        
        //creating producer thread 
        Thread T1 = new Thread(new Runnable() {
            @Override 
            public void run()
            {
                try {
                    pc.produce();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        //create consumer thread 
        Thread T2 = new Thread(new Runnable() {
            @Override 
            public void run()
            {
                try {
                    pc.consume();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println( " was interrupted.");
                }
            }
        });
        
        //start both threads
        T1.start();
        T2.start();
        
        //make T1 finish before T2
        T1.join();
        T2.join();
    }
    
    //Contains a list, producer (which adds items to the list) and consumer (removes items)
    public static class ProduceConsume {
        
        //create a list shared by both producer and consumer 
        //Size: 2
        LinkedList<Integer> list = new LinkedList<>(); 
        int capacity = 2;
        
        //Produce() function called by producer thread
        public void produce() throws InterruptedException 
        {
           Random rand = new Random();

            int value = 0; 
            while (true) {
                synchronized (this)
                        {
                            //producer waits while the list is full
                            while (list.size() == capacity) 
                                wait();
                            System.out.println("Producer 1 finished Process: " + value);
                            
                            //inserting jobs into the list 
                            list.add(value++);
                            
                            //letting consumer thread know that it can start consuming
                            notify();
                            
                            //putting the thread to sleep 
                            Thread.sleep(rand.nextInt(1000));
                        }
            }
        }
        
        //Consume function used by consumer thread
        public void consume() throws InterruptedException 
        {
            Random rand = new Random();
            while(true) {
                synchronized (this)
                {
                    //consumer thread waits when list is empty since it has nothing to consume
                    while (list.size() == 0)
                        wait();
                    
                    //retrieving first thing in the list 
                    int value = list.removeFirst();
                    
                    System.out.println("Consumer 1 finished Process: " + value);
                    
                    //waking up producer thread 
                    notify();
                    
                    //then making producer thread sleep
                    Thread.sleep(rand.nextInt(1000));
                            }
            }
        }
        
        
    }
}