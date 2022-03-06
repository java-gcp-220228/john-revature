package com.revature.main;

import com.revature.controller.AccountController;
import com.revature.controller.ClientController;
import com.revature.controller.Controller;
import io.javalin.Javalin;

public class Server {
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        map(app, new ClientController());
        map(app, new AccountController());

        app.start(7070);
    }

    public static void map(Javalin app, Controller... controllers) {
        for (Controller c : controllers) {
            c.mapEndpoints(app);
        }
    }
}
