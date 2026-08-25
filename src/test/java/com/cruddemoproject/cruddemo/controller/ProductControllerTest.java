package com.cruddemoproject.cruddemo.controller;

import com.cruddemoproject.cruddemo.entity.Product;
import com.cruddemoproject.cruddemo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ProductController Test Cases")
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        product1 = new Product("Laptop", "High-performance laptop", 999.99, 10);
        product1.setId(1L);
        
        product2 = new Product("Mouse", "Wireless mouse", 29.99, 50);
        product2.setId(2L);
    }

    @Test
    @DisplayName("Should create product and return CREATED status")
    void testCreateProduct() {
        Product newProduct = new Product("Keyboard", "Mechanical keyboard", 79.99, 30);
        
        when(productService.createProduct(any(Product.class))).thenReturn(newProduct);
        
        ResponseEntity<Product> response = productController.createProduct(newProduct);
        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Keyboard", response.getBody().getName());
        assertEquals(79.99, response.getBody().getPrice());
        verify(productService, times(1)).createProduct(newProduct);
    }

    @Test
    @DisplayName("Should get all products and return OK status")
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(product1, product2);
        
        when(productService.getAllProducts()).thenReturn(products);
        
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Laptop", response.getBody().get(0).getName());
        assertEquals("Mouse", response.getBody().get(1).getName());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Should get product by id and return OK status")
    void testGetProductByIdSuccess() {
        when(productService.getProductById(1L)).thenReturn(Optional.of(product1));
        
        ResponseEntity<Product> response = productController.getProductById(1L);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getName());
        assertEquals(999.99, response.getBody().getPrice());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    @DisplayName("Should return NOT_FOUND when product id doesn't exist")
    void testGetProductByIdNotFound() {
        when(productService.getProductById(999L)).thenReturn(Optional.empty());
        
        ResponseEntity<Product> response = productController.getProductById(999L);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(productService, times(1)).getProductById(999L);
    }

    @Test
    @DisplayName("Should get product by name and return OK status")
    void testGetProductByNameSuccess() {
        when(productService.getProductByName("Laptop")).thenReturn(product1);
        
        ResponseEntity<Product> response = productController.getProductById("Laptop");
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getName());
        assertEquals(999.99, response.getBody().getPrice());
        verify(productService, times(1)).getProductByName("Laptop");
    }

    @Test
    @DisplayName("Should return NOT_FOUND when product by name doesn't exist")
    void testGetProductByNameNotFound() {
        when(productService.getProductByName("NonExistent")).thenReturn(null);
        
        ResponseEntity<Product> response = productController.getProductById("NonExistent");
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(productService, times(1)).getProductByName("NonExistent");
    }

    @Test
    @DisplayName("Should update product and return OK status")
    void testUpdateProductSuccess() {
        Product updatedProduct = new Product("Gaming Laptop", "RTX 4090 Gaming laptop", 1499.99, 5);
        updatedProduct.setId(1L);
        
        when(productService.updateProduct(1L, updatedProduct)).thenReturn(updatedProduct);
        
        ResponseEntity<Product> response = productController.updateProduct(1L, updatedProduct);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Gaming Laptop", response.getBody().getName());
        assertEquals(1499.99, response.getBody().getPrice());
        verify(productService, times(1)).updateProduct(1L, updatedProduct);
    }

    @Test
    @DisplayName("Should return NOT_FOUND when updating non-existent product")
    void testUpdateProductNotFound() {
        Product updatedProduct = new Product("Gaming Laptop", "RTX 4090 Gaming laptop", 1499.99, 5);
        
        when(productService.updateProduct(999L, updatedProduct)).thenReturn(null);
        
        ResponseEntity<Product> response = productController.updateProduct(999L, updatedProduct);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(productService, times(1)).updateProduct(999L, updatedProduct);
    }

    @Test
    @DisplayName("Should delete product and return NO_CONTENT status")
    void testDeleteProductSuccess() {
        when(productService.deleteProduct(1L)).thenReturn(true);
        
        ResponseEntity<Void> response = productController.deleteProduct(1L);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    @DisplayName("Should return NOT_FOUND when deleting non-existent product")
    void testDeleteProductNotFound() {
        when(productService.deleteProduct(999L)).thenReturn(false);
        
        ResponseEntity<Void> response = productController.deleteProduct(999L);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(999L);
    }

    @Test
    @DisplayName("Should test product endpoint")
    void testTestProduct() {
        when(productService.testProduct()).thenReturn(true);
        
        ResponseEntity<String> response = productController.testProduct();
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test passed", response.getBody());
        verify(productService, times(1)).testProduct();
    }

    @Test
    @DisplayName("Should return error when test product fails")
    void testTestProductFails() {
        when(productService.testProduct()).thenReturn(false);
        
        ResponseEntity<String> response = productController.testProduct();
        
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Test failed", response.getBody());
        verify(productService, times(1)).testProduct();
    }

    @Test
    @DisplayName("Should get empty list when no products exist")
    void testGetAllProductsEmpty() {
        when(productService.getAllProducts()).thenReturn(Arrays.asList());
        
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(productService, times(1)).getAllProducts();
    }
}

