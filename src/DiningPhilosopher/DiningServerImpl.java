/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiningPhilosopher;

/**
 *
 * @author hp
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DiningServerImpl implements DiningServer{
    // different philosopher states
    enum State {
        THINKING, HUNGRY, EATING
    };

    // number of philosophers
    public static final int NUM_OF_PHILS = 5;

    // array to record each philosopher's state
    private final State[] state;
    final ReentrantLock entLock;
    private final Condition self[];
    public DiningServerImpl() {
        state=new State[NUM_OF_PHILS];
        entLock=new ReentrantLock();
        self=new Condition[NUM_OF_PHILS];
        for(int i=0;i<NUM_OF_PHILS;i++){
            state[i]=State.THINKING;
        }
    }
    int leftNeighbor(int i){
        return (i+4)%NUM_OF_PHILS;
    }
    int rightNeighbor(int i){
        return (i+1)%NUM_OF_PHILS;
    }
    // called by a philosopher when they wish to eat 
    @Override
    public void takeForks(int pnum) {
        try {
            entLock.lock();
                state[pnum]=State.HUNGRY;
                try{
                test(pnum);
                }catch(NullPointerException ex){}
                
                if(state[pnum]!=State.EATING){
                    try {
                        self[pnum].await();
                    }
                    catch(NullPointerException ex){}
                    
                    catch (InterruptedException ex) {
                           Logger.getLogger(DiningServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                } finally {
                 entLock.unlock();
                }
    }
    void test(int pnum){
        if(state[leftNeighbor(pnum)]!=State.EATING &&
			state[rightNeighbor(pnum)]!=State.EATING &&
			state[pnum]==State.HUNGRY){
			state[pnum]=State.EATING;
		        try{
                        self[pnum].signal();
                        }catch(NullPointerException ex){}   
		}
    }
    // called by a philosopher when they are finished eating 
    @Override
    public void returnForks(int pnum) {
        try{
       entLock.lock();
            state[pnum]=State.THINKING;
            test(leftNeighbor(pnum));
            test(rightNeighbor(pnum));
        } finally {
            entLock.unlock();
        }
    }
}