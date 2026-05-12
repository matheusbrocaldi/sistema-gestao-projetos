package com.gestao.model.enums;

/**
 * Enum que representa os cargos disponíveis para usuários com perfil Colaborador.
 * Reflete os papéis profissionais presentes nas equipes de projeto.
 */
public enum Cargo {

    DESENVOLVEDOR("Desenvolvedor"),
    ANALISTA_SISTEMAS("Analista de Sistemas"),
    DESIGNER_INTERFACE("Designer de Interface");

    private final String descricao;

    Cargo(String descricao) {
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
