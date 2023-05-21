package br.com.weskley.cayena.factory;

import br.com.weskley.cayena.dto.ProductDTO;
import br.com.weskley.cayena.model.Product;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
    public static Product createDefaultProduct() {
        return Product.builder()
                .id(1L)
                .name("Test Product 1")
                .stockQuantity(5L)
                .unitPrice(1.5)
                .supplier(SupplierFactory.createSupplier())
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
    }

    public static ProductDTO createProductDTO() {
        return ProductDTO.builder()
                .name("Test Product 1")
                .stockQuantity(5L)
                .unitPrice(1.5)
                .supplierId(1L)
                .build();
    }

    public static ProductDTO createInvalidProductDTO() {
        return ProductDTO.builder()
                .name("Test Product 1")
                .stockQuantity(-5L)
                .unitPrice(1.5)
                .supplierId(1L)
                .build();
    }

    public static List<Product> createDefaultListOfProduct() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .id(1L)
                .name("Test Product")
                .stockQuantity(5L)
                .unitPrice(1.5)
                .supplier(SupplierFactory.createSupplier())
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build());
        products.add(Product.builder()
                .id(2L)
                .name("Test Product 2")
                .stockQuantity(10L)
                .unitPrice(2.5)
                .supplier(SupplierFactory.createSupplier())
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build());
        return products;
    }
}
