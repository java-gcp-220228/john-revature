package com.revature.model;

public class Account {
    public enum AccountType {
        CHEQUING,
        SAVINGS;
    }
    private int id;
    private String type;
    private double balance;
    private int clientId;

    public Account() {
    }

    public Account(int id, String type, double balance, int clientId) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.clientId = clientId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public int getClientId() {
        return clientId;
    }
}
