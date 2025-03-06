package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

/**
 * Enum untuk status pembayaran
 */
@Getter
public enum PaymentStatus {

    WAITING("WAITING"),
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED");

    private final String value;

    private PaymentStatus(String paymentStatus) {
        this.value = paymentStatus;
    }

    /**
     * Memeriksa apakah parameter yang diberikan merupakan nilai yang valid dalam enum
     * @param param Parameter yang akan diperiksa
     * @return true jika parameter valid, false jika tidak
     */
    public static boolean contains(String param) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}
