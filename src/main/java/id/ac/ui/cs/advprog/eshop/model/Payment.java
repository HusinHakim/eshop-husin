package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

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
        // Constructor with parameters
    }

    public String getId() {
        return null;
    }

    public void setId(String id) {
        // Set id
    }

    public Order getOrder() {
        return null;
    }

    public void setOrder(Order order) {
        // Set order
    }

    public String getMethod() {
        return null;
    }

    public void setMethod(String method) {
        // Set method
    }

    public String getStatus() {
        return null;
    }

    public void setStatus(String status) {
        // Set status
    }

    public Map<String, String> getPaymentData() {
        return null;
    }

    public void setPaymentData(Map<String, String> paymentData) {
        // Set paymentData
    }
} 