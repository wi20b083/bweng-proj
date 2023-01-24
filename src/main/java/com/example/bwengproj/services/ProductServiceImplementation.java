package com.example.bwengproj.services;

import com.example.bwengproj.dto.ProductDto;
import com.example.bwengproj.model.Product;
import com.example.bwengproj.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product create(ProductDto dto) {
        Product db = new Product();
        db.setName(dto.name());
        db.setDescription(dto.description());
        db.setImagePath(dto.imagePath());
        return productRepository.save(db);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product get(Long id) {
        Optional<Product> p = productRepository.findById(id);
        if(p.isPresent()) {
            return p.get();
        } else {
            throw new EntityNotFoundException("No product found with ID " + id);
        }
    }

    @Override
    public Product update(Long id, ProductDto dto) {
        Product db = get(id);
        db.setName(dto.name());
        db.setDescription(dto.description());
        db.setImagePath(dto.imagePath());
        return productRepository.save(db);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }
}
