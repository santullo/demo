package com.example.demo.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum SimNaoEnum {
    SIM("S", "Sim", true),
    NAO("N", "Não", false);

    private final String sigla;
    private final String valor;
    private final Boolean booleano;

    private static final List<String> VALORES_VERDADEIROS = List.of("S", "SIM", "TRUE");
    private static final List<String> VALORES_FALSOS = List.of("N", "NÃO", "NAO", "FALSE");

    /**
     * Retorna o valor do enum SimNaoEnum correspondente baseado no valor da string fornecida
     *
     * @param valor String a ser convertida para SimNaoEnum
     * @return SimNaoEnum.S se o valor for verdadeiro, SimNaoEnum.N caso contrário
     */
    public static SimNaoEnum getFromValor(String valor) {
        return isSim(valor) ? SIM : NAO;
    }

    /**
     * Verifica se o valor fornecido representa "Sim"
     *
     * @param valor String a ser verificada
     * @return true se o valor normalizado estiver contido na lista de valores verdadeiros, false caso contrário
     */
    public static boolean isSim(String valor) {
        if (valor == null || valor.isEmpty()) {
            return false;
        }

        String valorNormalizado = valor.trim().toUpperCase();

        return VALORES_VERDADEIROS.contains(valorNormalizado);
    }

    /**
     * Verifica se o valor fornecido representa "Não"
     *
     * @param valor String a ser verificada
     * @return true se o valor normalizado estiver contido na lista de valores falsos, false caso contrário
     */
    public static boolean isNao(String valor) {
        if (valor == null || valor.isEmpty()) {
            return false;
        }

        String valorNormalizado = valor.trim().toUpperCase();

        return VALORES_FALSOS.contains(valorNormalizado);
    }

    @Converter(autoApply = true)
    public static class Mapeador implements AttributeConverter<SimNaoEnum, String> {

        @Override
        public String convertToDatabaseColumn(SimNaoEnum valor) {
            return String.valueOf(valor.getSigla());
        }

        @Override
        public SimNaoEnum convertToEntityAttribute(String valor) {
            if (valor == null) return null;
            if ("S".equalsIgnoreCase(valor)) return SIM;
            if ("N".equalsIgnoreCase(valor)) return NAO;
            throw new IllegalStateException("Valor inválido: " + valor);
        }
    }
}
