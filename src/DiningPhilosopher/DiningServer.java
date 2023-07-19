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
public interface DiningServer {
    //called by a philosopher when he wishies to eat
    public void takeForks(int philNumber);
    //called by a philosopher when he is finished eatign
    public void returnForks(int philNumber);

}
