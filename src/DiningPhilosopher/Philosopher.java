/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiningPhilosopher;

import java.util.concurrent.locks.ReentrantLock;

public class Philosopher implements Runnable{

    private final  DiningServer server;
    private final  int philNum;
    private final ReentrantLock entLock;
    public Philosopher(DiningServer server, int philNum) {
        this.server = server;
        this.philNum = philNum;
        entLock=new ReentrantLock();

    }

    private void thinking() {
        SleepUtilities.nap();
    }

    private void eating() {
        SleepUtilities.nap();
    }

    // philosophers alternate between thinking and eating
    @Override
    public void run() {
        while (true) {
            System.out.println("philosopher " + philNum + " is thinking.");
            thinking();

            System.out.println("philosopher " + philNum + " is hungry.");
           
            try{
            entLock.lock();
                server.takeForks(philNum);
            } finally {
                entLock.unlock();
            }
            System.out.println("philosopher " + philNum + " is eating.");

            eating();

            System.out.println("philosopher " + philNum + " is done eating.");
            
            try{
                entLock.lock();
            server.returnForks(philNum);
            }finally{
                entLock.unlock();
            } 
            }
        }
}
