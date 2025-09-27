package com.example.demo.repositories;

import com.example.demo.entities.Usuario;
import com.example.demo.repositories.queries.UsuarioQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Query(nativeQuery = true, value = UsuarioQuery.BUSCAR_TODOS_POR_STATUS)
    List<Usuario> findByStatus(String status);

    @Query(nativeQuery = true, value = UsuarioQuery.BUSCAR_POR_USERNAME)
    Optional<Usuario> findByUsernameOrEmail(String login);
}
