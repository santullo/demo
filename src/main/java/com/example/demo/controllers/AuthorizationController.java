package com.example.demo.controllers;

import com.example.demo.entities.Usuario;
import com.example.demo.records.LoginResponseRecord;
import com.example.demo.records.UsuarioLoginRecord;
import com.example.demo.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AuthorizationController")
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthorizationController(AuthenticationManager authenticationManager,
                                   TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseRecord> login(@RequestBody @Valid UsuarioLoginRecord usuarioLoginRecord) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(usuarioLoginRecord.login(), usuarioLoginRecord.senha());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseRecord(token));
    }
}
