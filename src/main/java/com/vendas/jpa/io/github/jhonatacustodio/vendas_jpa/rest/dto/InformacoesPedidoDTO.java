package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPedidoDTO {
    private Integer codigo;
    private String nomeCliente;
    private String cpf;
    private BigDecimal total;
    private String dataPedido;
    private String status;
    private List<InformacoesItemPedidoDTO> items;
}
