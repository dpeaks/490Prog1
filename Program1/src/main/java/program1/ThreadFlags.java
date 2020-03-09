/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program1;

/**
 * This class will alert the consumer class if the producer is done producing processes. 
 * @author Dante
 */
class ThreadFlags {
  
        //boolean to set the producer as done with its tasks
        private boolean producerComplete; 

        /**
         * Constructor for this class. Set the producerComplete flag to false.
         */
        public ThreadFlags() 
        {
            this.producerComplete = false; 
        }

        /**
         * Return the status of the producer class.
         * @return true if the producer is done, false otherwise.
         */
        public boolean isProducerComplete() {
           return producerComplete;
        }
        
        /**
         * Set the producerComplete flag.
         * @param producerComplete true if the producer is done, false otherwise.
         */
        public synchronized void setProducerComplete (boolean producerComplete) {
            this.producerComplete = producerComplete;
        }

    }