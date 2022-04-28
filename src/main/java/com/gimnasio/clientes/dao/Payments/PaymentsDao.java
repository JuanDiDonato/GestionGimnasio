package com.gimnasio.clientes.dao.Payments;

import com.gimnasio.clientes.models.Payments;

import java.util.List;

public interface PaymentsDao {
    List<Payments> getCurrentPayments();
    Payments getCurrentPaymentById(int id);
    Payments getCurrentPaymendForClient(int id_client);
    void createNewPayment(Payments payment);
    void deletePayment(int id);
}
