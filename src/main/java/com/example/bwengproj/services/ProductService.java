package com.example.bwengproj.services;

import com.example.bwengproj.dto.ProductDto;
import com.example.bwengproj.model.Product;

import javax.validation.Valid;
import java.util.List;

public interface ProductService {
    Product save(Product product);
    List<Product> getAll();
    Product get(Long id);
    Product update(Long id, ProductDto dto);
    void delete(Long id);
    void deleteAll();
}
