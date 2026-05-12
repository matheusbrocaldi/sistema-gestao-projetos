package com.gestao.controller;

import com.gestao.model.*;
import com.gestao.model.enums.StatusTarefa;
import com.gestao.repository.Repositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsável por todas as operações relacionadas a Tarefas.
 */
public class TarefaController {

    private final Repositorio repositorio;

    public TarefaController() {
        this.repositorio = Repositorio.getInstancia();
    }

    // =========================================================
    // Cadastro
    // =========================================================

    /**
     * Cadastra uma nova tarefa e a vincula ao projeto correspondente.
     */
    public Tarefa cadastrar(String titulo, String descricao, LocalDate prazo,
                            Colaborador responsavel, Projeto projeto) {
        Tarefa t = new Tarefa(titulo, descricao, prazo, responsavel, projeto.getId());
        repositorio.getTarefas().add(t);
        projeto.adicionarTarefa(t);
        return t;
    }

    // =========================================================
    // Atualização
    // =========================================================

    public void atualizar(int id, String titulo, String descricao, LocalDate prazo) {
        Tarefa t = buscarPorId(id);
        if (t == null) throw new IllegalArgumentException("Tarefa não encontrada.");
        if (titulo    != null && !titulo.isBlank())    t.setTitulo(titulo);
        if (descricao != null && !descricao.isBlank()) t.setDescricao(descricao);
        if (prazo     != null)                         t.setPrazo(prazo);
    }

    /**
     * Altera o status de uma tarefa.
     */
    public void alterarStatus(int id, StatusTarefa novoStatus) {
        Tarefa t = buscarPorId(id);
        if (t == null) throw new IllegalArgumentException("Tarefa não encontrada.");
        t.setStatus(novoStatus);
    }

    // =========================================================
    // Remoção
    // =========================================================

    public boolean remover(int id) {
        return repositorio.getTarefas().removeIf(t -> t.getId() == id);
    }

    // =========================================================
    // Consultas
    // =========================================================

    public List<Tarefa> listarTodas() {
        return repositorio.getTarefas();
    }

    /**
     * Retorna todas as tarefas de um projeto específico.
     */
    public List<Tarefa> listarPorProjeto(int projetoId) {
        List<Tarefa> resultado = new ArrayList<>();
        for (Tarefa t : repositorio.getTarefas()) {
            if (t.getProjetoId() == projetoId) resultado.add(t);
        }
        return resultado;
    }

    /**
     * Retorna todas as tarefas atribuídas a um colaborador específico.
     */
    public List<Tarefa> listarPorColaborador(int colaboradorId) {
        List<Tarefa> resultado = new ArrayList<>();
        for (Tarefa t : repositorio.getTarefas()) {
            if (t.getResponsavel() != null && t.getResponsavel().getId() == colaboradorId) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public Tarefa buscarPorId(int id) {
        for (Tarefa t : repositorio.getTarefas()) {
            if (t.getId() == id) return t;
        }
        return null;
    }
}
