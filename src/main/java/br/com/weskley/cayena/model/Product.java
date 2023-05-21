package br.com.weskley.cayena.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull(message = "Product name can't be null")
    @Column(name = "name")
    private String name;
    @Min(value = 0)
    @NotNull(message = "Product stock can't be null")
    @Column(name = "stock_quantity")
    private Long stockQuantity;
    @NotNull(message = "Product unit price can't be null")
    @DecimalMin(value = "0.0")
    @Column(name = "unit_price")
    private Double unitPrice;
    @NotNull(message = "Product supplier can't be null")
    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;
    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
