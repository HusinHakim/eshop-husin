package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

@Getter
@Setter
public class Payment {
    private String id;
    private Order order;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment() {
        // Default constructor
    }

    public Payment(String id, Order order, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    
}