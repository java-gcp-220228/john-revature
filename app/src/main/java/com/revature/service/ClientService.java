package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Client;

import java.sql.SQLException;
import java.util.List;

public class ClientService {

    private ClientDao clientDao;

    public ClientService() {
        this.clientDao = new ClientDao();
    }

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Client addClient(Client client) throws SQLException {
        return this.clientDao.addClient(client);
    }

    public List<Client> getAllClients() throws SQLException {
        return this.clientDao.getAllClients();
    }

    public Client getClientById(String id) throws SQLException, ClientNotFoundException {
        try {
            int intId = Integer.parseInt(id);

            Client client = this.clientDao.getClientById(intId);

            if (client == null) {
                throw new ClientNotFoundException("Client with id: " + id + " was not found");
            }
            return client;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A value that was not corresponding to a valid integer was provided");

        }
    }

    public Client updateClient(String id, Client client) throws ClientNotFoundException, SQLException {
        try {
            getClientById(id);
        } catch (ClientNotFoundException e) {
            throw new ClientNotFoundException("Update attempt on client that does not exist: " + e.getMessage());
        }
        client.setId(Integer.parseInt(id));
        Client updatedClient = clientDao.updateClient(client);
        return updatedClient;
    }

    public boolean deleteClient(String id) throws SQLException {
        boolean success;
        try {
            int intId = Integer.parseInt(id);

            success = this.clientDao.deleteClientById(intId);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A value that was not corresponding to a valid integer was provided");

        }
        return success;
    }
}
