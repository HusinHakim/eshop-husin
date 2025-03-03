package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Payment payment;
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        id = "payment-123";
        method = "VOUCHER";
        status = "SUCCESS";
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        
        order = new Order();
        order.setId("order-123");
        
        payment = new Payment();
    }

    @Test
    void testGetAndSetId() {
        payment.setId(id);
        assertEquals(id, payment.getId());
    }

    @Test
    void testGetAndSetMethod() {
        payment.setMethod(method);
        assertEquals(method, payment.getMethod());
    }

    @Test
    void testGetAndSetStatus() {
        payment.setStatus(status);
        assertEquals(status, payment.getStatus());
    }

    @Test
    void testGetAndSetPaymentData() {
        payment.setPaymentData(paymentData);
        assertEquals(paymentData, payment.getPaymentData());
    }
    
    @Test
    void testGetAndSetOrder() {
        payment.setOrder(order);
        assertEquals(order, payment.getOrder());
    }
    
    @Test
    void testConstructorWithParameters() {
        Payment newPayment = new Payment(id, order, method, status, paymentData);
        assertEquals(id, newPayment.getId());
        assertEquals(order, newPayment.getOrder());
        assertEquals(method, newPayment.getMethod());
        assertEquals(status, newPayment.getStatus());
        assertEquals(paymentData, newPayment.getPaymentData());
    }
    
    @Test
    void testDefaultConstructor() {
        Payment newPayment = new Payment();
        assertNull(newPayment.getId());
        assertNull(newPayment.getOrder());
        assertNull(newPayment.getMethod());
        assertNull(newPayment.getStatus());
        assertNull(newPayment.getPaymentData());
    }
}
