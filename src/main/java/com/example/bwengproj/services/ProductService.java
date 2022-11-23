package com.example.bwengproj.services;

import com.example.bwengproj.model.Product;
import com.example.bwengproj.model.User;

import java.util.List;

public interface ProductService {
    // save operation
    Product saveProduct(Product product);

    // read operation
    List<Product> fetchProductList();

    // update operation
    Product updateProduct(Product product, Long productId);

    // delete operation
    void deleteProductById(Long productId);

    //find by id
    Product fetchProductById(Long productId);

}
