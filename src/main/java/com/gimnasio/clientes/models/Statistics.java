package com.gimnasio.clientes.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
public class Statistics {

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
    @Column(name = "payment_value")
    private int payment_value;

    @Getter
    @Setter
    @Column(name = "payment_date")
    private String payment_date;
}
