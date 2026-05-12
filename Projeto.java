package com.gestao.model;

import com.gestao.model.enums.StatusProjeto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um projeto gerenciado pelo sistema.
 *
 * Cada projeto possui:
 *  - Um gerente responsável (obrigatório)
 *  - Status que evolui ao longo do ciclo de vida
 *  - Equipes alocadas
 *  - Tarefas distribuídas entre os colaboradores
 *
 * Demonstra Encapsulamento: o método setStatus() possui validação
 * de regra de negócio embutida.
 */
public class Projeto {

    private static int contadorId = 1;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private StatusProjeto status;
    private Gerente gerente;
    private List<Equipe> equipes;
    private List<Tarefa> tarefas;

    // =========================================================
    // Construtor
    // =========================================================

    public Projeto(String nome, String descricao,
                   LocalDate dataInicio, LocalDate dataTerminoPrevista,
                   Gerente gerente) {
        this.id                  = contadorId++;
        this.nome                = nome;
        this.descricao           = descricao;
        this.dataInicio          = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status              = StatusProjeto.PLANEJADO; // Estado inicial padrão
        this.gerente             = gerente;
        this.equipes             = new ArrayList<>();
        this.tarefas             = new ArrayList<>();
    }

    // =========================================================
    // Getters e Setters
    // =========================================================

    public int getId()                              { return id; }

    public String getNome()                         { return nome; }
    public void setNome(String nome)                { this.nome = nome; }

    public String getDescricao()                    { return descricao; }
    public void setDescricao(String d)              { this.descricao = d; }

    public LocalDate getDataInicio()                { return dataInicio; }
    public void setDataInicio(LocalDate d)          { this.dataInicio = d; }

    public LocalDate getDataTerminoPrevista()       { return dataTerminoPrevista; }
    public void setDataTerminoPrevista(LocalDate d) { this.dataTerminoPrevista = d; }

    public StatusProjeto getStatus()                { return status; }

    /**
     * Altera o status do projeto com validação de regra de negócio.
     * Projetos concluídos ou cancelados não podem ser reabertos.
     * Demonstra Encapsulamento — a regra vive dentro da própria classe.
     */
    public void setStatus(StatusProjeto novoStatus) {
        if (this.status == StatusProjeto.CONCLUIDO || this.status == StatusProjeto.CANCELADO) {
            throw new IllegalStateException(
                "Projeto '" + nome + "' já foi finalizado e não pode ter o status alterado.");
        }
        this.status = novoStatus;
    }

    public Gerente getGerente()                     { return gerente; }
    public void setGerente(Gerente g)               { this.gerente = g; }

    public List<Equipe> getEquipes()                { return equipes; }
    public List<Tarefa> getTarefas()                { return tarefas; }

    // =========================================================
    // Métodos auxiliares
    // =========================================================

    public void adicionarEquipe(Equipe equipe) {
        if (!equipes.contains(equipe)) equipes.add(equipe);
    }

    public void adicionarTarefa(Tarefa tarefa) {
        if (!tarefas.contains(tarefa)) tarefas.add(tarefa);
    }

    public void removerTarefa(Tarefa tarefa) {
        tarefas.remove(tarefa);
    }

    // =========================================================
    // toString
    // =========================================================

    @Override
    public String toString() {
        return String.format("[%d] %-30s | Status: %-12s | Início: %s | Término: %s | Gerente: %s",
                id, nome, status.getDescricao(),
                dataInicio.format(FMT),
                dataTerminoPrevista.format(FMT),
                gerente.getNome());
    }
}
