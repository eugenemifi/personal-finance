package ru.mifi.personalfinance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    private int walletId;
    private String name;
    private double balance;
    private List<Transaction> transactions;
}
