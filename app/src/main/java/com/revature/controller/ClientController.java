package com.revature.controller;

import com.revature.exception.ClientNotFoundException;
import com.revature.model.Client;
import com.revature.service.ClientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class ClientController  implements Controller {

    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    private Handler getAllClients = (ctx) -> {
        List<Client> clients = clientService.getAllClients();

        ctx.json(clients);
    };

    private Handler getClientById = (ctx) -> {
        String id = ctx.pathParam("id");

        try {
            Client client = clientService.getClientById(id);
            ctx.json(client);
        } catch (ClientNotFoundException e) {
            ctx.json(e.getMessage());
            ctx.status(404);
        } catch (IllegalArgumentException e) {
            ctx.json(e.getMessage());
            ctx.status(400);
        }

    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients", getAllClients);
        app.get("/clients/{id}", getClientById);
    }
}
