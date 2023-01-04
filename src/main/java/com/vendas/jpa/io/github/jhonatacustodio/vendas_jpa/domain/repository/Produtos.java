package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Produto;

@ComponentScan(basePackages = "com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository.Produtos")
@Configuration
public interface Produtos extends JpaRepository<Produto, Integer> {
    
}
