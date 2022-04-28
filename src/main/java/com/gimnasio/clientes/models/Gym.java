package com.gimnasio.clientes.models;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Table(name = "gym")
public class Gym {

    @Getter
    @Setter
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    public boolean isValid(){
        if(name == null || name == "" || password == null || password == "" || email == null || email == ""){
            return false;
        }
        return true;
    }
}
