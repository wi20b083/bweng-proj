package com.example.bwengproj.controllers;

import com.example.bwengproj.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return null;
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    public ResponseEntity<?> createProduct(String json) {
        return null;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, String json) {
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return null;
    }
}
