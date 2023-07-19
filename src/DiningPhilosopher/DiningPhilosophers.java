/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiningPhilosopher;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class DiningPhilosophers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DiningServer server = new DiningServerImpl();

        Philosopher[] philosopherArray = new Philosopher[DiningServerImpl.NUM_OF_PHILS];

        // create the philosopher threads
        for (int i = 0; i < DiningServerImpl.NUM_OF_PHILS; i++) {
            philosopherArray[i] = new Philosopher(server, i);
        }

        for (int i = 0; i < DiningServerImpl.NUM_OF_PHILS; i++) {
            new Thread(philosopherArray[i]).start();
            try {
                new Thread(philosopherArray[i]).join();
            } catch (InterruptedException ex) {
                Logger.getLogger(DiningPhilosophers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
