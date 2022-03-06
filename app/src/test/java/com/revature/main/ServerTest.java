package com.revature.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServerTest {
    @Test
    public void test() {
        Server testServer = new Server();
        Assertions.assertNotNull(testServer);
    }
}
