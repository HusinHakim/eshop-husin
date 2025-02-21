package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {

    private List<Product> productData = new ArrayList<>();

    private static int idCounter;

    public Product create(Product product) {
        product.setProductId(String.valueOf(idCounter++));
        productData.add(product);
        return product;
    }

    public Product edit(Product updatedProduct) {
        for (Product product : productData) {
            if (product.getProductId().equals(updatedProduct.getProductId())) {
                updatedProduct.setProductId(product.getProductId());
                int index = productData.indexOf(product);
                productData.set(index, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }

    public Product findProductById(String id) {
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public boolean delete(String id) {
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                productData.remove(product);
                return true;
            }
        }
        return false;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

}
