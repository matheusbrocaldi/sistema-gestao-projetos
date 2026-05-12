package com.gestao.model;

import com.gestao.model.enums.StatusTarefa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma tarefa pertencente a um projeto.
 *
 * Cada tarefa é atribuída a um Colaborador específico e possui
 * prazo de entrega e status de andamento próprios.
 * A referência ao projeto é feita por ID para evitar dependência circular.
 */
public class Tarefa {

    private static int contadorId = 1;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int id;
    private String titulo;
    private String descricao;
    private LocalDate prazo;
    private StatusTarefa status;
    private Colaborador responsavel;
    private int projetoId; // ID do projeto ao qual esta tarefa pertence

    // =========================================================
    // Construtor
    // =========================================================

    public Tarefa(String titulo, String descricao, LocalDate prazo,
                  Colaborador responsavel, int projetoId) {
        this.id          = contadorId++;
        this.titulo      = titulo;
        this.descricao   = descricao;
        this.prazo       = prazo;
        this.status      = StatusTarefa.PENDENTE; // Estado inicial padrão
        this.responsavel = responsavel;
        this.projetoId   = projetoId;
    }

    // =========================================================
    // Getters e Setters
    // =========================================================

    public int getId()                          { return id; }

    public String getTitulo()                   { return titulo; }
    public void setTitulo(String t)             { this.titulo = t; }

    public String getDescricao()                { return descricao; }
    public void setDescricao(String d)          { this.descricao = d; }

    public LocalDate getPrazo()                 { return prazo; }
    public void setPrazo(LocalDate p)           { this.prazo = p; }

    public StatusTarefa getStatus()             { return status; }
    public void setStatus(StatusTarefa s)       { this.status = s; }

    public Colaborador getResponsavel()         { return responsavel; }
    public void setResponsavel(Colaborador c)   { this.responsavel = c; }

    public int getProjetoId()                   { return projetoId; }

    // =========================================================
    // toString
    // =========================================================

    @Override
    public String toString() {
        String resp = (responsavel != null) ? responsavel.getNome() : "Não atribuído";
        return String.format("[%d] %-35s | Status: %-12s | Prazo: %s | Responsável: %s",
                id, titulo, status.getDescricao(), prazo.format(FMT), resp);
    }
}
