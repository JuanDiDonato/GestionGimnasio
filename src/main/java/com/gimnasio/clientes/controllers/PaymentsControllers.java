package com.gimnasio.clientes.controllers;

import com.gimnasio.clientes.dao.Payments.PaymentsDao;
import com.gimnasio.clientes.models.Payments;
import com.gimnasio.clientes.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentsControllers {

    @Autowired
    private PaymentsDao paymentsDao;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "api/payments", method = RequestMethod.GET)
    public List<Payments> getCurrentPayments(HttpServletRequest request, HttpServletResponse response){
        if(jwtUtil.isValidAuthTokenFromCookie(request)){
            return paymentsDao.getCurrentPayments();
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }

    /**
     *
     * @param paymentData
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "api/payments", method = RequestMethod.POST)
    public Map createNewPayment(@RequestBody Payments paymentData, HttpServletRequest request, HttpServletResponse response){
        if(jwtUtil.isValidAuthTokenFromCookie(request)){
            Map <String, String> res = new HashMap<>();
            if(paymentData.isValid()){
                paymentData.setId_gym(jwtUtil.getId_gym());
                paymentsDao.createNewPayment(paymentData);
                res.put("error","false");
                res.put("message","Operacion realizada con exito");
                response.setStatus(HttpServletResponse.SC_CREATED);
                return res;
            }else{
                res.put("error","true");
                res.put("message","Complete todos los campos");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return res;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }

    @RequestMapping(value = "api/payments/{id}", method = RequestMethod.DELETE)
    public Map deletePayment(@PathVariable int id, HttpServletRequest request, HttpServletResponse response){
        if(jwtUtil.isValidAuthTokenFromCookie(request)){
            Map <String,String> res = new HashMap<>();
            if (paymentsDao.getCurrentPaymentById(id) == null){
                res.put("error","true");
                res.put("message","Ocurrio un error inesperado");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return res;
            }else{
                paymentsDao.deletePayment(id);
                res.put("error","false");
                res.put("message","Operacion realizada con exito");
                response.setStatus(HttpServletResponse.SC_OK);
                return res;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }

}
