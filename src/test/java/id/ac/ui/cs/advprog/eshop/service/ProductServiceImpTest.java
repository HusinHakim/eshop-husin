package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    private Product sampleProduct;
    @BeforeEach
    void setUp() {
        sampleProduct = new Product("1", "Sample Product", 10);
    }
    @Test
    void testEditProduct_Success() {
        when(productRepository.update(sampleProduct)).thenReturn(sampleProduct);
        Product updatedProduct = productService.update(sampleProduct);
        assertNotNull(updatedProduct);
        assertEquals("Sample Product", updatedProduct.getProductName());
        assertEquals(10, updatedProduct.getProductQuantity());
        verify(productRepository, times(1)).update(sampleProduct);
    }
    @Test
    void testEditProduct_Failure_ProductNotFound() {
        when(productRepository.update(sampleProduct)).thenReturn(null);
        Product updatedProduct = productService.update(sampleProduct);
        assertNull(updatedProduct);
        verify(productRepository, times(1)).update(sampleProduct);
    }
    @Test
    void testDeleteProduct_Success() {
        doNothing().when(productRepository).delete("1");
        assertDoesNotThrow(() -> productService.delete("1"));
        verify(productRepository, times(1)).delete("1");
    }
    @Test
    void testDeleteProduct_Failure_ProductNotFound() {
        doNothing().when(productRepository).delete("999");
        assertDoesNotThrow(() -> productService.delete("999"));
        verify(productRepository, times(1)).delete("999");
    }
}