package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

}
