package com.example.demo.entities;

import com.example.demo.enums.AtivoInativoEnum;
import com.example.demo.records.UsuarioRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable, UserDetails {
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
    @Column(name = "status", nullable = false, length = 1)
    @Convert(converter = AtivoInativoEnum.Mapeador.class)
    private AtivoInativoEnum status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_perfil_usuario",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private List<Perfil> perfis = new ArrayList<>();

    public Usuario(UsuarioRecord usuarioRecord) {
        this.nomeCompleto = usuarioRecord.nome();
        this.email = usuarioRecord.email();
        this.username = usuarioRecord.username();
        this.senha = new BCryptPasswordEncoder().encode(usuarioRecord.senha());
        this.cpf = usuarioRecord.cpf();
        this.criadoEm = LocalDateTime.now();
        this.status = AtivoInativoEnum.ATIVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis.stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getNome()))
                .toList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
