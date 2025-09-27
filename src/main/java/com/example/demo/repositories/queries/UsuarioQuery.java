package com.example.demo.repositories.queries;

public class UsuarioQuery {

    public static final String BUSCAR_TODOS_POR_STATUS = """
            SELECT * FROM MEU.TB_USUARIO USUARIO WHERE USUARIO.ATIVO = :status ORDER BY USUARIO.NOME_COMPLETO
            """;

    public static final String BUSCAR_POR_USERNAME = """
            SELECT * FROM MEU.TB_USUARIO USUARIO WHERE USUARIO.USERNAME = :login OR USUARIO.EMAIL = :login
            """;
}
