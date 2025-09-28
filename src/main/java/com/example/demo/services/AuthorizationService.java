package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService{

    private final UsuarioService usuarioService;

    @Autowired
    public AuthorizationService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioService.consultarPorUsernameOrEmail(login);
    }
}
