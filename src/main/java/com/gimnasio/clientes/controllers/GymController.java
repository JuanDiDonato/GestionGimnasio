package com.gimnasio.clientes.controllers;

import com.gimnasio.clientes.dao.Gym.GymDao;
import com.gimnasio.clientes.models.Gym;
import com.gimnasio.clientes.util.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GymController {

    @Autowired
    private GymDao gymDao;

    @Autowired
    private JWTUtil jwtUtil;


    @RequestMapping(value = "api/gym", method = RequestMethod.POST)
    public Map setGym(@RequestBody Gym gymData, HttpServletResponse response){
            Map<String, String> res = new HashMap<String, String>();
            if(!gymData.isValid()){
                res.put("Error","true");
                res.put("Message", "Complete todos los campos");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return res;
            }else {
                Gym gimnasio = gymDao.getGymByName(gymData);
                if (gimnasio != null) {
                    res.put("Error", "true");
                    res.put("Message", "Ocurrio un error inesperado");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return res;
                } else {
                    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                    String hash = argon2.hash(12, 1024, 2, gymData.getPassword());
                    gymData.setPassword(hash);
                    gymDao.registerGym(gymData);
                    res.put("Error", "false");
                    res.put("Message", "Operacion realizada con exito");
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    return res;
                }
            }
        }

    @RequestMapping(value = "api/gym/profits", method = RequestMethod.POST)
    public Map clientProfits(@RequestBody Map dates, HttpServletResponse response, HttpServletRequest request){
        if(jwtUtil.isValidAuthTokenFromCookie(request)){
            Map<String, String> res = new HashMap<String, String>();
            Integer profits = gymDao.getProfitsByDate(dates);
            res.put("Error", "false");
            res.put("profits", profits.toString());
            response.setStatus(HttpServletResponse.SC_OK);
            return res;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }
}
