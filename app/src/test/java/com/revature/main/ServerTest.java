package com.revature.main;

import com.revature.controller.AccountController;
import com.revature.controller.ClientController;
import com.revature.controller.ExceptionController;
import io.javalin.Javalin;
import io.javalin.testtools.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ServerTest {
    private static Javalin app;
    @BeforeAll
    public static void setApp() {
        app = Javalin.create();
        Server.map(app, new ClientController(), new AccountController(), new ExceptionController());
    }

    @Test
    public void testGetToFetchClientsReturnsListOfClients() {
        TestUtil.test(app, (server, client) -> {
            assertThat(client.get("/clients").code()).isEqualTo(200);
        });
    }
}
