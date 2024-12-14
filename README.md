# Personal Finance Manager

![Personal Finance Manager](https://img.shields.io/badge/Version-1.0-blue)
![Java](https://img.shields.io/badge/Java-17-green)
![Gradle](https://img.shields.io/badge/Gradle-BuildTool-orange)

## 📖 Описание

**Personal Finance Manager** – это консольное приложение для управления личными финансами. Приложение позволяет
пользователям:

- Авторизоваться и управлять своими кошельками.
- Добавлять доходы и расходы.
- Устанавливать бюджеты по категориям.
- Отслеживать текущий баланс и состояние бюджета.

---

## 🚀 Основные возможности

- **Авторизация и регистрация пользователей**
    - Поддержка уникальных логинов и паролей.
- **Управление кошельками**
    - Создание кошельков с уникальными именами и балансами.
    - Поддержка нескольких кошельков для одного пользователя.
- **Финансовые транзакции**
    - Добавление доходов и расходов.
    - Автоматическое обновление баланса.
- **Бюджетирование**
    - Установка бюджета на категории расходов.
    - Контроль превышения бюджета.
- **Отображение данных**
    - Просмотр списка кошельков, транзакций, баланса и оставшихся бюджетов.

---

## 🛠️ Технологии

- **Java 17** – основной язык разработки.
- **Gradle** – инструмент сборки.
- **JUnit 5** – тестирование.

---

## 📋 Требования

- Установленная Java Development Kit (JDK) версии 17 или выше.
- Инструмент сборки Gradle (или поддержка Gradle в вашей IDE, например IntelliJ IDEA).

---

## 📦 Установка и запуск

1. **Клонирование репозитория:**
   ```bash
   git clone https://github.com/username/personal-finance-manager.git
   cd personal-finance-manager

2. **Сборка проекта с помощью Gradle:**
   ```bash
   ./gradlew build


3. **Запуск приложения:**
   ```bash
   ./gradlew run


🧪 Тестирование

4. ** Для запуска тестов выполните команду:
   ```bash
   ./gradlew test

📖 Руководство пользователя
1. Регистрация пользователя
В главном меню выберите пункт Create User, введите уникальное имя пользователя и пароль.

2. Авторизация
Выберите пункт Login и введите логин и пароль, чтобы получить доступ к своим кошелькам.

3. Создание кошелька
После входа выберите Create Wallet, задайте уникальное имя кошелька и начальный баланс.

4. Добавление транзакций
В меню выберите Add Transaction, укажите тип транзакции (INCOME или EXPENSE), категорию и сумму.

5. Просмотр кошельков
Выберите Show Wallets, чтобы увидеть список кошельков, транзакции и текущий баланс.

📂 Структура проекта
```
src
├── main
│   ├── java
│   │   ├── ru.mifi.personalfinance
│   │   │   ├── model        # Модели данных: User, Wallet, Transaction
│   │   │   ├── service      # Логика приложения: FinanceService
│   │   │   ├── view         # Взаимодействие с пользователем: FinanceManagerView
│   │   └── AppRunner.java   # Точка входа в приложение
│   └── resources            # Ресурсы (если используются)
├── test                     # Юнит-тесты
│   └── java
│       └── ru.mifi.personalfinance
│           ├── FinanceManagerTest.java
└── build.gradle             # Конфигурация Gradle
```
🌟 Пример работы приложения

```yaml
    Welcome to Personal Finance Manager!
    Login Menu:
    1. Login
    2. Create User
    3. Exit
       Enter your choice: 2
    
    Create User:
    Enter user ID: 1
    Enter username: john_doe
    Enter password: ********
    User created successfully: john_doe
```

🌟 Пример работы приложения

```yaml
Main Menu:
1. Create Wallet
2. Add Transaction (Income or Expense)
3. Show Wallets
4. Logout
Enter your choice: 2

Add Transaction:
Enter wallet ID: 1
Enter transaction type (INCOME/EXPENSE): INCOME
Enter amount: 5000
INCOME transaction of 5000 added successfully.
```
