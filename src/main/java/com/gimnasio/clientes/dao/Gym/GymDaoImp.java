package com.gimnasio.clientes.dao.Gym;

import com.gimnasio.clientes.models.Client;
import com.gimnasio.clientes.models.Gym;
import com.gimnasio.clientes.util.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class GymDaoImp implements GymDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     *
     * @param gym
     */
    @Override
    public void registerGym(Gym gym) {
        entityManager.merge(gym);
    }

    @Override
    public Gym getGymByEmail(Gym gym) {
        List<Gym> gimnasio = entityManager.createQuery("FROM Gym WHERE email = :email")
                .setParameter("email",gym.getEmail())
                .getResultList();

        if(gimnasio.isEmpty()){
            return null;
        }else{
            return gimnasio.get(0);
        }
    }

    @Override
    public Gym authGym(Gym gym) {
        List<Gym> gimnasio = entityManager.createQuery("FROM Gym WHERE email = :email")
                .setParameter("email",gym.getEmail())
                .getResultList();
        if(gimnasio.isEmpty()){
            return null;
        }
        String passwordHashed = gimnasio.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(passwordHashed,gym.getPassword())){
            return gimnasio.get(0);
        }
        return null;
    }

    /*
    @Override
    public Integer getProfitsByDate(Map dates) {
        String query = "FROM Client WHERE gym = :gym AND payment >= :date_start AND payment <= :date_end";
        int profits = 0;
        List<Client> clientes = entityManager.createQuery(query)
                .setParameter("gym", jwtUtil.getGym())
                .setParameter("date_start",dates.get("date_start"))
                .setParameter("date_end",dates.get("date_end"))
                .getResultList();
        for(Client cliente : clientes){
            profits = profits + cliente.getValue();
        }


        for(int i = 0; i < clientes.size(); i++){
            profits = profits + clientes.get(i).getValue();
        }


        return profits;
    }
    */

}
