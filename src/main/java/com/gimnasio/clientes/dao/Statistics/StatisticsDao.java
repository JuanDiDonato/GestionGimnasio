package com.gimnasio.clientes.dao.Statistics;

import com.gimnasio.clientes.models.Statistics;

import java.util.Map;

public interface StatisticsDao {
    Map getProfitsByDateRange(Map dates);
    void addNewStatistic(Statistics statistics);
}
