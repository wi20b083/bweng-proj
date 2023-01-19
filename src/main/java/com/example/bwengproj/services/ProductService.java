package com.example.bwengproj.services;

import com.example.bwengproj.model.Product;

import javax.validation.Valid;

public interface ProductService {
    // save operation
    Product saveProduct(@Valid Product product);

    // read operation
    Iterable<Product> fetchProductList();

    // update operation
    Product updateProduct(@Valid Product product, Long productId);

    // delete operation
    void deleteProductById(Long productId);

    //find by id
    Product fetchProductById(Long productId);

}
