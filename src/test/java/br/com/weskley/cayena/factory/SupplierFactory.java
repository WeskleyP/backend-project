package br.com.weskley.cayena.factory;

import br.com.weskley.cayena.model.Supplier;

import java.sql.Timestamp;
import java.time.Instant;

public class SupplierFactory {
    public static Supplier createSupplier() {
        return Supplier.builder()
                .id(1L)
                .name("Test Supplier 1")
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
    }
}
