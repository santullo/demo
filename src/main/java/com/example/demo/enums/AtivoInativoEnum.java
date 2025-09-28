package com.example.demo.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@SuppressWarnings("unused")
public enum AtivoInativoEnum {
    ATIVO("A", "Ativo", true),
    INATIVO("I", "Inativo", false);

    private final String sigla;
    private final String valor;
    private final Boolean booleano;

    private static final List<String> VALORES_ATIVO = List.of("A", "ATIVO", "TRUE");
    private static final List<String> VALORES_INATIVO = List.of("I", "INATIVO", "FALSE");

    /**
     * Retorna o valor do enum SimNaoEnum correspondente baseado no valor da string fornecida
     *
     * @param valor String a ser convertida para SimNaoEnum
     * @return SimNaoEnum.S se o valor for verdadeiro, SimNaoEnum.N caso contrário
     */
    public static AtivoInativoEnum getFromValor(String valor) {
        return isAtivo(valor) ? ATIVO : INATIVO;
    }

    /**
     * Verifica se o valor fornecido representa "Sim"
     *
     * @param valor String a ser verificada
     * @return true se o valor normalizado estiver contido na lista de valores verdadeiros, false caso contrário
     */
    public static boolean isAtivo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return false;
        }

        String valorNormalizado = valor.trim().toUpperCase();

        return VALORES_ATIVO.contains(valorNormalizado);
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

        return VALORES_INATIVO.contains(valorNormalizado);
    }

    /**
     * Conversor JPA para mapeamento entre o enum {@link AtivoInativoEnum}
     * e sua representação textual no banco de dados.
     * <p>
     * Esta classe implementa {@link AttributeConverter} para converter automaticamente
     * os valores do enum para caracteres single-char ('A'/'I') no banco de dados
     * e vice-versa durante as operações de persistência e recuperação de entidades.
     * </p>
     *
     * <p><b>Exemplo de uso:</b></p>
     * <pre>
     * {@code
     * @Entity
     * public class Usuario {
     *     @Convert(converter = AtivoInativoEnum.Mapeador.class)
     *     private AtivoInativoEnum status;
     * }
     * }
     * </pre>
     *
     * <p>
     * Com {@code autoApply = true}, o conversor será aplicado automaticamente
     * a todos os atributos do tipo {@link AtivoInativoEnum} em todas as entidades
     * do persistence context.
     * </p>
     *
     * @see AttributeConverter
     * @see Converter
     * @see AtivoInativoEnum
     *
     */
    @Converter(autoApply = true)
    public static class Mapeador implements AttributeConverter<AtivoInativoEnum, String> {

        @Override
        public String convertToDatabaseColumn(AtivoInativoEnum valor) {
            return String.valueOf(valor.getSigla());
        }

        @Override
        public AtivoInativoEnum convertToEntityAttribute(String valor) {
            if (valor == null) return null;
            if ("A".equalsIgnoreCase(valor)) return ATIVO;
            if ("I".equalsIgnoreCase(valor)) return INATIVO;
            throw new IllegalStateException("Valor inválido: " + valor);
        }
    }
}
