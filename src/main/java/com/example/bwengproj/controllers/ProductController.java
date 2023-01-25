package com.example.bwengproj.controllers;

import com.example.bwengproj.dto.ProductDto;
import com.example.bwengproj.model.Product;
import com.example.bwengproj.model.status.Role;
import com.example.bwengproj.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bwengproj.util.Util.objectMapper;
import static com.example.bwengproj.util.Util.response;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * Gets all {@link Product} entities from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the found {@link Product} entities attached
     */
    @GetMapping
    public ResponseEntity<?> getAll() throws JsonProcessingException {
        List<Product> products = productService.getAll();
        return response(products);
    }

    /**
     * Creates a {@link Product} entity in the Database,
     * Authentication: authenticated,
     * Authorization: {@link Role} "ROLE_ADMIN"
     *
     * @param json JSON String with {@link ProductDto} fields
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the created {@link Product} entity attached
     */
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody String json) throws JsonProcessingException {
        ProductDto dto = objectMapper.readValue(json, ProductDto.class);
        Product product = productService.create(dto);
        return response(product);
    }

    /**
     * Updates a {@link Product} entity from the Database,
     * Authentication: authenticated,
     * Authorization: {@link Role} "ROLE_ADMIN"
     *
     * @param id   ID of the {@link Product} to update
     * @param json JSON String with {@link ProductDto} fields
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the updated {@link Product} entity attached
     */
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String json) throws JsonProcessingException {
        ProductDto dto = objectMapper.readValue(json, ProductDto.class);
        Product product = productService.update(id, dto);
        return response(product);
    }

    /**
     * Deletes a {@link Product} entity from the Database,
     * Authentication: authenticated,
     * Authorization: {@link Role} "ROLE_ADMIN"
     *
     * @param id ID of the {@link Product} to update
     * @return {@link ResponseEntity} with {@link HttpStatus} 200
     */
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws JsonProcessingException {
        productService.delete(id);
        return response(null);
    }
}
