package com.gimnasio.clientes.dao.Gym;

import com.gimnasio.clientes.models.Gym;

import java.util.Date;
import java.util.Map;

public interface GymDao {
    void registerGym(Gym gym);
    Gym getGymByName(Gym gym);
    Gym authGym(Gym gym);
    Integer getProfitsByDate(Map dates);
}
