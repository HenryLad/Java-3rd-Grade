import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Bankkonto {
    private double balance;

    public Bankkonto(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        try {
            System.out.println("Einzahlung wird bearbeitet ...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (this) {
            double originalBalance = balance;
            balance += amount;
            System.out.printf("Alter Kontostand: %.0f, Einzahlung: %.0f, Neuer Kontostand: %.0f%n", originalBalance, amount,
                    balance);
        }

       
    }

    public double getBalance() {
        return balance;
    }

    public  void withdraw(double amount) {
        try {
            System.out.println("Auszahlung wird bearbeitet ...");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (this) {
            if (amount <= balance) {
                double originalBalance = balance;
                balance -= amount;
                System.out.printf("Alter Kontostand: %.0f, Einzahlung: %.0f, Neuer Kontostand: %.0f%n", originalBalance,
                        amount, balance);
            } else {
                System.out.println("Nicht genug Geld auf dem Konto du Eyrie");
            }
        }
    }
}