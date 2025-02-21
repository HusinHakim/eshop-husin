package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl service;

    @Test
    void testCreateAndFindProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Mockito.when(productRepository.create(product)).thenReturn(product);
        service.create(product);

        Mockito.when(productRepository.findAll()).thenReturn(List.of(product).iterator());
        Iterator<Product> productIterator = service.findAll().iterator();

        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Mockito.when(productRepository.create(product)).thenReturn(product);
        service.create(product);

        product.setProductName("Sampo Cap Bambang EDIT");
        product.setProductQuantity(999);
        Mockito.when(productRepository.edit(product)).thenReturn(product);
        Product editedProduct = service.edit(product);

        assertEquals(product.getProductId(), editedProduct.getProductId());
        assertEquals("Sampo Cap Bambang EDIT", editedProduct.getProductName());
        assertEquals(999, editedProduct.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Mockito.when(productRepository.create(product)).thenReturn(product);
        service.create(product);

        Mockito.when(productRepository.delete(product.getProductId())).thenReturn(true);
        boolean isDeleted = service.delete(product.getProductId());

        assertTrue(isDeleted);
    }

    @Test
    void testFindProductById() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Mockito.when(productRepository.create(product)).thenReturn(product);
        service.create(product);

        Mockito.when(productRepository.findProductById(product.getProductId())).thenReturn(product);
        Product findProduct = service.findProductById(product.getProductId());

        assertNotNull(findProduct);
        assertEquals(product.getProductId(), findProduct.getProductId());
        assertEquals(product.getProductName(), findProduct.getProductName());
        assertEquals(product.getProductQuantity(), findProduct.getProductQuantity());
    }

}