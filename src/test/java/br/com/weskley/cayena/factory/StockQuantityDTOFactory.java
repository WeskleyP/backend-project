package br.com.weskley.cayena.factory;

import br.com.weskley.cayena.dto.StockQuantityDTO;
import br.com.weskley.cayena.enums.StockUpdateEnum;

public class StockQuantityDTOFactory {
    public static StockQuantityDTO createdStockQuantityDTODecrease() {
        return StockQuantityDTO.builder()
                .action(StockUpdateEnum.DECREASE)
                .quantity(5L)
                .build();
    }
    public static StockQuantityDTO createdStockQuantityDTOIncrease() {
        return StockQuantityDTO.builder()
                .action(StockUpdateEnum.INCREASE)
                .quantity(5L)
                .build();
    }
}
