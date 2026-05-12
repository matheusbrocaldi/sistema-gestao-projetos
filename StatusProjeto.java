package com.gestao.model.enums;

/**
 * Enum que representa os possíveis estados de um projeto.
 * Um projeto transita de PLANEJADO → EM_ANDAMENTO → CONCLUIDO (ou CANCELADO).
 */
public enum StatusProjeto {

    PLANEJADO("Planejado"),
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusProjeto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
