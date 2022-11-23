package com.example.bwengproj.controllers;

import com.example.bwengproj.model.Product;
import com.example.bwengproj.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    // Save operation
    @PostMapping("/products")
    public Product saveProduct(@Valid @RequestBody Product product){

        return productService.saveProduct(product);
    }

    // Read operation
    @GetMapping("/products")
    public Iterable<Product> fetchProductList(){
        return productService.fetchProductList();
    }

    // Update operation
    @PutMapping("/products/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable("id") long productId){
        return productService.updateProduct(product, productId);
    }

    // Delete operation
    @DeleteMapping("/products/{id}")
    public String deleteProductById(@PathVariable("id") long productId){
        productService.deleteProductById(productId);
        return "Deleted Successfully";
    }

    // get by id
    @GetMapping("/products/{id}")
    public Product fetchProductByID(@PathVariable("id") long productId){
        return productService.fetchProductById(productId);
    }
}
