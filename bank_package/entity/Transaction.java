package bank_package.entity;

import java.time.LocalDate;

public class Transaction {
    private LocalDate transactionDate;
    private String userId;
    private String forUserId;
    private Double transactionAmount;
    private String transactionType;
    private Double initialBalance;
    private Double finalBalance;

    public Transaction(LocalDate transactionDate, String userId, String forUserId, Double transactionAmount, String transactionType, Double initialBalance, Double finalBalance) {
        this.transactionDate = transactionDate;
        this.userId = userId;
        this.forUserId = forUserId;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(Double finalBalance) {
        this.finalBalance = finalBalance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getForUserId() {
        return forUserId;
    }

    public void setForUserId(String forUserId) {
        this.forUserId = forUserId;
    }
}
