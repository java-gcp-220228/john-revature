package com.revature.controller;

import com.revature.exception.AccountNotFoundException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.WrongAccountException;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class ExceptionController implements Controller {

    private final ExceptionHandler notFound = (e, ctx) -> {
        ctx.status(404);
        ctx.json(e.getMessage());
    };

    private final ExceptionHandler wrongAccount = (e, ctx) -> {
        ctx.status(403);
        ctx.json(e.getMessage());
    };

    private final ExceptionHandler illegalArgument = (e, ctx) -> {
        ctx.status(400);
        ctx.json(e.getMessage());
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.exception(ClientNotFoundException.class, notFound);
        app.exception(AccountNotFoundException.class, notFound);
        app.exception(WrongAccountException.class, wrongAccount);
        app.exception(IllegalArgumentException.class, illegalArgument);
    }
}
