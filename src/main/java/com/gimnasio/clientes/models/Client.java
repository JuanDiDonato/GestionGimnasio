package com.gimnasio.clientes.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cliente")
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
    @Column(name = "dni")
    private String dni;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "surname")
    private String surname;

    @Getter
    @Setter
    @Column(name = "payment")
    private String payment;

    @Getter
    @Setter
    @Column(name = "expiration")
    private String expiration;

    @Getter
    @Setter
    @Column(name = "value")
    private Integer value;

    @Getter
    @Setter
    @Column(name = "gym")
    private String gym;

    /**
     *
     * @return
     */
    public boolean isValid(){
        if(name != null && name != "" && surname != null && surname != ""
                && dni != null && dni != "" && value != null && payment != "" &&
                payment != "" && expiration != null && expiration != "" ){
            return true;
        }
        return false;
    }
}
