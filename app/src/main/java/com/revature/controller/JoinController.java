package com.revature.controller;

import com.revature.model.Client;
import com.revature.service.JoinService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class JoinController implements Controller {

    private JoinService joinService;

    public JoinController() { this.joinService = new JoinService(); }

    private final Handler getClientAccounts = (ctx) -> {
        List<Client> clients = joinService.getClientAccounts();
        ctx.status(200);
        ctx.json(clients);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients-accounts", getClientAccounts);
    }
}
