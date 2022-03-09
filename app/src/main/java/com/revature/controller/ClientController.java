package com.revature.controller;


import com.revature.model.Client;
import com.revature.service.ClientService;
import io.javalin.Javalin;
import io.javalin.core.validation.BodyValidator;
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

        Client client = clientService.getClientById(id);
        ctx.json(client);

    };

    private Handler postNewClient = (ctx) -> {
        BodyValidator<Client> body = ctx.bodyValidator(Client.class);
        Client new_client = body.check(client -> client.getAge() >= 18, "Client must be at least 18 years old")
                .check(client -> client.getFirstName() != null, "Client must have a first name")
                .check(client -> client.getLastName() != null, "Client must have a last name")
                .get();
        Client client = clientService.addClient(new_client);
        ctx.json(client);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients", getAllClients);
        app.get("/clients/{id}", getClientById);
        app.post("/clients", postNewClient);
    }
}
