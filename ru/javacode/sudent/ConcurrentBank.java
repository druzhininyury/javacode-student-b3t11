package ru.javacode.sudent;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentBank {

    public static class BankAccount {

        private final int id;
        private volatile long balance;

        // Omitted checking balance correctness.
        public BankAccount(int id, long balance) {
            this.id = id;
            this.balance = balance;
        }

        // Omitted checking amount correctness.
        public synchronized void deposit(long amount) {
            balance += amount;
        }

        // Omitted checking amount correctness.
        public synchronized void withdraw(long amount) {
            balance -= amount;
        }

        public long getBalance() {
            return balance;
        }

    }

    private static int nextAccountId = 0;

    private final List<BankAccount> accounts = new ArrayList<>();

    public synchronized BankAccount createAccount(long amount) {
        BankAccount result = new BankAccount(nextAccountId++, amount);
        accounts.add(result);
        return result;
    }

    // Omitted checking if accounts not belongs to bank and amount correctness.
    public synchronized void transfer(BankAccount accountFrom, BankAccount accountTo, long amount) {
        accountFrom.withdraw(amount);
        accountTo.deposit(amount);
    }

    public synchronized long getTotalBalance() {
        return accounts.stream().mapToLong(account -> account.getBalance()).sum();
    }
}
