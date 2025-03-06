package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderService orderService;

    private PaymentServiceImpl paymentService;

    private Order mockOrder;
    private Payment mockPayment;
    private Map<String, String> voucherCodePayment;
    private Map<String, String> cashOnDeliveryPayment;

    private static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        products.add(product1);
        products.add(product2);
        return products;
    }

    @BeforeEach
    void setUp() {
        // Inisialisasi paymentService secara manual
        paymentService = new PaymentServiceImpl(paymentRepository, orderService);
        
        mockOrder = Order.builder().id("787c1e14-8383-4308-b2d5-f924b9d588b8")
                .products(getProducts())
                .orderTime(1708560000L)
                .author("Husin")
                .status(OrderStatus.WAITING_PAYMENT.getValue())
                .build();

        voucherCodePayment = new HashMap<>();
        voucherCodePayment.put("voucherCode", "VOUCHER12345678901");

        cashOnDeliveryPayment = new HashMap<>();
        cashOnDeliveryPayment.put("address", "Rawajati Barat V No. 35");
        cashOnDeliveryPayment.put("deliveryFee", "30000");

        mockPayment = new Payment();
        mockPayment.setId("payment-123");
        mockPayment.setOrder(mockOrder);
        mockPayment.setMethod(PaymentMethod.VOUCHER_CODE.getValue());
        mockPayment.setStatus(PaymentStatus.SUCCESS.getValue());
        mockPayment.setPaymentData(voucherCodePayment);
    }

    @Test
    void testAddPaymentVoucherCode() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);
        

        Payment payment = paymentService.addPayment(mockOrder, PaymentMethod.VOUCHER_CODE, voucherCodePayment);

        assertNotNull(payment);
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), payment.getMethod());
        assertEquals("VOUCHER12345678901", payment.getPaymentData().get("voucherCode"));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentCashOnDelivery() {
        Payment codMockPayment = new Payment();
        codMockPayment.setId("payment-456");
        codMockPayment.setOrder(mockOrder);
        codMockPayment.setMethod(PaymentMethod.CASH_ON_DELIVERY.getValue());
        codMockPayment.setStatus(PaymentStatus.WAITING.getValue());
        codMockPayment.setPaymentData(cashOnDeliveryPayment);
        
        when(paymentRepository.save(any(Payment.class))).thenReturn(codMockPayment);

        Payment payment = paymentService.addPayment(mockOrder, PaymentMethod.CASH_ON_DELIVERY, cashOnDeliveryPayment);

        assertNotNull(payment);
        assertEquals(PaymentMethod.CASH_ON_DELIVERY.getValue(), payment.getMethod());
        assertEquals("Rawajati Barat V No. 35", payment.getPaymentData().get("address"));
        assertEquals("30000", payment.getPaymentData().get("deliveryFee"));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatus() {
        when(paymentRepository.findById(anyString())).thenReturn(mockPayment);
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);
        

        Payment updatedPayment = paymentService.setStatus("payment-123", PaymentStatus.SUCCESS);

        assertNotNull(updatedPayment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        verify(paymentRepository, times(1)).findById(anyString());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderService, times(1)).updateStatus(anyString(), anyString());
    }

    @Test
    void testGetPayment() {
        when(paymentRepository.findById(anyString())).thenReturn(mockPayment);

        Payment retrievedPayment = paymentService.getPayment("payment-123");

        assertNotNull(retrievedPayment);
        assertEquals(mockPayment, retrievedPayment);
        verify(paymentRepository, times(1)).findById(anyString());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> paymentList = Arrays.asList(mockPayment);
        when(paymentRepository.findAll()).thenReturn(paymentList);

        List<Payment> retrievedPayments = paymentService.getAllPayments();

        assertNotNull(retrievedPayments);
        assertEquals(1, retrievedPayments.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testVoucherCodeValidation() {
        Map<String, String> validVoucher = new HashMap<>();
        validVoucher.put("voucherCode", "VOUCHER12345678901");
        
        Map<String, String> invalidVoucher = new HashMap<>();
        invalidVoucher.put("voucherCode", "INVALID123");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment payment = invocation.getArgument(0);
            return payment;
        });
        
        Payment validPayment = paymentService.addPayment(mockOrder, PaymentMethod.VOUCHER_CODE, validVoucher);
        Payment invalidPayment = paymentService.addPayment(mockOrder, PaymentMethod.VOUCHER_CODE, invalidVoucher);
        
        assertEquals(PaymentStatus.SUCCESS.getValue(), validPayment.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), invalidPayment.getStatus());
    }

    @Test
    void testCashOnDeliveryValidation() {
        Map<String, String> validCOD = new HashMap<>();
        validCOD.put("address", "Rawajati Barat V No. 35");
        validCOD.put("deliveryFee", "30000");
        
        Map<String, String> invalidCOD = new HashMap<>();
        invalidCOD.put("address", "");
        invalidCOD.put("deliveryFee", "0");
        
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment payment = invocation.getArgument(0);
            return payment;
        });
        
        Payment validPayment = paymentService.addPayment(mockOrder, PaymentMethod.CASH_ON_DELIVERY, validCOD);
        Payment invalidPayment = paymentService.addPayment(mockOrder, PaymentMethod.CASH_ON_DELIVERY, invalidCOD);
        
        assertEquals(PaymentStatus.WAITING.getValue(), validPayment.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), invalidPayment.getStatus());
    }
} 