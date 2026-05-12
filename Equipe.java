package com.gestao.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma equipe de trabalho.
 *
 * Uma equipe é composta por um ou mais Colaboradores e pode
 * ser alocada em vários projetos simultaneamente.
 * Os projetos são referenciados por ID para evitar dependência circular.
 */
public class Equipe {

    private static int contadorId = 1;

    private int id;
    private String nome;
    private String descricao;
    private List<Colaborador> membros;
    private List<Integer> projetosIds; // IDs dos projetos em que a equipe atua

    // =========================================================
    // Construtor
    // =========================================================

    public Equipe(String nome, String descricao) {
        this.id          = contadorId++;
        this.nome        = nome;
        this.descricao   = descricao;
        this.membros     = new ArrayList<>();
        this.projetosIds = new ArrayList<>();
    }

    // =========================================================
    // Getters e Setters
    // =========================================================

    public int getId()                      { return id; }

    public String getNome()                 { return nome; }
    public void setNome(String n)           { this.nome = n; }

    public String getDescricao()            { return descricao; }
    public void setDescricao(String d)      { this.descricao = d; }

    public List<Colaborador> getMembros()   { return membros; }
    public List<Integer> getProjetosIds()   { return projetosIds; }

    // =========================================================
    // Métodos auxiliares
    // =========================================================

    public void adicionarMembro(Colaborador c) {
        if (!membros.contains(c)) membros.add(c);
    }

    public void removerMembro(Colaborador c) {
        membros.remove(c);
    }

    public void adicionarProjeto(int projetoId) {
        if (!projetosIds.contains(projetoId)) projetosIds.add(projetoId);
    }

    // =========================================================
    // toString
    // =========================================================

    @Override
    public String toString() {
        return String.format("[%d] %-25s | Membros: %d | Projetos: %d | Descrição: %s",
                id, nome, membros.size(), projetosIds.size(), descricao);
    }
}
