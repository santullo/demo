package com.example.demo.services;

import com.example.demo.dtos.UsuarioDTO;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.utils.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> listarTodos(String ativo) {

        if (ativo != null && !ativo.isEmpty()) {
            return ObjectMapperUtils.mapAll(usuarioRepository.findByStatus(ativo), UsuarioDTO.class);
        }

        return ObjectMapperUtils.mapAll(usuarioRepository.findAll(), UsuarioDTO.class);
    }
}
