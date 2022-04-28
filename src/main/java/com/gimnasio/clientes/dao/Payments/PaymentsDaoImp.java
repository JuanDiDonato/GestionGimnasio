package com.gimnasio.clientes.dao.Payments;

import com.gimnasio.clientes.models.Client;
import com.gimnasio.clientes.models.Payments;
import com.gimnasio.clientes.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class PaymentsDaoImp implements PaymentsDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     *
     * @return
     */
    @Override
    public List<Payments> getCurrentPayments() {
        String query = "FROM Payments WHERE id_gym = :id_gym" ;
        List<Payments> payments = entityManager.createQuery(query)
                .setParameter("id_gym",jwtUtil.getId_gym())
                .getResultList();
        return payments;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Payments getCurrentPaymentById(int id) {
        String query = "FROM Payments WHERE id = :id AND id_gym = :id_gym";
        List<Payments> payment = entityManager.createQuery(query)
                .setParameter("id",id)
                .setParameter("id_gym",jwtUtil.getId_gym())
                .getResultList();
        if(payment.isEmpty()){
            return null;
        }else{
            return payment.get(0);
        }

    }

    @Override
    public Payments getCurrentPaymendForClient(int id_client) {
        String query = "FROM Payments WHERE id_client = :id_client AND id_gym = :id_gym";
        List<Payments> payment = entityManager.createQuery(query)
                .setParameter("id_client",id_client)
                .setParameter("id_gym",jwtUtil.getId_gym())
                .getResultList();
        if (payment.isEmpty()){
            return null;
        }else{
            return payment.get(0);
        }
    }

    /**
     *
     * @param payment
     */
    @Override
    public void createNewPayment(Payments payment) {
        entityManager.merge(payment);
    }

    /**
     *
     * @param id
     */
    @Override
    public void deletePayment(int id) {
        Payments payment = entityManager.find(Payments.class, id);
        entityManager.remove(payment);
    }

}
