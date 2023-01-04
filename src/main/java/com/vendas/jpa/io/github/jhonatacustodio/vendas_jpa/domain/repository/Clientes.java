package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Cliente;



@ComponentScan(basePackages = "com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository.Clientes")
@Configuration
public interface Clientes extends JpaRepository<Cliente, Integer> {

    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> pesquisarPorNome(@Param("nome") String nome);

    @Query("select c from Cliente c left join fetch c.pedido where c.id =:id")
    Cliente findClientFetchPedido(@Param("id") Integer id);

}
