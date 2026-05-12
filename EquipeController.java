package com.gestao.controller;

import com.gestao.model.*;
import com.gestao.repository.Repositorio;

import java.util.List;

/**
 * Controller responsável por todas as operações relacionadas a Equipes.
 */
public class EquipeController {

    private final Repositorio repositorio;

    public EquipeController() {
        this.repositorio = Repositorio.getInstancia();
    }

    // =========================================================
    // Cadastro
    // =========================================================

    public Equipe cadastrar(String nome, String descricao) {
        Equipe e = new Equipe(nome, descricao);
        repositorio.getEquipes().add(e);
        return e;
    }

    // =========================================================
    // Atualização
    // =========================================================

    public void atualizar(int id, String nome, String descricao) {
        Equipe e = buscarPorId(id);
        if (e == null) throw new IllegalArgumentException("Equipe não encontrada.");
        if (nome      != null && !nome.isBlank())      e.setNome(nome);
        if (descricao != null && !descricao.isBlank()) e.setDescricao(descricao);
    }

    // =========================================================
    // Membros
    // =========================================================

    /**
     * Adiciona um colaborador como membro de uma equipe.
     */
    public void adicionarMembro(int equipeId, Colaborador colaborador) {
        Equipe e = buscarPorId(equipeId);
        if (e == null) throw new IllegalArgumentException("Equipe não encontrada.");
        e.adicionarMembro(colaborador);
    }

    /**
     * Remove um colaborador de uma equipe.
     */
    public void removerMembro(int equipeId, Colaborador colaborador) {
        Equipe e = buscarPorId(equipeId);
        if (e == null) throw new IllegalArgumentException("Equipe não encontrada.");
        e.removerMembro(colaborador);
    }

    // =========================================================
    // Remoção
    // =========================================================

    public boolean remover(int id) {
        return repositorio.getEquipes().removeIf(e -> e.getId() == id);
    }

    // =========================================================
    // Consultas
    // =========================================================

    public List<Equipe> listarTodas() {
        return repositorio.getEquipes();
    }

    public Equipe buscarPorId(int id) {
        for (Equipe e : repositorio.getEquipes()) {
            if (e.getId() == id) return e;
        }
        return null;
    }
}
