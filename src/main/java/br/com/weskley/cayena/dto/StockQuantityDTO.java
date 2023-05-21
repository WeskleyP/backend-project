package br.com.weskley.cayena.dto;

import br.com.weskley.cayena.enums.StockUpdateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockQuantityDTO {

    private StockUpdateEnum action;
    private Long quantity;
}
