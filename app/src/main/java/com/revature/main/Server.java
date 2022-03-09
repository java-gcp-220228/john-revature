package com.revature.main;

import com.revature.controller.AccountController;
import com.revature.controller.ClientController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import com.revature.dao.ClientDao;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    public static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.before((ctx) -> {
            logger.info(ctx.method() + " request received for " + ctx.path());
        });

        app.before("/clients/{client_id}/*", ctx -> {
            ClientDao clientDao = new ClientDao();
            int client_id = Integer.parseInt(ctx.pathParam("client_id"));
            if(clientDao.getClientById(client_id) == null){
                throw new NotFoundResponse();
            }
        });

        map(app, new ClientController(), new AccountController(), new ExceptionController());

        app.start(7070);
    }

    public static void map(Javalin app, Controller... controllers) {
        for (Controller c : controllers) {
            c.mapEndpoints(app);
        }
    }
}
