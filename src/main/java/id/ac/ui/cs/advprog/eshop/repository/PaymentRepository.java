package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository untuk menyimpan dan mengelola data Payment
 */
@Repository
public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        // If payment with the same ID already exists, remove it
        paymentData.removeIf(existingPayment -> existingPayment.getId().equals(payment.getId()));
        
        // Add the payment to the list
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        
        for (Payment payment : paymentData) {
            if (payment.getId().equals(id)) {
                return payment;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }
}