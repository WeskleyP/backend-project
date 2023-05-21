package br.com.weskley.cayena.service;

import br.com.weskley.cayena.dto.StockQuantityDTO;
import br.com.weskley.cayena.exception.NegativeStockException;
import br.com.weskley.cayena.exception.NoSuchElementFoundException;
import br.com.weskley.cayena.factory.ProductFactory;
import br.com.weskley.cayena.factory.StockQuantityDTOFactory;
import br.com.weskley.cayena.model.Product;
import br.com.weskley.cayena.repository.ProductRepository;
import br.com.weskley.cayena.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    ProductRepository productRepository;
    @Mock
    Validator validator;

    @Test
    void whenFindAllHasData_thenReturnListOfProducts() {
        when(productRepository.findAll()).thenReturn(ProductFactory.createDefaultListOfProduct());
        List<Product> actualProducts = productService.findAllProducts();
        assertFalse(actualProducts.isEmpty());
        assertEquals(2L, actualProducts.size());
        assertEquals(1L, actualProducts.get(0).getId());
    }

    @Test
    void whenFindAllHasNoData_thenReturnEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> actualProducts = productService.findAllProducts();
        assertTrue(actualProducts.isEmpty());
    }

    @Test
    void whenFindAllPaginatedHasData_thenReturnPageOfProducts() {
        List<Product> listOfProduct = ProductFactory.createDefaultListOfProduct();
        Pageable pageable = PageRequest.of(0, 5);
        PageImpl<Product> productsPaginated = new PageImpl<>(listOfProduct, pageable, listOfProduct.size());
        when(productRepository.findAll(pageable)).thenReturn(productsPaginated);
        Page<Product> actualProducts = productService.findAllProductsPaginated(pageable);
        assertEquals(2, actualProducts.getTotalElements());
        assertFalse(actualProducts.getContent().isEmpty());
        assertEquals(2L, actualProducts.getContent().size());
        assertEquals(1L, actualProducts.getContent().get(0).getId());
    }

    @Test
    void whenFindAllPaginatedHasNoData_thenReturnPageWithNoContent() {
        Pageable pageable = PageRequest.of(0, 5);
        PageImpl<Product> productsPaginated = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(productRepository.findAll(pageable)).thenReturn(productsPaginated);
        Page<Product> actualProducts = productService.findAllProductsPaginated(pageable);
        assertEquals(0, actualProducts.getTotalElements());
        assertTrue(actualProducts.getContent().isEmpty());
    }

    @Test
    void givenId_whenFindByIdHasData_thenReturnProduct() {
        when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(ProductFactory.createDefaultProduct()));
        Product actualProducts = productService.findProductById(1L);
        assertEquals(1L, actualProducts.getId());
        assertEquals("Test Product 1", actualProducts.getName());
    }

    @Test
    void givenId_whenFindByIdHasNoData_thenThrowNoSuchElementFoundException() {
        when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementFoundException.class,
                () -> productService.findProductById(1L),
                "Product not found");
    }

    @Test
    void givenProductDTO_whenProductIsValid_thenReturnSavedProduct() {
        Product product = ProductFactory.createDefaultProduct();
        when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
        Product savedProduct = productService.save(ProductFactory.createInvalidProductDTO());
        assertEquals(product, savedProduct);
        assertEquals(1L, savedProduct.getId());
    }

    @Test
    void givenProductDTO_whenProductIsInvalid_thenThrowException() {
        Set<ConstraintViolation<Product>> constraintViolations = new HashSet<>();
        constraintViolations.add(mock(ConstraintViolation.class));
        when(validator.validate(any(Product.class))).thenReturn(constraintViolations);

        assertThrows(ConstraintViolationException.class, () -> {
            productService.save(ProductFactory.createInvalidProductDTO());
        });
    }

    @Test
    void givenProductDTO_whenProductIsValid_thenReturnUpdatedProduct() {
        Product product = ProductFactory.createDefaultProduct();
        Product updatedProduct = ProductFactory.createDefaultProduct();
        updatedProduct.setName("Updated Name");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(updatedProduct);
        Product actualProduct = productService.update(1L, ProductFactory.createProductDTO());
        assertEquals(updatedProduct, actualProduct);
        assertEquals(1L, actualProduct.getId());
        assertEquals("Updated Name", actualProduct.getName());
    }

    @Test
    void givenProductDTO_whenProductNotExists_thenThrowExceptionWhenUpdating() {
        when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementFoundException.class,
                () -> productService.update(1L, ProductFactory.createProductDTO()),
                "Cannot update a product that not exists");
    }

    @Test
    void givenProductDTO_whenProductIsInvalid_thenThrowExceptionWhenUpdating() {
        Product product = ProductFactory.createDefaultProduct();
        Set<ConstraintViolation<Product>> constraintViolations = new HashSet<>();
        constraintViolations.add(mock(ConstraintViolation.class));
        when(validator.validate(any(Product.class))).thenReturn(constraintViolations);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(ConstraintViolationException.class, () -> {
            // Act
            productService.update(1L, ProductFactory.createInvalidProductDTO());
        });
    }

    @Test
    public void givenExistingId_whenDeleteById_thenDeleteProduct() {
        Long id = 1L;
        Product product = ProductFactory.createDefaultProduct();
        when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));
        productService.deleteById(id);

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    public void givenNonExistingId_whenDeleteById_thenThrowNoSuchElementFoundException() {
        when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            productService.deleteById(1L);
        }, "Cannot delete a product that not exists");

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void givenExistingIdAndValidStockQuantity_whenUpdateStockQuantity_thenUpdateProductStockQuantity() {
        StockQuantityDTO stockQuantityDTO = StockQuantityDTOFactory.createdStockQuantityDTODecrease();
        Product product = ProductFactory.createDefaultProduct();
        product.setStockQuantity(10L);
        when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));
        Product savedProduct = ProductFactory.createDefaultProduct();
        savedProduct.setStockQuantity(5L);
        when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(savedProduct);

        Product updatedProduct = productService.updateStockQuantity(1L, stockQuantityDTO);

        assertEquals(5L, updatedProduct.getStockQuantity());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void givenNonExistingId_whenUpdateStockQuantity_thenThrowNoSuchElementFoundException() {
        StockQuantityDTO stockQuantityDTO = StockQuantityDTOFactory.createdStockQuantityDTODecrease();
        when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            productService.updateStockQuantity(1L, stockQuantityDTO);
        });

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void givenValidStockQuantity_whenUpdateStockQuantity_thenUpdateProductStockQuantity() {
        StockQuantityDTO stockQuantityDTO = StockQuantityDTOFactory.createdStockQuantityDTOIncrease();
        Product product = ProductFactory.createDefaultProduct();
        when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));
        Product savedProduct = ProductFactory.createDefaultProduct();
        savedProduct.setStockQuantity(10L);
        when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(savedProduct);

        Product updatedProduct = productService.updateStockQuantity(1L, stockQuantityDTO);

        assertEquals(10L, updatedProduct.getStockQuantity());
    }

    @Test
    public void givenInvalidStockQuantity_whenUpdateStockQuantity_thenThrowNegativeStockException() {
        StockQuantityDTO stockQuantityDTO = StockQuantityDTOFactory.createdStockQuantityDTODecrease();
        stockQuantityDTO.setQuantity(15L);
        Product product = new Product();
        product.setStockQuantity(10L);
        when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));

        assertThrows(NegativeStockException.class, () -> {
            productService.updateStockQuantity(1L, stockQuantityDTO);
        }, "Stock can't be negative");

    }
}