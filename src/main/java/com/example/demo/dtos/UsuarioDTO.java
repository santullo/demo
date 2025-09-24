package com.example.demo.dtos;

import com.example.demo.enums.SimNaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String nomeCompleto;
    private String email;
    private String username;
    private LocalDateTime criadoEm;
    private String cpf;
    private SimNaoEnum ativo;
}
