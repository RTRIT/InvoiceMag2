package com.example.invoiceProject.Controller;

import com.example.invoiceProject.Model.Product;
import com.example.invoiceProject.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String getAllProducts(ModelMap model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/index";
    }

    @GetMapping("/create")
    public String createProductForm(ModelMap model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product) {
        productService.createProduct(product);
        return "redirect:/product/list";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable UUID id, ModelMap model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id)); // Ném lỗi nếu không tìm thấy
        model.addAttribute("product", product); // Thêm đối tượng thực tế vào model
        return "product/edit";
    }
    

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable UUID id, @ModelAttribute Product product) {
        productService.updateProduct(product, id);
        return "redirect:/product/list";
    }    

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return "redirect:/product/list";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, ModelMap model) {
        List<Product> products = productService.searchProductsByKeyword(keyword);
        model.addAttribute("products", products);
        return "product/index";
    }
 }
