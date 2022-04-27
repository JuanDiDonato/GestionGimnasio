package com.gimnasio.clientes.dao.Gym;

import com.gimnasio.clientes.models.Gym;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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

    /**
     *
     * @param gym
     */
    @Override
    public void registerGym(Gym gym) {
        entityManager.merge(gym);
    }

    @Override
    public Gym getGymByName(Gym gym) {
        List<Gym> gimnasio = entityManager.createQuery("FROM Gym WHERE gym_name = :gym_name")
                .setParameter("gym_name",gym.getGym_name())
                .getResultList();

        if(gimnasio.isEmpty()){
            return null;
        }else{
            return gimnasio.get(0);
        }
    }

    @Override
    public Gym authGym(Gym gym) {
        List<Gym> gimnasio = entityManager.createQuery("FROM Gym WHERE gym_name = :gym_name")
                .setParameter("gym_name",gym.getGym_name())
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
}
