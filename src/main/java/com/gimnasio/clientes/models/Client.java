package com.gimnasio.clientes.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@ToString
public class Client {

    @Getter
    @Setter
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(name = "id_gym")
    private String id_gym;

    @Getter
    @Setter
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @Column(name = "full_name")
    private String full_name;



    /**
     *
     * @return
     */
    public boolean isValid(){
        if(full_name != null && full_name != "" && email != null && email != "" ){
            return true;
        }
        return false;
    }
}
