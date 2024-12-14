package ru.mifi.personalfinance.view;


import ru.mifi.personalfinance.model.TransactionType;
import ru.mifi.personalfinance.model.User;
import ru.mifi.personalfinance.model.Wallet;
import ru.mifi.personalfinance.service.FinanceManager;

import java.util.ArrayList;
import java.util.Scanner;

public class FinanceManagerView {

    private final FinanceManager financeManager = new FinanceManager();
    private final Scanner scanner = new Scanner(System.in);



    public void run() {
        System.out.println("Welcome to Personal Finance Manager!");
        while (true) {
            showMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> createUser();
                case "2" -> createWallet();
                case "3" -> addTransaction();
                case "4" -> showWallets();
                case "5" -> {
                    System.out.println("Exiting application. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Create User");
        System.out.println("2. Create Wallet");
        System.out.println("3. Add Transaction (Income or Expense)");
        System.out.println("4. Show Wallets");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private void createUser() {
        System.out.println("\nCreate User:");
        System.out.print("Enter user ID: ");
        int userId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = User.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .build();

        financeManager.addUser(user);
        System.out.println("User created successfully: " + username);
    }

    private void createWallet() {
        System.out.println("\nCreate Wallet:");
        System.out.print("Enter wallet ID: ");
        int walletId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter wallet name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine());

        Wallet wallet = Wallet.builder()
                .walletId(walletId)
                .name(name)
                .balance(balance)
                .transactions(new ArrayList<>())
                .build();

        financeManager.addWallet(wallet);
        System.out.println("Wallet created successfully: " + name);
    }

    private void addTransaction() {
        System.out.println("\nAdd Transaction:");
        System.out.print("Enter wallet ID: ");
        int walletId = Integer.parseInt(scanner.nextLine());
        Wallet wallet = financeManager.getWallets().get(walletId);

        if (wallet == null) {
            System.out.println("Wallet not found.");
            return;
        }

        System.out.print("Enter transaction type (INCOME/EXPENSE): ");
        String typeInput = scanner.nextLine();
        TransactionType type;
        try {
            type = TransactionType.valueOf(typeInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid transaction type. Please try again.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        switch (type) {
            case INCOME -> financeManager.addIncome(wallet, amount);
            case EXPENSE -> financeManager.addExpense(wallet, amount);
        }
        System.out.println(type + " transaction of " + amount + " added successfully.");
    }

    private void showWallets() {
        System.out.println("\nWallets:");
        if (financeManager.getWallets().isEmpty()) {
            System.out.println("No wallets found.");
            return;
        }

        financeManager.getWallets().values().forEach(wallet -> {
            System.out.println("Wallet ID: " + wallet.getWalletId() +
                    ", Name: " + wallet.getName() +
                    ", Balance: " + wallet.getBalance());
            if (wallet.getTransactions().isEmpty()) {
                System.out.println("  No transactions yet.");
            } else {
                wallet.getTransactions().forEach(transaction -> {
                    System.out.println("  Transaction ID: " + transaction.getTransactionId() +
                            ", Type: " + transaction.getType() +
                            ", Amount: " + transaction.getAmount());
                });
            }
        });
    }
}
