package com.gestao.model;

import com.gestao.model.enums.Cargo;

/**
 * Representa um usuário com perfil de Colaborador.
 *
 * O Colaborador é o profissional executor das tarefas dentro dos projetos.
 * Pode ser Desenvolvedor, Analista de Sistemas ou Designer de Interface.
 * Seu acesso é restrito às tarefas atribuídas a ele.
 *
 * Demonstra Herança com atributo especializado (cargo).
 */
public class Colaborador extends Usuario {

    /** Cargo técnico do colaborador dentro da equipe */
    private Cargo cargo;

    public Colaborador(String nome, String cpf, String email,
                       String login, String senha, Cargo cargo) {
        super(nome, cpf, email, login, senha);
        this.cargo = cargo;
    }

    /**
     * Implementação do método abstrato — exibe perfil com cargo técnico.
     */
    @Override
    public String exibirPerfil() {
        return "Perfil: Colaborador | Cargo: " + cargo.getDescricao();
    }

    // =========================================================
    // Getters e Setters
    // =========================================================

    public Cargo getCargo()         { return cargo; }
    public void setCargo(Cargo c)   { this.cargo = c; }
}
