package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Usuario;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.service.implementation.UsuarioServiceImple;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioServiceImple usuarioServiceImple;
    
    private final PasswordEncoder encoder;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioServiceImple.salvar(usuario);
    }

}
