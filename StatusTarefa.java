package com.gestao.model.enums;

/**
 * Enum que representa os possíveis estados de uma tarefa dentro de um projeto.
 */
public enum StatusTarefa {

    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDA("Concluída");

    private final String descricao;

    StatusTarefa(String descricao) {
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
