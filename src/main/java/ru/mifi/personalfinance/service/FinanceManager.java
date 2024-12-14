package ru.mifi.personalfinance.service;

import lombok.Data;
import ru.mifi.personalfinance.model.Transaction;
import ru.mifi.personalfinance.model.TransactionType;
import ru.mifi.personalfinance.model.User;
import ru.mifi.personalfinance.model.Wallet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class FinanceManager {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Wallet> wallets = new HashMap<>();
    private AtomicInteger transactionIdCounter = new AtomicInteger(0);

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void addWallet(Wallet wallet) {
        wallets.put(wallet.getWalletId(), wallet);
    }


    public void addTransaction(Wallet wallet, Transaction transaction) {
        if(Objects.isNull(transaction) || Objects.isNull(transaction.getType())) {
            throw new IllegalArgumentException("Unsupported transaction type");
        }

        wallet.getTransactions().add(transaction);

        switch (transaction.getType()) {
            case EXPENSE -> wallet.setBalance(wallet.getBalance() - transaction.getAmount());
            case INCOME -> wallet.setBalance(wallet.getBalance() + transaction.getAmount());
            default -> throw new IllegalArgumentException("Unsupported transaction type");
        }
    }

    public void addExpense(Wallet wallet, double amount) {
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID())
                .amount(amount)
                .type(TransactionType.EXPENSE)
                .build();

        addTransaction(wallet, transaction);
    }

    public void addIncome(Wallet wallet, double amount) {
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID())
                .amount(amount)
                .type(TransactionType.INCOME)
                .build();
        addTransaction(wallet, transaction);
    }

}
