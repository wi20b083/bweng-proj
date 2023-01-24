package com.example.bwengproj.controllers;

import com.example.bwengproj.dto.ProductDto;
import com.example.bwengproj.model.Product;
import com.example.bwengproj.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bwengproj.util.Util.objectMapper;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAll() throws JsonProcessingException {
        List<Product> products = productService.getAll();
        return response(products);
    }

    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody String json) throws JsonProcessingException {
        ProductDto dto = objectMapper.readValue(json, ProductDto.class);
        Product product = productService.create(dto);
        return response(product);
    }

    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String json) throws JsonProcessingException {
        ProductDto dto = objectMapper.readValue(json, ProductDto.class);
        Product product = productService.update(id, dto);
        return response(product);
    }

    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws JsonProcessingException {
        productService.delete(id);
        return response(null);
    }

    private ResponseEntity<?> response(Object o) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.writeValueAsString(o), HttpStatus.OK);
    }
}
