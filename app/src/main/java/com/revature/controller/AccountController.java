package com.revature.controller;

import com.revature.model.Account;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import io.javalin.core.validation.BodyValidator;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountController implements Controller {
    private AccountService accountService;

    public AccountController() { this.accountService = new AccountService();}

    private final Handler getAllAccountsByClientId = (ctx) -> {
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
                Double lessThan = ctx.queryParamAsClass("amountLessThan", Double.class).get();
                Double greaterThan = ctx.queryParamAsClass("amountGreaterThan", Double.class).get();
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
                Double lessThan = ctx.queryParamAsClass("amountLessThan", Double.class).get();
                for (Account account : accounts) {
                    double balance = account.getBalance();
                    if (balance > lessThan) toRemove.add(account);
                }

            } else if (map.containsKey("amountGreaterThan")) {
                Double greaterThan = ctx.queryParamAsClass("amountGreaterThan", Double.class).get();
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

    private final Handler getAccountById = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int client_id = Integer.parseInt(ctx.pathParam("client_id"));

        Account account = accountService.getAccountById(id);
        throwIfNotSameClient(account,client_id);
        ctx.json(account);
    };

    private final Handler createNewAccount = (ctx) -> {
        Account newAccount = sanitize(ctx);
        int client_id = Integer.parseInt(ctx.pathParam("client_id"));
        throwIfNotSameClient(newAccount, client_id);
        Account createdAccount = accountService.createAccount(newAccount);
        ctx.status(201);
        ctx.json(createdAccount);
    };

    private final Handler updateAccount = (ctx) -> {
        Account accountToUpdate = sanitize(ctx);
        int client_id = Integer.parseInt(ctx.pathParam("client_id"));
        throwIfNotSameClient(accountToUpdate, client_id);
        Account updatedAccount = accountService.updateAccount(accountToUpdate);
        ctx.status(200);
        ctx.json(updatedAccount);
    };

    private final Handler deleteAccount = (ctx) -> {
        String id = ctx.pathParam("id");
        String client_id = ctx.pathParam("client_id");
        if(accountService.deleteAccount(id,client_id)) {
            ctx.status(200);
            ctx.json("Account with id: " + id + " deleted");
        }else {
            ctx.status(400);
            ctx.json("Unable to perform delete operation");
        }
    };

    private void throwIfNotSameClient(Account account, int clientId) {
        if (account.getClientId() != clientId) throw new UnauthorizedResponse("Client id does not match");
    }
    private Account sanitize(Context ctx) {
        BodyValidator<Account> body = ctx.bodyValidator(Account.class);
        return body.check(account -> Account.types.contains(Account.AccountType.valueOf(account.getType())),
                        "Invalid account type")
                .check(account -> account.getClientId() > 0, "Invalid Client ID")
                .get();
    }

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients/{client_id}/accounts", getAllAccountsByClientId);
        app.get("/clients/{client_id}/accounts/{id}", getAccountById);
        app.post("/clients/{client_id}/accounts", createNewAccount);
        app.put("/clients/{client_id}/accounts/{id}", updateAccount);
        app.delete("/clients/{client_id}/accounts/{id}", deleteAccount);
    }
}
