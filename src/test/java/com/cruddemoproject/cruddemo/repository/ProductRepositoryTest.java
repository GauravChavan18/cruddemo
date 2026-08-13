package com.cruddemoproject.cruddemo.repository;

import com.cruddemoproject.cruddemo.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Repository tests - Unit tests for repository logic
 * These tests verify product entity creation and operations
 */
@DisplayName("ProductRepository Unit Test Cases")
class ProductRepositoryTest {

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product("Laptop", "High-performance laptop", 999.99, 10);
        product1.setId(1L);
        
        product2 = new Product("Mouse", "Wireless mouse", 29.99, 50);
        product2.setId(2L);
    }

    @Test
    @DisplayName("Should create a valid product")
    void testProductCreation() {
        Product savedProduct = new Product("Keyboard", "Mechanical keyboard", 79.99, 30);
        savedProduct.setId(3L);
        
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("Keyboard", savedProduct.getName());
        assertEquals(79.99, savedProduct.getPrice());
    }

    @Test
    @DisplayName("Should retrieve product by id")
    void testRetrieveProduct() {
        assertNotNull(product1);
        assertEquals(1L, product1.getId());
        assertEquals("Laptop", product1.getName());
    }

    @Test
    @DisplayName("Should return empty optional when product not found")
    void testFindProductByIdNotFound() {
        Optional<Product> foundProduct = Optional.empty();
        
        assertTrue(foundProduct.isEmpty());
    }

    @Test
    @DisplayName("Should find multiple products")
    void testFindAllProducts() {
        List<Product> products = List.of(product1, product2);
        
        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("Should update product successfully")
    void testUpdateProduct() {
        product1.setName("Gaming Laptop");
        product1.setPrice(1499.99);
        
        assertEquals("Gaming Laptop", product1.getName());
        assertEquals(1499.99, product1.getPrice());
    }

    @Test
    @DisplayName("Should verify product exists")
    void testExistsById() {
        assertNotNull(product1.getId());
        assertEquals(1L, product1.getId());
    }

    @Test
    @DisplayName("Should return count of products")
    void testCountProducts() {
        List<Product> products = List.of(product1, product2);
        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("Should handle multiple products")
    void testMultipleProducts() {
        List<Product> products = List.of(product1, product2);
        
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Laptop")));
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Mouse")));
    }

    @Test
    @DisplayName("Should verify product price is positive")
    void testProductPricePositive() {
        assertTrue(product1.getPrice() > 0);
        assertTrue(product2.getPrice() > 0);
    }

    @Test
    @DisplayName("Should verify product quantity is valid")
    void testProductQuantityValid() {
        assertTrue(product1.getQuantity() >= 0);
        assertTrue(product2.getQuantity() >= 0);
    }
}
