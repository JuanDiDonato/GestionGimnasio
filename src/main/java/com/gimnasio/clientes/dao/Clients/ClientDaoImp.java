package com.gimnasio.clientes.dao.Clients;

import com.gimnasio.clientes.models.Client;
import com.gimnasio.clientes.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ClientDaoImp implements ClientDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private JWTUtil jwtUtil;


    /**
     *
     * @return
     */
    @Override
    public List<Client> getClientList() {
        System.out.println(jwtUtil.getGym());
        String query = "FROM Client WHERE gym = :gym";
        return entityManager.createQuery(query)
                .setParameter("gym",jwtUtil.getGym())
                .getResultList();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Client getClientById(@PathVariable int id) {
        List<Client> cliente = entityManager.createQuery("FROM Client WHERE id = :id AND gym = :gym")
                .setParameter("id",id)
                .setParameter("gym",jwtUtil.getGym())
                .getResultList();

        if(cliente.isEmpty()){
            return null;
        }else{
            return cliente.get(0);
        }
    }

    @Override
    public Client getClientByDni(Client client) {
        List<Client> cliente = entityManager.createQuery("FROM Client WHERE dni = :dni AND gym = :gym")
                .setParameter("dni",client.getDni())
                .setParameter("gym",jwtUtil.getGym())
                .getResultList();

        if(cliente.isEmpty()){
            return null;
        }else{
            return cliente.get(0);
        }

    }

    @Override
    public void createClient(Client cliente) {
        entityManager.merge(cliente);
    }

    @Override
    public void updateClient(Client client) {
        entityManager.merge(client);
    }

    @Override
    public void deleteClientById(int id) {
        Client client = entityManager.find(Client.class, id);
        entityManager.remove(client);
    }
}
