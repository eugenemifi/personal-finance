package ru.mifi.personalfinance.service;

import ru.mifi.personalfinance.model.Transaction;
import ru.mifi.personalfinance.model.TransactionType;
import ru.mifi.personalfinance.model.User;
import ru.mifi.personalfinance.model.Wallet;

import java.util.HashMap;
import java.util.Map;

public class FinanceService {

    private final Map<String, User> users = new HashMap<>(); // Хранилище пользователей

    // Добавление нового пользователя
    public void addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("User already exists!");
        }
        users.put(user.getUsername(), user);
    }

    // Получение всех пользователей
    public Map<String, User> getUsers() {
        return users;
    }

    // Получение всех кошельков
    public Map<Integer, Wallet> getWallets() {
        Map<Integer, Wallet> wallets = new HashMap<>();
        users.values().forEach(user -> user.getWallets().values().forEach(wallet -> wallets.put(wallet.getId(), wallet)));
        return wallets;
    }

    // Добавление нового кошелька
    public void addWallet(User user, Wallet wallet) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null when adding a wallet");
        }

        if (user.getWallets().containsKey(wallet.getName())) {
            throw new IllegalArgumentException("Wallet with the same name already exists for the user");
        }

        user.getWallets().put(wallet.getName(), wallet);
    }

    // Добавление дохода
    public void addIncome(Wallet wallet, double amount) {
        Transaction transaction = Transaction.builder()
                .id(java.util.UUID.randomUUID())
                .type(TransactionType.INCOME)
                .category("General")
                .amount(amount)
                .build();
        addTransaction(wallet, transaction);
    }

    // Добавление расхода
    public void addExpense(Wallet wallet, double amount) {
        Transaction transaction = Transaction.builder()
                .id(java.util.UUID.randomUUID())
                .type(TransactionType.EXPENSE)
                .category("General")
                .amount(amount)
                .build();
        addTransaction(wallet, transaction);
    }

    // Добавление транзакции
    public void addTransaction(Wallet wallet, Transaction transaction) {
        wallet.getTransactions().add(transaction);

        if (transaction.getType() == TransactionType.INCOME) {
            wallet.setBalance(wallet.getBalance() + transaction.getAmount());
        } else if (transaction.getType() == TransactionType.EXPENSE) {
            wallet.setBalance(wallet.getBalance() - transaction.getAmount());
        }
    }
}
