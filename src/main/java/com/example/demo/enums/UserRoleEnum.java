package com.example.demo.enums;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public enum UserRoleEnum {
    ADMIN("ADMIN", "A"),
    USER("USER", "U");

    private final String nome;
    private final String sigla;

    UserRoleEnum(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }
}
