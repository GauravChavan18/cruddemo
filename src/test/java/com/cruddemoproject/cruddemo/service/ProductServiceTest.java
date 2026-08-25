package com.cruddemoproject.cruddemo.service;

import com.cruddemoproject.cruddemo.entity.Product;
import com.cruddemoproject.cruddemo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ProductService Test Cases")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

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
    @DisplayName("Should create a new product")
    void testCreateProduct() {
        Product newProduct = new Product("Keyboard", "Mechanical keyboard", 79.99, 30);
        
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);
        
        Product result = productService.createProduct(newProduct);
        
        assertNotNull(result);
        assertEquals("Keyboard", result.getName());
        assertEquals(79.99, result.getPrice());
        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    @DisplayName("Should get all products")
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(product1, product2);
        
        when(productRepository.findAll()).thenReturn(products);
        
        List<Product> result = productService.getAllProducts();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Mouse", result.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get product by id")
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        
        Optional<Product> result = productService.getProductById(1L);
        
        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());
        assertEquals(999.99, result.get().getPrice());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty optional when product not found")
    void testGetProductByIdNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        
        Optional<Product> result = productService.getProductById(999L);
        
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should update existing product")
    void testUpdateProduct() {
        Product updatedProduct = new Product("Gaming Laptop", "RTX 4090 Gaming laptop", 1499.99, 5);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        
        Product result = productService.updateProduct(1L, updatedProduct);
        
        assertNotNull(result);
        assertEquals("Gaming Laptop", result.getName());
        assertEquals(1499.99, result.getPrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should return null when updating non-existent product")
    void testUpdateProductNotFound() {
        Product updatedProduct = new Product("Gaming Laptop", "RTX 4090 Gaming laptop", 1499.99, 5);
        
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        
        Product result = productService.updateProduct(999L, updatedProduct);
        
        assertNull(result);
        verify(productRepository, times(1)).findById(999L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Should delete existing product")
    void testDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        
        boolean result = productService.deleteProduct(1L);
        
        assertTrue(result);
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should return false when deleting non-existent product")
    void testDeleteProductNotFound() {
        when(productRepository.existsById(999L)).thenReturn(false);
        
        boolean result = productService.deleteProduct(999L);
        
        assertFalse(result);
        verify(productRepository, times(1)).existsById(999L);
        verify(productRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should get empty list when no products exist")
    void testGetAllProductsEmpty() {
        when(productRepository.findAll()).thenReturn(Arrays.asList());
        
        List<Product> result = productService.getAllProducts();
        
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get product by name")
    void testGetProductByName() {
        when(productRepository.findByName("Laptop")).thenReturn(Optional.of(product1));
        
        Product result = productService.getProductByName("Laptop");
        
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(999.99, result.getPrice());
        verify(productRepository, times(1)).findByName("Laptop");
    }

    @Test
    @DisplayName("Should throw exception when product not found by name")
    void testGetProductByNameNotFound() {
        when(productRepository.findByName("NonExistent")).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            productService.getProductByName("NonExistent");
        });
        verify(productRepository, times(1)).findByName("NonExistent");
    }

    @Test
    @DisplayName("Should throw exception with correct message when product not found by name")
    void testGetProductByNameNotFoundWithMessage() {
        when(productRepository.findByName("NonExistent")).thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductByName("NonExistent");
        });
        
        assertTrue(exception.getMessage().contains("Product not found with name: NonExistent"));
        verify(productRepository, times(1)).findByName("NonExistent");
    }
}
