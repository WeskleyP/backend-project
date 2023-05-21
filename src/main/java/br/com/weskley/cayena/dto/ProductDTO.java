package br.com.weskley.cayena.dto;

import br.com.weskley.cayena.model.Product;
import br.com.weskley.cayena.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private Long stockQuantity;
    private Double unitPrice;
    private Long supplierId;

    public static Product convertDTOtoEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .stockQuantity(productDTO.getStockQuantity())
                .unitPrice(productDTO.getUnitPrice())
                .supplier(Supplier.builder().id(productDTO.getSupplierId()).build())
                .build();
    }

    private static ProductDTO convertEntityToDTO(Product product) {
        ProductDTOBuilder builder = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .stockQuantity(product.getStockQuantity())
                .unitPrice(product.getUnitPrice());
        if (product.getSupplier() != null) {
            builder.supplierId(product.getSupplier().getId());
        }
        return builder.build();
    }
}
