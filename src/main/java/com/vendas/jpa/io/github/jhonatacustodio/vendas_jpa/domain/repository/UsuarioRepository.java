package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
    Optional<Usuario> findByLogin(String login);

    
}
