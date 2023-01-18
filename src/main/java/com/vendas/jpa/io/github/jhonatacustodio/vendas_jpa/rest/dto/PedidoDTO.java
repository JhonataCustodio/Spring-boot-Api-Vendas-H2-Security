package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.validation.NotEmptyList;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    
    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer cliente;
    @NotNull(message = "{campo.total-pedido.ibrigatorio}")
    private BigDecimal total;
    @NotEmptyList(message = "campo.items-pedido.obrigatorio")
    private List<ItemPedidoDTO> items;
}
