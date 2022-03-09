package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    private ClientDao mockDao;
    private ClientService clientService;

    @BeforeEach
    void setup() {
        mockDao = mock(ClientDao.class);
        clientService = new ClientService(mockDao);
    }

    @Test
    public void testAddClient() throws SQLException {
        Client mockClient = new Client(10, "Tom", "Brown", 56);
        when(mockDao.addClient(any())).thenReturn(mockClient);
        Client actual = clientService.addClient(new Client(0, "Tom", "Brown",56));
        Assertions.assertEquals(actual.getFirstName(), mockClient.getFirstName());
        Assertions.assertEquals(actual.getLastName(), mockClient.getLastName());
        Assertions.assertEquals(actual.getAge(), mockClient.getAge());
    }

    @Test
    public void testGetAllClients() throws SQLException {

        List<Client> mockClients = new ArrayList<>();
        mockClients.add(new Client(1, "Tom", "Davenport", 36));
        mockClients.add(new Client(2, "Chris", "Tat", 30));
        mockClients.add(new Client(3, "Bob", "Smith", 21));

        when(mockDao.getAllClients()).thenReturn(mockClients);

        List<Client> actual = clientService.getAllClients();

        List<Client> expected = new ArrayList<>(mockClients);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetClientById_positive() throws SQLException, ClientNotFoundException {
        when(mockDao.getClientById(eq(11))).thenReturn(
                new Client(11, "Test", "Case", 11)
        );

        Client actual = clientService.getClientById("11");

        Client expected = new Client(11, "Test", "Case", 11);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetClientById_clientDoesNotExist() {
        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            clientService.getClientById("11");
        });
    }

    @Test
    public void testGetClientById_invalidId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.getClientById("invalid string");
        });
    }

    @Test
    public void testGetClientById_sqlException() throws SQLException {
        when(mockDao.getClientById(anyInt())).thenThrow(SQLException.class);

        Assertions.assertThrows(SQLException.class, () -> {
            clientService.getClientById("9");
        });
    }
}
