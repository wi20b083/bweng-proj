package com.example.bwengproj.services;

import com.example.bwengproj.model.Product;
import com.example.bwengproj.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    // save operation
    @Override
    public Product saveProduct(@Valid Product product) {
        return productRepository.save(product);
    }

    // read operation
    @Override
    public Iterable<Product> fetchProductList() {
        return productRepository.findAll();
    }

    /* update operation
    @Override
    public Auction updateAuction(Auction auction, Long auctionId) {
        Auction aucDB = auctionRepository.findById(auctionId).get();

        if (Objects.nonNull(department.getDepartmentName()) && !"".equalsIgnoreCase(department.getDepartmentName())) {
            depDB.setDepartmentName(department.getDepartmentName());
        }

        if (Objects.nonNull(department.getDepartmentAddress()) && !"".equalsIgnoreCase(department.getDepartmentAddress())) {
            depDB.setDepartmentAddress(department.getDepartmentAddress());
        }

        if (Objects.nonNull(department.getDepartmentCode()) && !"".equalsIgnoreCase(department.getDepartmentCode())) {
            depDB.setDepartmentCode(department.getDepartmentCode());
        }

        return departmentRepository.save(depDB);
    }*/

    @Override
    public Product updateProduct(@Valid Product product, Long productId) {
        Product productDB = productRepository.findById(productId).get();

        // insert code with finished methods from Auction in model

        return null;
    }

    // delete operation
    @Override
    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product fetchProductById(Long productId) {
        return productRepository.findById(productId).get();
    }
}
