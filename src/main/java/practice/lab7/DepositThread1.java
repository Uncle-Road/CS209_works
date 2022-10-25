package practice.lab7;

public class DepositThread1 implements Runnable {
    private Account1 account;
    private double money;

    public DepositThread1(Account1 account, double money) {
        this.account = account;
        this.money = money;
    }

    @Override
    public void run() {
        account.deposit(money);
    }
}
