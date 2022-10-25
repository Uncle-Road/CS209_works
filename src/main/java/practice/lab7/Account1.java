package practice.lab7;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account1 {
    private double balance;

    /**
     * @param money
     */
    public synchronized void deposit(double money) {
        try {
            double newBalance = balance + money;
            try {
                Thread.sleep(10);   // Simulating this service takes some processing time
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            balance = newBalance;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getBalance() {
        return balance;
    }
}