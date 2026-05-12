package com.gestao.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um usuário com perfil de Gerente.
 *
 * O Gerente é responsável por conduzir projetos, coordenar equipes
 * e acompanhar prazos de entrega. Cada projeto deve ter exatamente
 * um gerente responsável.
 *
 * Demonstra Herança e o atributo extra 'projetosResponsavel'.
 */
public class Gerente extends Usuario {

    /** Lista dos projetos sob responsabilidade deste gerente */
    private List<Projeto> projetosResponsavel;

    public Gerente(String nome, String cpf, String email, String login, String senha) {
        super(nome, cpf, email, login, senha);
        this.projetosResponsavel = new ArrayList<>();
    }

    /**
     * Implementação do método abstrato — exibe perfil com quantidade de projetos.
     */
    @Override
    public String exibirPerfil() {
        return "Perfil: Gerente | Projetos sob responsabilidade: " + projetosResponsavel.size();
    }

    // =========================================================
    // Gerenciamento de projetos
    // =========================================================

    public void adicionarProjeto(Projeto projeto) {
        if (!projetosResponsavel.contains(projeto)) {
            projetosResponsavel.add(projeto);
        }
    }

    public void removerProjeto(Projeto projeto) {
        projetosResponsavel.remove(projeto);
    }

    public List<Projeto> getProjetosResponsavel() {
        return projetosResponsavel;
    }
}
