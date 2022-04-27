package com.gimnasio.clientes.dao.Clients;

import com.gimnasio.clientes.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientDao {
    List<Client> getClientList();
    Client getClientById(@PathVariable int id);
    Client getClientByDni(Client client);
    void createClient(Client client);
    void updateClient(Client client);
    void deleteClientById(int id);

}
