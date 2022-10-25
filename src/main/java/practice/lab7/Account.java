package practice.lab7;

import org.openjdk.jmh.annotations.Benchmark;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private double balance;

    /**
     * @param money
     */
	private Lock lock = new ReentrantLock();
	public void deposit(double money) {
//    public synchronized void deposit(double money) {
        try {
			lock.lock();
			double newBalance = balance + money;
            try {
                Thread.sleep(10);   // Simulating this service takes some processing time
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            balance = newBalance;
			lock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public double getBalance() {
        return balance;
    }
}