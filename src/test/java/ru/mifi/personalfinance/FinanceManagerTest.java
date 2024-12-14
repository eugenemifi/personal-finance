package ru.mifi.personalfinance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mifi.personalfinance.model.Transaction;
import ru.mifi.personalfinance.model.TransactionType;
import ru.mifi.personalfinance.model.User;
import ru.mifi.personalfinance.model.Wallet;
import ru.mifi.personalfinance.service.FinanceManager;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FinanceManagerTest {
    private FinanceManager financeManager;
    private User user;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        financeManager = new FinanceManager();

        // Создание тестового пользователя
        user = User.builder()
                .userId(1)
                .username("testuser")
                .password("password123")
                .build();

        // Создание тестового кошелька
        wallet = Wallet.builder()
                .walletId(1)
                .name("Test Wallet")
                .balance(1000.0)
                .transactions(new ArrayList<>())
                .build();
    }

    @Test
    void givenUser_whenAddUser_thenUserIsAdded() {
        // Given
        assertEquals(0, financeManager.getUsers().size());

        // When
        financeManager.addUser(user);

        // Then
        assertEquals(1, financeManager.getUsers().size());
        assertEquals(user, financeManager.getUsers().get(1));
    }

    @Test
    void givenWallet_whenAddWallet_thenWalletIsAdded() {
        // Given
        assertEquals(0, financeManager.getWallets().size());

        // When
        financeManager.addWallet(wallet);

        // Then
        assertEquals(1, financeManager.getWallets().size());
        assertEquals(wallet, financeManager.getWallets().get(1));
    }

    @Test
    void givenWallet_whenAddExpense_thenTransactionIsAddedAndBalanceUpdated() {
        // Given
        financeManager.addWallet(wallet);
        double initialBalance = wallet.getBalance();
        double expenseAmount = 200.0;

        // When
        financeManager.addExpense(wallet, expenseAmount);

        // Then
        assertEquals(initialBalance - expenseAmount, wallet.getBalance());
        assertEquals(1, wallet.getTransactions().size());

        Transaction transaction = wallet.getTransactions().get(0);
        assertEquals(expenseAmount, transaction.getAmount());
        assertEquals(TransactionType.EXPENSE, transaction.getType());
    }

    @Test
    void givenWallet_whenAddIncome_thenTransactionIsAddedAndBalanceUpdated() {
        // Given
        financeManager.addWallet(wallet);
        double initialBalance = wallet.getBalance();
        double incomeAmount = 500.0;

        // When
        financeManager.addIncome(wallet, incomeAmount);

        // Then
        assertEquals(initialBalance + incomeAmount, wallet.getBalance());
        assertEquals(1, wallet.getTransactions().size());

        Transaction transaction = wallet.getTransactions().get(0);
        assertEquals(incomeAmount, transaction.getAmount());
        assertEquals(TransactionType.INCOME, transaction.getType());
    }

    @Test
    void givenWallet_whenAddMultipleTransactions_thenAllAreHandledCorrectly() {
        // Given
        financeManager.addWallet(wallet);

        // When
        financeManager.addExpense(wallet, 300.0);
        financeManager.addIncome(wallet, 500.0);

        // Then
        assertEquals(2, wallet.getTransactions().size());

        Transaction firstTransaction = wallet.getTransactions().get(0);
        assertEquals(300.0, firstTransaction.getAmount());
        assertEquals(TransactionType.EXPENSE, firstTransaction.getType());

        Transaction secondTransaction = wallet.getTransactions().get(1);
        assertEquals(500.0, secondTransaction.getAmount());
        assertEquals(TransactionType.INCOME, secondTransaction.getType());

        assertEquals(1200.0, wallet.getBalance()); // 1000 - 300 + 500
    }

    @Test
    void givenUnsupportedTransactionType_whenAddTransaction_thenThrowsException() {
        // Given
        Transaction invalidTransaction = Transaction.builder()
                .transactionId(UUID.randomUUID())
                .amount(100.0)
                .type(null)
                .build();

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                financeManager.addTransaction(wallet, invalidTransaction)
        );

        assertEquals("Unsupported transaction type", exception.getMessage());
    }
}
