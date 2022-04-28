package com.gimnasio.clientes.dao.Statistics;

import java.util.Map;

public interface StatisticsDao {
    Map getProfitsByDateRange(Map dates);
}
