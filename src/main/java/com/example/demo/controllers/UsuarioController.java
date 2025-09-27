package com.example.demo.controllers;

import com.example.demo.dtos.UsuarioDTO;
import com.example.demo.records.UsuarioRecord;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController("UsuarioController")
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos(@RequestParam(required = false) String ativo) {
        return ResponseEntity.ok(usuarioService.listarTodos(ativo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> consultarPorId(@PathVariable("id") UUID id) throws Exception {
        return ResponseEntity.ok(usuarioService.consultarUsuarioDTOPorID(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvar(@RequestBody UsuarioRecord usuarioRecord) {
        UsuarioDTO usuarioCriado = usuarioService.salvar(usuarioRecord);
        URI locacation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioCriado.getId())
                .toUri();
        return ResponseEntity.created(locacation).body(usuarioCriado);
    }
}
