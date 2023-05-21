package br.com.weskley.cayena.resource;

import br.com.weskley.cayena.dto.ProductDTO;
import br.com.weskley.cayena.dto.StockQuantityDTO;
import br.com.weskley.cayena.model.Product;
import br.com.weskley.cayena.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "product")
@RestController
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @GetMapping(path = "findAll")
    public ResponseEntity<List<Product>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping(path = "findAllPaginated")
    public ResponseEntity<Page<Product>> findAllPaginated(Pageable pageable) {
        return ResponseEntity.ok(productService.findAllProductsPaginated(pageable));
    }

    @GetMapping(path = "findById/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PostMapping(path = "save")
    public ResponseEntity<Product> save(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.save(productDTO));
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.update(id, productDTO));
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "updateStock/{id}")
    public ResponseEntity<Product> updateStock(@PathVariable("id") Long id, @RequestBody StockQuantityDTO stockQuantityDTO) {
        return ResponseEntity.ok(productService.updateStockQuantity(id, stockQuantityDTO));
    }
}
