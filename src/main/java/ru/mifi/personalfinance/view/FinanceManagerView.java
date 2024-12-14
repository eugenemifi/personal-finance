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
    private User loggedInUser;

    public static void main(String[] args) {
        FinanceManagerView app = new FinanceManagerView();
        app.run();
    }

    public void run() {
        System.out.println("Welcome to Personal Finance Manager!");

        while (true) {
            if (loggedInUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\nLogin Menu:");
        System.out.println("1. Login");
        System.out.println("2. Create User");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> loginUser();
            case "2" -> createUser();
            case "3" -> {
                System.out.println("Exiting application. Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Create Wallet");
        System.out.println("2. Add Transaction (Income or Expense)");
        System.out.println("3. Show Wallets");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> createWallet();
            case "2" -> addTransaction();
            case "3" -> showWallets();
            case "4" -> {
                loggedInUser = null;
                System.out.println("Logged out successfully.");
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void createUser() {
        System.out.println("\nCreate User:");
        int userId = getValidatedInteger("Enter user ID: ");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (financeManager.getUsers().containsKey(userId)) {
            System.out.println("Error: User ID already exists. Please use a different ID.");
            return;
        }

        User user = User.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .build();

        financeManager.addUser(user);
        System.out.println("User created successfully: " + username);
    }

    private void loginUser() {
        System.out.println("\nLogin:");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        loggedInUser = financeManager.getUsers().values().stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (loggedInUser != null) {
            System.out.println("Login successful. Welcome, " + loggedInUser.getUsername() + "!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void createWallet() {
        System.out.println("\nCreate Wallet:");
        int walletId = getValidatedInteger("Enter wallet ID: ");
        System.out.print("Enter wallet name: ");
        String name = scanner.nextLine();
        double balance = getValidatedDouble("Enter initial balance: ");

        if (financeManager.getWallets().containsKey(walletId)) {
            System.out.println("Error: Wallet ID already exists. Please use a different ID.");
            return;
        }

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
        int walletId = getValidatedInteger("Enter wallet ID: ");
        Wallet wallet = financeManager.getWallets().get(walletId);

        if (wallet == null) {
            System.out.println("Error: Wallet not found.");
            return;
        }

        System.out.print("Enter transaction type (INCOME/EXPENSE): ");
        String typeInput = scanner.nextLine();
        TransactionType type;
        try {
            type = TransactionType.valueOf(typeInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid transaction type. Please try again.");
            return;
        }

        double amount = getValidatedDouble("Enter amount: ");
        if (amount <= 0) {
            System.out.println("Error: Amount must be greater than zero.");
            return;
        }

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

    // Метод для валидации целых чисел
    private int getValidatedInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
    }

    // Метод для валидации чисел с плавающей точкой
    private double getValidatedDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }
}
