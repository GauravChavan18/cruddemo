package com.cruddemoproject.cruddemo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Entity Test Cases")
class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Laptop", "High-performance laptop", 999.99, 10);
    }

    @Test
    @DisplayName("Should create product with all fields")
    void testProductConstructor() {
        assertNotNull(product);
        assertEquals("Laptop", product.getName());
        assertEquals("High-performance laptop", product.getDescription());
        assertEquals(999.99, product.getPrice());
        assertEquals(10, product.getQuantity());
    }

    @Test
    @DisplayName("Should create empty product")
    void testEmptyProductConstructor() {
        Product emptyProduct = new Product();
        assertNotNull(emptyProduct);
        assertNull(emptyProduct.getId());
        assertNull(emptyProduct.getName());
    }

    @Test
    @DisplayName("Should set and get product id")
    void testSetAndGetId() {
        product.setId(1L);
        assertEquals(1L, product.getId());
    }

    @Test
    @DisplayName("Should set and get product name")
    void testSetAndGetName() {
        product.setName("Gaming Laptop");
        assertEquals("Gaming Laptop", product.getName());
    }

    @Test
    @DisplayName("Should set and get product description")
    void testSetAndGetDescription() {
        product.setDescription("RTX 4090 Gaming laptop");
        assertEquals("RTX 4090 Gaming laptop", product.getDescription());
    }

    @Test
    @DisplayName("Should set and get product price")
    void testSetAndGetPrice() {
        product.setPrice(1499.99);
        assertEquals(1499.99, product.getPrice());
    }

    @Test
    @DisplayName("Should set and get product quantity")
    void testSetAndGetQuantity() {
        product.setQuantity(5);
        assertEquals(5, product.getQuantity());
    }

    @Test
    @DisplayName("Should handle zero price")
    void testZeroPrice() {
        product.setPrice(0.0);
        assertEquals(0.0, product.getPrice());
    }

    @Test
    @DisplayName("Should handle zero quantity")
    void testZeroQuantity() {
        product.setQuantity(0);
        assertEquals(0, product.getQuantity());
    }

    @Test
    @DisplayName("Should handle negative quantity")
    void testNegativeQuantity() {
        product.setQuantity(-5);
        assertEquals(-5, product.getQuantity());
    }

    @Test
    @DisplayName("Should handle large price")
    void testLargePrice() {
        product.setPrice(999999.99);
        assertEquals(999999.99, product.getPrice());
    }

    @Test
    @DisplayName("Should handle large quantity")
    void testLargeQuantity() {
        product.setQuantity(1000000);
        assertEquals(1000000, product.getQuantity());
    }
}

