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
        String query = "FROM Client WHERE id_gym = :id_gym";
        String inner = "FROM Client INNER JOIN Payments WHERE Client.id_gym = :id_gym";
        return entityManager.createQuery(query)
                .setParameter("id_gym",jwtUtil.getId_gym())
                .getResultList();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Client getClientById(int id) {
        List<Client> client = entityManager.createQuery("FROM Client WHERE id = :id AND id_gym = :id_gym")
                .setParameter("id",id)
                .setParameter("id_gym",jwtUtil.getId_gym())
                .getResultList();

        if(client.isEmpty()){
            return null;
        }else{
            return client.get(0);
        }
    }
    @Override
    public Client getClientByEmail(Client client) {
        List<Client> results = entityManager.createQuery("FROM Client WHERE email = :email AND id_gym = :id_gym")
                .setParameter("email",client.getEmail())
                .setParameter("id_gym",jwtUtil.getId_gym())
                .getResultList();

        if(results.isEmpty()){
            return null;
        }else{
            return results.get(0);
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
