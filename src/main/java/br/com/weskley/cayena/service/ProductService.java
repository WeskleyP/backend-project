package br.com.weskley.cayena.service;

import br.com.weskley.cayena.dto.ProductDTO;
import br.com.weskley.cayena.dto.StockQuantityDTO;
import br.com.weskley.cayena.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();
    Page<Product> findAllProductsPaginated(Pageable pageable);

    Product findProductById(Long id);
    Product save(ProductDTO productDTO);
    Product update(Long id, ProductDTO productDTO);
    void deleteById(Long id);

    Product updateStockQuantity(Long id, StockQuantityDTO stockQuantityDTO);
}
