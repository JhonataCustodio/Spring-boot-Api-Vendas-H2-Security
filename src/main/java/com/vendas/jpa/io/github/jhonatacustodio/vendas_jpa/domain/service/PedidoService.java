package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.service;

import java.util.Optional;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Pedido;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.enums.StatusPedido;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedidoDTO);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
