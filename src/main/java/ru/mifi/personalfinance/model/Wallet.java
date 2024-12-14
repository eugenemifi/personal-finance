package ru.mifi.personalfinance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    private Integer id;
    private String name;
    private double balance;
    private List<Transaction> transactions; // Список транзакций
    private Map<String, Double> budgetByCategory; // Бюджет по категориям
}
