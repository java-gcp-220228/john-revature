package com.revature.main;

import com.revature.controller.*;
import com.revature.dao.ClientDao;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.staticfiles.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    public static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.addStaticFiles("static",Location.CLASSPATH);
            javalinConfig.enableCorsForAllOrigins();
        });

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

        app.after((ctx) -> {
            logger.info("Server response: ("+ ctx.status() + ") " + ctx.resultString());
        });

        map(app, new ClientController(),
                new AccountController(),
                new JoinController(),
                new ExceptionController());

        app.start(7070);
    }

    public static void map(Javalin app, Controller... controllers) {
        for (Controller c : controllers) {
            c.mapEndpoints(app);
        }
    }
}
