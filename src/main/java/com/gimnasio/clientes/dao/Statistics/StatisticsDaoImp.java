package com.gimnasio.clientes.dao.Statistics;

import com.gimnasio.clientes.models.Statistics;
import com.gimnasio.clientes.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class StatisticsDaoImp implements StatisticsDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public Map getProfitsByDateRange(Map dates) {
        String query = "FROM Statistics WHERE id_gym = :id_gym AND payment_date >= :date_start AND payment_date <= :date_end";
        Integer profits = 0;
        List<Statistics> statistics = entityManager.createQuery(query)
                .setParameter("id_gym",jwtUtil.getId_gym())
                .setParameter("date_start",dates.get("date_start"))
                .setParameter("date_end",dates.get("date_end"))
                .getResultList();
        if (statistics.isEmpty()){
            return null;
        }else{
            Map <String,Integer> statisticByRange = new HashMap<>();

            for(Statistics statistic : statistics){
                profits = profits + statistic.getPayment_value();
            }
            statisticByRange.put("customersNumber",statistics.size());
            statisticByRange.put("profits",profits);
            return statisticByRange;
        }

    }

    @Override
    public void addNewStatistic(Statistics statistics) {
        entityManager.merge(statistics);
    }

}
