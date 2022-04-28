package com.gimnasio.clientes.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "payments")
public class Payments {

    @Getter
    @Setter
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(name = "id_client")
    private int id_client;

    @Getter
    @Setter
    @Column(name = "id_gym")
    private String id_gym;

    @Getter
    @Setter
    @Column(name = "payment_date")
    private String payment_date;

    @Getter
    @Setter
    @Column(name = "payment_exp")
    private String payment_exp;

    @Getter
    @Setter
    @Column(name = "payment_value")
    private int payment_value;

    @Getter
    @Setter
    @Column(name = "status")
    private String status;

    public boolean isValid(){
        if(payment_date == null || payment_date == "" || payment_exp == null || payment_date == ""){
            return false;
        }
        return true;
    }

}
