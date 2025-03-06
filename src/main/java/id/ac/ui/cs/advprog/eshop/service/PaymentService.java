package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.List;
import java.util.Map;

/**
 * Interface untuk layanan pembayaran
 */
public interface PaymentService {

    
    Payment addPayment(Order order, PaymentMethod paymentMethod, Map<String, String> paymentData);

    
    Payment setStatus(String id, PaymentStatus status);

    
    Payment getPayment(String id);

    
    List<Payment> getAllPayments();
} 