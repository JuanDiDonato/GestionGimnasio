package com.gimnasio.clientes.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "gimnasios")
public class Gym {

    @Getter
    @Setter
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(name = "gym_name")
    private String gym_name;

    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    public boolean isValid(){
        if(gym_name == null || gym_name == "" || password == null || password == ""){
            return false;
        }
        return true;
    }
}
