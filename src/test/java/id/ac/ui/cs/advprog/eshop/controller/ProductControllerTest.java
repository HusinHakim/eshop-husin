package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Mock
    private ProductService service;

    @Test
    public void productListPageTest() throws Exception {
        mvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product' List")));
    }

    @Test
    public void createProductPageTest() throws Exception {
        mvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Create New Product")));
    }

    @Test
    public void createProductPostTest() throws Exception {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Mockito.when(service.create(product)).thenReturn(product);
        mvc.perform(post("/product/create").flashAttr("product", product))
                .andExpect(status().is3xxRedirection());

        Mockito.when(service.findAll()).thenReturn(List.of(product));
        mvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product' List")))
                .andExpect(content().string(containsString("Sampo Cap Bambang")))
                .andExpect(content().string(containsString("100")));
    }

    @Test
    public void editProductPageTest() throws Exception {
        Product product = new Product();
        product.setProductName("Sampo Cap Usep");
        product.setProductQuantity(444);
        productRepository.create(product);

        mvc.perform(get("/product/edit/" + product.getProductId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Edit Product")));
    }

    @Test
    public void editProductPostTest() throws Exception {
        Product product = new Product();
        product.setProductName("Sampo Cap Usep");
        product.setProductQuantity(444);
        productRepository.create(product);

        Product editedProduct = new Product();
        editedProduct.setProductId(product.getProductId());
        editedProduct.setProductName("Sampo Cap Usep EDIT");
        editedProduct.setProductQuantity(888);

        Mockito.when(service.edit(editedProduct)).thenReturn(editedProduct);
        mvc.perform(post("/product/edit/" + editedProduct.getProductId()).flashAttr("product", editedProduct))
                .andExpect(status().is3xxRedirection());

        Mockito.when(service.findAll()).thenReturn(List.of(product));
        mvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product' List")))
                .andExpect(content().string(containsString("Sampo Cap Usep EDIT")))
                .andExpect(content().string(containsString("888")));
    }

    @Test
    public void deleteProductTest() throws Exception {
        Product product = new Product();
        product.setProductName("Sampo Cap Usep DELETED");
        product.setProductQuantity(777);
        productRepository.create(product);

        mvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product' List")))
                .andExpect(content().string(containsString("Sampo Cap Usep DELETED")))
                .andExpect(content().string(containsString("777")));

        Mockito.when(service.delete(product.getProductId())).thenReturn(true);
        mvc.perform(get("/product/delete/" + product.getProductId()))
                .andExpect(status().is3xxRedirection());

        Mockito.when(service.findAll()).thenReturn(List.of(product));
        mvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product' List")))
                .andExpect(content().string(not(containsString("Sampo Cap Usep DELETED"))))
                .andExpect(content().string(not(containsString("777"))));
    }

}