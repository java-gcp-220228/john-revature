package com.revature.controller;


import com.revature.model.Client;
import com.revature.service.ClientService;
import io.javalin.Javalin;
import io.javalin.core.validation.BodyValidator;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

public class ClientController  implements Controller {

    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    private final Handler getAllClients = (ctx) -> {
        List<Client> clients = clientService.getAllClients();
        ctx.status(200);
        ctx.json(clients);
    };

    private final Handler getClientById = (ctx) -> {
        String id = ctx.pathParam("id");

        Client client = clientService.getClientById(id);
        ctx.json(client);

    };

    private final Handler postNewClient = (ctx) -> {
        Client new_client = sanitize(ctx);
        Client client = clientService.addClient(new_client);
        ctx.status(201);
        ctx.json(client);
    };

    private final Handler putNewClientDetails = (ctx) -> {
        Client new_details = sanitize(ctx);
        Client updatedClient = clientService.updateClient(ctx.pathParam("id"), new_details);

        ctx.status(200);
        ctx.json(updatedClient);
    };

    private final Handler deleteClient = (ctx) -> {
        String id = ctx.pathParam("id");

        if(clientService.deleteClient(id)) {
            ctx.status(200);
            ctx.json("Client with id: " + id + " deleted");
        }else {
            ctx.status(400);
            ctx.json("Unable to perform delete operation");
        }
    };

    public Client sanitize(Context ctx) {
        BodyValidator<Client> body = ctx.bodyValidator(Client.class);
        return body.check(client -> client.getAge() > 0, "Client must have a positive age")
                .check(client -> client.getFirstName() != null, "Client must have a first name")
                .check(client -> client.getLastName() != null, "Client must have a last name")
                .get();
    }

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients", getAllClients);
        app.get("/clients/{id}", getClientById);
        app.post("/clients", postNewClient);
        app.put("/clients/{id}", putNewClientDetails);
        app.delete("/clients/{id}", deleteClient);
    }
}
