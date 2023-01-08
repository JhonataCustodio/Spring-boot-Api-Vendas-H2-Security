package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Usuario;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository.UsuarioRepository;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.exception.SenhaInvalidaException;

@Service
public class UsuarioServiceImple implements UserDetailsService{

    private static BCryptPasswordEncoder passwordEcorder = new BCryptPasswordEncoder();

    @Autowired
    private UsuarioRepository userRepository;


    @Transactional
    public Usuario salvar(Usuario usuario){
        return userRepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean verif_senhas = passwordEcorder.matches(usuario.getSenha(), user.getPassword());
        
        if(verif_senhas){
            return user;
        }
        throw new SenhaInvalidaException();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = (Usuario) userRepository.findByLogin(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};
        
        return User
            .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
            .build();
    }
    
}
