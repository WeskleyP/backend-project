package br.com.weskley.cayena.service.impl;

import br.com.weskley.cayena.dto.ProductDTO;
import br.com.weskley.cayena.dto.StockQuantityDTO;
import br.com.weskley.cayena.enums.StockUpdateEnum;
import br.com.weskley.cayena.exception.NegativeStockException;
import br.com.weskley.cayena.exception.NoSuchElementFoundException;
import br.com.weskley.cayena.model.Product;
import br.com.weskley.cayena.model.Supplier;
import br.com.weskley.cayena.repository.ProductRepository;
import br.com.weskley.cayena.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Validated
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Validator validator;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAllProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Product not found"));
    }

    @Override
    public Product save(ProductDTO productDTO) {
        Product product = ProductDTO.convertDTOtoEntity(productDTO);
        validateProduct(product);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Cannot update a product that not exists"));
        product.setName(productDTO.getName());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setSupplier(Supplier.builder().id(productDTO.getSupplierId()).build());
        product.setUnitPrice(productDTO.getUnitPrice());
        validateProduct(product);
        return productRepository.save(product);
    }

    private void validateProduct(Product product) {
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @Override
    public void deleteById(Long id) {
        productRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Cannot delete a product that not exists"));
        productRepository.deleteById(id);
    }

    @Override
    public Product updateStockQuantity(Long id, StockQuantityDTO stockQuantityDTO) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException("Cannot update stock quantity of a product that doesn't exists")
        );
        validateStockOfProduct(product, stockQuantityDTO);
        return productRepository.save(product);
    }

    private void validateStockOfProduct(Product product, StockQuantityDTO stockQuantityDTO) {
        Long actualStock = product.getStockQuantity();
        Long newStock = null;
        if (stockQuantityDTO.getAction().equals(StockUpdateEnum.DECREASE)) {
            newStock = actualStock - stockQuantityDTO.getQuantity();
        } else if (stockQuantityDTO.getAction().equals(StockUpdateEnum.INCREASE)) {
            newStock = actualStock + stockQuantityDTO.getQuantity();
        }
        if (newStock == null || newStock < 0) {
            throw new NegativeStockException("Stock can't be negative");
        }
        product.setStockQuantity(newStock);
    }
}
