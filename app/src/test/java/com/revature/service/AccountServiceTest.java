package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exception.AccountNotFoundException;
import com.revature.exception.WrongAccountException;
import com.revature.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    private AccountDao mockDao;
    private AccountService accountService;

    @BeforeEach
    void setup() {
        mockDao = mock(AccountDao.class);
        accountService = new AccountService(mockDao);
    }

    @Test
    public void testGetAllAccountByClientId() throws SQLException {
        List<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(new Account(1, Account.AccountType.SAVINGS.name(), 0, 1));
        mockAccounts.add(new Account(2, Account.AccountType.CHEQUING.name(), 10000, 1));
        mockAccounts.add(new Account(3, Account.AccountType.SAVINGS.name(), 5000, 1));
        mockAccounts.add(new Account(4, Account.AccountType.SAVINGS.name(), 250, 1));

        when(mockDao.getAllAccountsFromClientId(1)).thenReturn(mockAccounts);

        List<Account> actual = accountService.getAllAccountByClientId(1);

        List<Account> expected = new ArrayList<>(mockAccounts);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetAccountById_positive() throws SQLException, WrongAccountException, AccountNotFoundException {
        when(mockDao.getAccountById(eq(100))).thenReturn(
                new Account(100, Account.AccountType.CHEQUING.name(), 0, 1)
        );

        Account actual = accountService.getAccountById(100, 1);

        Account expected = new Account(100, Account.AccountType.CHEQUING.name(), 0, 1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetAccountById_accountDoesNotExist() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccountById(0, 1);
        });
    }

    @Test
    public void testGetAccountById_invalidId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.getAccountById(Integer.parseInt("invalid string"), 1);
        });
    }

    @Test
    public void testGetAccountById_wrongAccount() throws SQLException {
        when(mockDao.getAccountById(eq(100))).thenReturn(
                new Account(100, Account.AccountType.CHEQUING.name(), 0, 1)
        );

        Assertions.assertThrows(WrongAccountException.class, () -> {
            accountService.getAccountById(100,2);
        });
    }

    @Test
    public void testGetAccountById_sqlException() throws SQLException {
        when(mockDao.getAccountById(anyInt())).thenThrow(SQLException.class);
        Assertions.assertThrows(SQLException.class, () -> {
            accountService.getAccountById(1,1);
        });
    }
}
