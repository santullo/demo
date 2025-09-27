package com.example.demo.services;

import com.example.demo.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UsuarioService usuarioService;

    @Autowired
    public AuthorizationService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            return usuarioService.consultarPorUsernameOrEmail(login);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
