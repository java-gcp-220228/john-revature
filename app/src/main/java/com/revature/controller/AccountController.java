package com.revature.controller;

import com.revature.exception.AccountNotFoundException;
import com.revature.exception.WrongAccountException;
import com.revature.model.Account;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountController implements Controller {
    private AccountService accountService;

    public AccountController() { this.accountService = new AccountService();}
    private Handler getAllAccountsByClientId = (ctx) -> {
        int client_id = Integer.parseInt(ctx.pathParam("client_id"));
        List<Account> accounts = accountService.getAllAccountByClientId(client_id);
        if (accounts==null) {
            ctx.result("No accounts");
        }else {
            Map<String, List<String>> map = ctx.queryParamMap();
            List<Account> toRemove = new ArrayList<>();
            boolean errorFlag = false;
            String errorMsg = "";
            if (map.containsKey("amountLessThan") && map.containsKey("amountGreaterThan")) {
                Double lessThan = Double.parseDouble(ctx.queryParam("amountLessThan"));
                Double greaterThan = Double.parseDouble(ctx.queryParam("amountGreaterThan"));
                if(lessThan.compareTo(greaterThan) > 0) {
                    for (Account account : accounts) {
                        double balance = account.getBalance();
                        if (balance > lessThan || balance < greaterThan) toRemove.add(account);
                    }
                }else {
                        errorFlag = true;
                        errorMsg = "Invalid range";
                }
            } else if (map.containsKey("amountLessThan")) {
                Double lessThan = Double.parseDouble(ctx.queryParam("amountLessThan"));
                for (Account account : accounts) {
                    double balance = account.getBalance();
                    if (balance > lessThan) toRemove.add(account);
                }

            } else if (map.containsKey("amountGreaterThan")) {
                Double greaterThan = Double.parseDouble(ctx.queryParam("amountGreaterThan"));
                for (Account account : accounts) {
                    double balance = account.getBalance();
                    if (balance < greaterThan) toRemove.add(account);
                }

            }
            if (!errorFlag) {
                accounts.removeAll(toRemove);
                if (accounts.isEmpty()) {
                    ctx.result("No accounts");
                } else {
                    ctx.json(accounts);
                }
            }else {
                ctx.result(errorMsg);
            }
        }
    };

    private Handler getAccountById = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int client_id = Integer.parseInt(ctx.pathParam("client_id"));

        Account account = accountService.getAccountById(id, client_id);
        ctx.json(account);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients/{client_id}/accounts", getAllAccountsByClientId);
        app.get("/clients/{client_id}/accounts/{id}", getAccountById);
    }
}
