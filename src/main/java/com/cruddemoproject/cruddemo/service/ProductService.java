package com.cruddemoproject.cruddemo.service;

import com.cruddemoproject.cruddemo.entity.Product;
import com.cruddemoproject.cruddemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create a new product
    public Product createProduct(Product product) {

        if(product.getPrice()>=0)
        {
            return productRepository.save(product);
        }
        else
        {
            throw new IllegalArgumentException("Price cannot be negative");
        }

    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Update a product
    public Product updateProduct(Long id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product productToUpdate = existingProduct.get();
            productToUpdate.setName(product.getName());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setQuantity(product.getQuantity());
            return productRepository.save(productToUpdate);
        }
        return null;
    }

    // Delete a product
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // test function
    public boolean testProduct()
    {
        return false;
    }

    public Product getProductByName(String name) {

        Product product = productRepository.
                findByName(name).orElseThrow(()-> new RuntimeException("Product not found with name: " + name));

        return product;

    }
}

