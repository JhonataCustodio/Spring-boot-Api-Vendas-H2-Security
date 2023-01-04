package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Cliente;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Pedido;


@ComponentScan(basePackages = "com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository.Pedidos")
@Configuration
public interface Pedidos extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);

    @Query("select p from Pedido p left join fetch p.itens where p.id =:id")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
