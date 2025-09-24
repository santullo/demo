package com.example.demo.entities;

import com.example.demo.enums.SimNaoEnum;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @ColumnDefault("'S'")
    @Column(name = "ativo", nullable = false, length = 1)
    @Convert(converter = SimNaoEnum.Mapeador.class)
    private SimNaoEnum ativo;

}
