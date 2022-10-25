package practice.lab7;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



@State(Scope.Benchmark)
public class Test {

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void testAccountLock(Blackhole bh) {
        Account account = new Account();
        ExecutorService service = Executors.newFixedThreadPool(100);
        for(int i = 1; i <= 100; i++) {
            service.execute(new DepositThread(account, 10));
        }
        service.shutdown();

        while(!service.isTerminated()) {}

//        System.out.println("Balance: " + account.getBalance());
        bh.consume(account.getBalance());
    }

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void testAccountSync(Blackhole bh) {
        Account1 account = new Account1();
        ExecutorService service = Executors.newFixedThreadPool(100);
        for(int i = 1; i <= 100; i++) {
            service.execute(new DepositThread1(account, 10));
        }
        service.shutdown();

        while(!service.isTerminated()) {}

//        System.out.println("Balance: " + account.getBalance());
        bh.consume(account.getBalance());
    }

    public static void main(String[] args) throws IOException {
        Account account = new Account();
        ExecutorService service = Executors.newFixedThreadPool(100);
        for(int i = 1; i <= 100; i++) {
            service.execute(new DepositThread(account, 10));
        }
        service.shutdown();

        while(!service.isTerminated()) {}

        System.out.println("Balance: " + account.getBalance());
    }


}
