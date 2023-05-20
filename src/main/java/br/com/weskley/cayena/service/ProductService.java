package br.com.weskley.cayena.service;

import br.com.weskley.cayena.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProducts();
    List<Product> findAllProducts(Pageable pageable);

    Product findProductById(Long id);
    Product save();
}
