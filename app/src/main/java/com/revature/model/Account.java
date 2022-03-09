package com.revature.model;

import java.util.EnumSet;
import java.util.Objects;

public class Account {
    public enum AccountType {
        CHEQUING,
        SAVINGS;
    }
    public static EnumSet<AccountType> types = EnumSet.allOf(AccountType.class);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Double.compare(account.balance, balance) == 0 && clientId == account.clientId && Objects.equals(type, account.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, balance, clientId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", clientId=" + clientId +
                '}';
    }
}
