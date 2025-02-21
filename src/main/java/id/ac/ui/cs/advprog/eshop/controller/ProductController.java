package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable("id") String productId, Model model) {
        Product product = service.findProductById(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit/{id}")
    public String editProductPost(@PathVariable("id") String productId, @ModelAttribute Product product, Model model) {
        product.setProductId(productId);
        service.edit(product);
        return "redirect:../list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String productId, Model model) {
        service.delete(productId);
        return "redirect:../list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }

}
