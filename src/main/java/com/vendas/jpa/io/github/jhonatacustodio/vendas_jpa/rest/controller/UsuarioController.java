package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Usuario;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.security.jwt.JwtService;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.service.implementation.UsuarioServiceImple;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.exception.SenhaInvalidaException;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto.CredenciaisDTO;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto.TokenDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioServiceImple usuarioServiceImple;
    
    private final PasswordEncoder encoder;

    private final JwtService jwtService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioServiceImple.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                            .login(credenciais.getLogin())
                            .senha(credenciais.getSenha())
                            .build(); 
            //UserDetails usuarioAutenticado =  usuarioServiceImple.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        }catch(UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
