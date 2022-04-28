package com.gimnasio.clientes.controllers;

import com.gimnasio.clientes.dao.Clients.ClientDao;
import com.gimnasio.clientes.models.Client;
import com.gimnasio.clientes.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;



@RestController
public class ClientControllers {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "api/clients", method = RequestMethod.GET)
    public List<Client> getClients(HttpServletResponse response,HttpServletRequest request){
        if(jwtUtil.isValidAuthTokenFromCookie(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return clientDao.getClientList();
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }

    /**
     *
     * @param clientData
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "api/client", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map setClient(@RequestBody Client clientData, HttpServletResponse response,HttpServletRequest request){
        if(jwtUtil.isValidAuthTokenFromCookie(request)){
            Map<String, String> res = new HashMap<String, String>();
            if(!clientData.isValid()){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.put("Error","true");
                res.put("Message","Complete todos los campos");
                return res;
            }else{
                Client client = clientDao.getClientByEmail(clientData);
                if(client != null){
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    res.put("Error","true");
                    res.put("Message","Este email ya esta registrado");
                    return res;
                }else{
                    clientData.setId_gym(jwtUtil.getId_gym());
                    clientDao.createClient(clientData);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    res.put("Error","false");
                    res.put("Message","Cliente creado con exito");
                    return res;
                }
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }

    /**
     *
     * @param id
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "api/client/{id}", method = RequestMethod.GET)
    public Client getClientById(@PathVariable int id, HttpServletResponse response, HttpServletRequest request){
        if(jwtUtil.isValidAuthTokenFromCookie(request)){
            response.setStatus(HttpServletResponse.SC_OK);
            return clientDao.getClientById(id);
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }

    /**
     *
     * @param id
     * @param clientData
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "api/client/{id}", method = RequestMethod.PUT)
    public Map editClientById(@PathVariable int id, @RequestBody Client clientData ,HttpServletResponse response,HttpServletRequest request){
       if(jwtUtil.isValidAuthTokenFromCookie(request)){
           Map<String, String> res = new HashMap<String, String>();
           Client cliente = clientDao.getClientById(id);
           if(cliente != null){
               cliente.setEmail(clientData.getEmail());
               cliente.setFull_name(clientData.getFull_name());
               clientDao.updateClient(cliente);
               response.setStatus(HttpServletResponse.SC_OK);
               res.put("Error","false");
               res.put("Message","Datos actualizados con exito");
               return res;
           }else{
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               res.put("Error","true");
               res.put("Message","Ocurrio un error inesperado");
               return res;
           }
       }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }

    /**
     *
     * @param id
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "api/client/{id}", method = RequestMethod.DELETE)
    public Map deleteClientById(@PathVariable int id,HttpServletResponse response, HttpServletRequest request) {
        if(jwtUtil.isValidAuthTokenFromCookie(request)){
            Map<String, String> res = new HashMap<String, String>();
            Client cliente = clientDao.getClientById(id);
            if(cliente != null){
                clientDao.deleteClientById(id);
                res.put("Error","false");
                res.put("Message","Cliente borrado con exito");
                response.setStatus(HttpServletResponse.SC_OK);
                return res;
            }else{
                res.put("Error","true");
                res.put("Message","El cliente no existe");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return res;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }


}


