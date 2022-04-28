package com.gimnasio.clientes.controllers;

import com.gimnasio.clientes.dao.Gym.GymDao;
import com.gimnasio.clientes.models.Gym;
import com.gimnasio.clientes.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private GymDao gymDao;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     *
     * @param gymData
     * @param response
     * @return
     */
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public Map loginGym(@RequestBody Gym gymData, HttpServletResponse response){
        Map<String, String> res = new HashMap<String, String>();
        if(!gymData.isValid()){
            res.put("Error","true");
            res.put("Message", "Complete todos los campos");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return res;
        }else{
            Gym credentials = gymDao.authGym(gymData);
            if(credentials != null){
                String token = jwtUtil.create(String.valueOf(credentials.getId()), credentials.getName());

                Cookie cookie = new Cookie("token",token);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);

                res.put("Error","false");
                response.setStatus(HttpServletResponse.SC_OK);
                response.addCookie(cookie);
                return res;
            }
            res.put("Error","true");
            res.put("Message", "Ocurrio un error inesperado");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return res;
        }
    }

}
