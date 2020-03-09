/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program1;

/**
 *
 * @author Dante
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