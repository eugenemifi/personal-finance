package ru.mifi.personalfinance;

import ru.mifi.personalfinance.view.FinanceManagerView;

public class AppRunner {
    public static void main(String[] args) {
        FinanceManagerView app = new FinanceManagerView();
        app.run();
    }
}
