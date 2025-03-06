package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

/**
 * Enum untuk metode pembayaran yang tersedia
 */
@Getter
public enum PaymentMethod {

    VOUCHER_CODE("VOUCHER_CODE"),
    CASH_ON_DELIVERY("CASH_ON_DELIVERY");

    private final String value;

    private PaymentMethod(String paymentMethod) {
        this.value = paymentMethod;
    }

    /**
     * Memeriksa apakah parameter yang diberikan merupakan nilai yang valid dalam enum
     * @param param Parameter yang akan diperiksa
     * @return true jika parameter valid, false jika tidak
     */
    public static boolean contains(String param) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}
