package com.gestao.controller;

import com.gestao.model.*;
import com.gestao.model.enums.StatusProjeto;
import com.gestao.repository.Repositorio;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller responsável por todas as operações relacionadas a Projetos.
 */
public class ProjetoController {

    private final Repositorio repositorio;

    public ProjetoController() {
        this.repositorio = Repositorio.getInstancia();
    }

    // =========================================================
    // Cadastro
    // =========================================================

    /**
     * Cadastra um novo projeto e o vincula ao gerente responsável.
     */
    public Projeto cadastrar(String nome, String descricao,
                             LocalDate dataInicio, LocalDate dataFim,
                             Gerente gerente) {
        Projeto p = new Projeto(nome, descricao, dataInicio, dataFim, gerente);
        gerente.adicionarProjeto(p);
        repositorio.getProjetos().add(p);
        return p;
    }

    // =========================================================
    // Atualização
    // =========================================================

    /**
     * Atualiza os dados editáveis de um projeto.
     */
    public void atualizar(int id, String nome, String descricao, LocalDate dataFim) {
        Projeto p = buscarPorId(id);
        if (p == null) throw new IllegalArgumentException("Projeto não encontrado.");
        if (nome      != null && !nome.isBlank())      p.setNome(nome);
        if (descricao != null && !descricao.isBlank()) p.setDescricao(descricao);
        if (dataFim   != null)                         p.setDataTerminoPrevista(dataFim);
    }

    /**
     * Altera o status de um projeto (com validação de regra no Model).
     */
    public void alterarStatus(int id, StatusProjeto novoStatus) {
        Projeto p = buscarPorId(id);
        if (p == null) throw new IllegalArgumentException("Projeto não encontrado.");
        p.setStatus(novoStatus); // Regra de negócio validada dentro de Projeto
    }

    // =========================================================
    // Remoção
    // =========================================================

    /**
     * Remove um projeto e desvincula do gerente responsável.
     */
    public boolean remover(int id) {
        Projeto p = buscarPorId(id);
        if (p != null) {
            p.getGerente().removerProjeto(p);
            return repositorio.getProjetos().remove(p);
        }
        return false;
    }

    // =========================================================
    // Alocação de Equipe
    // =========================================================

    /**
     * Associa uma equipe a um projeto (relação bidirecional).
     */
    public void adicionarEquipe(int projetoId, Equipe equipe) {
        Projeto p = buscarPorId(projetoId);
        if (p == null) throw new IllegalArgumentException("Projeto não encontrado.");
        p.adicionarEquipe(equipe);
        equipe.adicionarProjeto(projetoId);
    }

    // =========================================================
    // Consultas
    // =========================================================

    public List<Projeto> listarTodos() {
        return repositorio.getProjetos();
    }

    public Projeto buscarPorId(int id) {
        for (Projeto p : repositorio.getProjetos()) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}
