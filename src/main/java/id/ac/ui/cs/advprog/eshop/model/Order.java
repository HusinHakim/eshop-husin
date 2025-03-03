package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    @Setter
    String status;


    // 4 usages
    public Order(String id, List<Product> products, Long orderTime, String author) {
    }

    // 2 usages
    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
    }
    

    
    
}
