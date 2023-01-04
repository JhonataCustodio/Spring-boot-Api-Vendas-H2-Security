package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.ItemPedido;

public interface ItensPedidos extends JpaRepository<ItemPedido, Integer> {
    
}
