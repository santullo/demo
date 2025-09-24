package com.example.demo.repositories.queries;

public class UsuarioQuery {

    public static final String BUSCAR_TODOS_POR_STATUS = """
            SELECT * FROM MEU.TB_USUARIO USUARIO WHERE USUARIO.ATIVO = 1
            """;
}
