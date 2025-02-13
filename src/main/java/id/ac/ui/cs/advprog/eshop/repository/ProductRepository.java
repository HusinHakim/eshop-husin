package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public List<Product> findAll() {
        return new ArrayList<>(productData);
    }

    public Optional<Product> findById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();
    }

    public Product update(Product updatedProduct) {
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(updatedProduct.getProductId())) {
                productData.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }
}