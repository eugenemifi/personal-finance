package ru.mifi.personalfinance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mifi.personalfinance.model.TransactionType;
import ru.mifi.personalfinance.model.User;
import ru.mifi.personalfinance.model.Wallet;
import ru.mifi.personalfinance.service.FinanceService;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FinanceManagerTest {

    private FinanceService financeService;
    private User user;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        financeService = new FinanceService();

        // Создание тестового пользователя
        user = User.builder()
                .id(1)
                .username("testuser")
                .password("password123")
                .wallets(new HashMap<>())
                .build();
        financeService.addUser(user);

        // Создание тестового кошелька
        wallet = Wallet.builder()
                .id(100)
                .name("Test Wallet")
                .balance(1000.0)
                .transactions(new ArrayList<>())
                .budgetByCategory(new HashMap<>())
                .build();
        user.getWallets().put(wallet.getName(), wallet);
    }

    @Test
    void givenNewUser_whenAddUser_thenUserIsAddedSuccessfully() {
        // Given
        User newUser = User.builder()
                .id(2)
                .username("newuser")
                .password("password123")
                .wallets(new HashMap<>())
                .build();

        // When
        financeService.addUser(newUser);

        // Then
        assertTrue(financeService.getUsers().containsKey("newuser"));
    }

    @Test
    void givenExistingUser_whenAddUser_thenThrowsException() {
        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                financeService.addUser(user));
        assertEquals("User already exists!", exception.getMessage());
    }

    @Test
    void givenValidWallet_whenAddIncome_thenBalanceAndTransactionsAreUpdated() {
        // Given
        double incomeAmount = 500.0;

        // When
        financeService.addIncome(wallet, incomeAmount);

        // Then
        assertEquals(1500.0, wallet.getBalance());
        assertEquals(1, wallet.getTransactions().size());
        assertEquals(incomeAmount, wallet.getTransactions().get(0).getAmount());
        assertEquals(TransactionType.INCOME, wallet.getTransactions().get(0).getType());
    }

    @Test
    void givenValidWallet_whenAddExpense_thenBalanceAndTransactionsAreUpdated() {
        // Given
        double expenseAmount = 300.0;

        // When
        financeService.addExpense(wallet, expenseAmount);

        // Then
        assertEquals(700.0, wallet.getBalance());
        assertEquals(1, wallet.getTransactions().size());
        assertEquals(expenseAmount, wallet.getTransactions().get(0).getAmount());
        assertEquals(TransactionType.EXPENSE, wallet.getTransactions().get(0).getType());
    }

    @Test
    void givenWalletWithMultipleTransactions_whenCalculateTotals_thenTotalsAreCorrect() {
        // Given
        financeService.addIncome(wallet, 1000.0);
        financeService.addExpense(wallet, 200.0);
        financeService.addExpense(wallet, 300.0);

        // When
        double totalIncome = financeService.getWallets()
                .get(wallet.getId())
                .getTransactions()
                .stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(t -> t.getAmount())
                .sum();

        double totalExpense = financeService.getWallets()
                .get(wallet.getId())
                .getTransactions()
                .stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(t -> t.getAmount())
                .sum();

        // Then
        assertEquals(1000.0, totalIncome);
        assertEquals(500.0, totalExpense);
    }
}
