import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean authenticate(String userId, String userPin) {
        return this.userId.equals(userId) && this.userPin.equals(userPin);
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            if (amount % 500 == 0) {
                balance -= amount;
                transactionHistory.add(new Transaction("Withdraw", amount));
                System.out.println("Amount withdrawn: $" + amount);
            } else {
                System.out.println("Invalid withdrawal amount");
            }
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            System.out.println("Amount deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void transfer(double amount, Account recipient) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                recipient.balance += amount;
                transactionHistory.add(new Transaction("Transfer to " + recipient.userId, amount));
                System.out.println("Amount transferred: $" + amount);
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Invalid transfer amount ");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transaction history found");
        } else {
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction.getType() + ": $" + transaction.getAmount());
            }
        }
    }
}

public class ATM {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Account account = new Account("123456", "1234", 10000.0);

            System.out.println("Welcome to the ATM Interface!");

            System.out.print("Enter User ID: ");
            String userId = sc.nextLine();

            System.out.print("Enter User PIN: ");
            String userPin = sc.nextLine();

            if (account.authenticate(userId, userPin)) {
                int choice;
                do {
                    System.out.println("ATM Menu:");
                    System.out.println("1. Deposit");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Transfer");
                    System.out.println("4. Balance");
                    System.out.println("5. Transaction History");
                    System.out.println("6. Exit");

                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.print("Enter the amount to deposit: $");
                            double depositAmount = sc.nextDouble();
                            sc.nextLine();
                            account.deposit(depositAmount);
                            break;
                        case 2:
                            System.out.println("Only 500 notes are available");
                            System.out.print("Enter the amount to withdraw: $");
                            double withdrawAmount = sc.nextDouble();
                            sc.nextLine();
                            account.withdraw(withdrawAmount);
                            break;
                        case 3:
                            System.out.print("Enter the recipient's User ID: ");
                            String recipientId = sc.nextLine();
                            System.out.print("Enter the amount to transfer: $");
                            double transferAmount = sc.nextDouble();
                            sc.nextLine();
                            Account recipient = new Account(recipientId, "", 0);
                            account.transfer(transferAmount, recipient);
                            break;
                        case 4:
                            System.out.println("Current Balance: $" + account.getBalance());
                            break;
                        case 5:
                            account.printTransactionHistory();
                            break;
                        case 6:
                            System.out.println("Thank you for using our ATM");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                    System.out.println();
                } while (choice != 6);
            } else {
                System.out.println("Authentication failed. Invalid User ID or PIN.");
            }
        }
    }
}
