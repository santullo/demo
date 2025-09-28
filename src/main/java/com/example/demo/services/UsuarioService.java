package com.example.demo.services;

import com.example.demo.dtos.UsuarioDTO;
import com.example.demo.entities.Usuario;
import com.example.demo.enums.AtivoInativoEnum;
import com.example.demo.records.UsuarioLoginRecord;
import com.example.demo.records.UsuarioRecord;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.utils.ObjectMapperUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings({"unchecked", "unused"})
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> listarTodos(String ativo) {

        if (ativo != null && !ativo.isEmpty()) {
            return converterListaParaDTO(usuarioRepository.findByStatus(ativo));
        }

        return converterListaParaDTO(usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeCompleto")));
    }

    public UsuarioDTO consultarUsuarioDTOPorID(UUID id) throws Exception {
        return consultarPorId(id, true);
    }

    public Usuario consultarUsuarioPorID(UUID id) throws Exception {
        return consultarPorId(id, false);
    }

    public <T> T consultarPorId(UUID id, boolean isDTO) throws Exception {
        return usuarioRepository.findById(id).map(usuario -> {
            if (isDTO) {
                return (T) converterParaDTO(usuario);
            } else {
                return (T) usuario;
            }
        }).orElseThrow(() -> new Exception("Não foi possível encontrar o usuário com o ID: " + id));
    }

    public UserDetails consultarPorUsernameOrEmail(String login) {
        return usuarioRepository.findByUsernameOrEmail(login).orElse(null);
    }

    @Transactional(rollbackOn = Exception.class)
    public UsuarioDTO salvar(UsuarioRecord usuarioRecord) {
        Usuario usuarioCriado = usuarioRepository.save(new Usuario(usuarioRecord));
        return converterParaDTO(usuarioCriado);
    }

    @Transactional(rollbackOn = Exception.class)
    public UsuarioDTO alterar(UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = consultarUsuarioPorID(usuarioDTO.getId());
        usuario.setNomeCompleto(usuarioDTO.getNomeCompleto());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setStatus(usuarioDTO.getStatus());

        return converterParaDTO(usuarioRepository.save(usuario));
    }

    @Transactional(rollbackOn = Exception.class)
    public void alterarStatusUsuario(UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = consultarUsuarioPorID(usuarioDTO.getId());
        if (AtivoInativoEnum.ATIVO.equals(usuarioDTO.getStatus())) {
            usuario.setStatus(AtivoInativoEnum.INATIVO);
        } else {
            usuario.setStatus(AtivoInativoEnum.ATIVO);
        }
        usuarioRepository.save(usuario);
    }

    @Transactional(rollbackOn = Exception.class)
    public void alterarSenha(UsuarioLoginRecord usuarioLoginRecord) {
        Usuario usuario = (Usuario) consultarPorUsernameOrEmail(usuarioLoginRecord.login());
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioLoginRecord.senha()));
        usuarioRepository.save(usuario);
    }

    private List<UsuarioDTO> converterListaParaDTO(List<Usuario> usuarios) {
        return ObjectMapperUtils.mapAll(usuarios, UsuarioDTO.class);
    }

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        return ObjectMapperUtils.map(usuario, UsuarioDTO.class);
    }
}
