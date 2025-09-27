package com.example.demo.entities;

import com.example.demo.enums.SimNaoEnum;
import com.example.demo.records.UsuarioRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_completo")
    @NotBlank(message = "Nome é obrigatório")
    private String nomeCompleto;

    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    @NotBlank(message = "Usuário é obrigatório")
    private String username;

    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @NotBlank(message = "Senha é obrigatória")
    @Column(name = "senha", nullable = false)
    private String senha;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @ColumnDefault("'S'")
    @Column(name = "ativo", nullable = false, length = 1)
    @Convert(converter = SimNaoEnum.Mapeador.class)
    private SimNaoEnum ativo;

    public Usuario(UsuarioRecord usuarioRecord) {
        this.nomeCompleto = usuarioRecord.nome();
        this.email = usuarioRecord.email();
        this.username = usuarioRecord.username();
        this.senha = new BCryptPasswordEncoder().encode(usuarioRecord.senha());
        this.cpf = usuarioRecord.cpf();
        this.criadoEm = LocalDateTime.now();
        this.ativo = SimNaoEnum.SIM;
    }
}
