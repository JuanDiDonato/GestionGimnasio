package com.gimnasio.clientes.controllers;

import com.gimnasio.clientes.dao.Statistics.StatisticsDao;
import com.gimnasio.clientes.models.Statistics;
import com.gimnasio.clientes.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StatisticsControllers {

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/profits", method = RequestMethod.POST)
    public Map getProfitsByDateRange(@RequestBody Map dates, HttpServletResponse response, HttpServletRequest request){
        if (jwtUtil.isValidAuthTokenFromCookie(request)){
            Map profits = statisticsDao.getProfitsByDateRange(dates);
            response.setStatus(HttpServletResponse.SC_OK);
            return profits;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }

    @RequestMapping(value = "api/statistics", method = RequestMethod.POST)
    public Map getProfitsByDateRange(@RequestBody Statistics statistics, HttpServletResponse response, HttpServletRequest request){
        if (jwtUtil.isValidAuthTokenFromCookie(request)){
            Map <String,String> res = new HashMap<>();
            statistics.setId_gym(jwtUtil.getId_gym());
            statisticsDao.addNewStatistic(statistics);
            response.setStatus(HttpServletResponse.SC_OK);
            res.put("error","false");
            return res;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }
}
