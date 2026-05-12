package com.gestao.controller;

import com.gestao.model.*;
import com.gestao.model.enums.StatusTarefa;
import com.gestao.repository.Repositorio;

import java.util.List;

/**
 * Controller responsável pela geração de relatórios de desempenho.
 *
 * Consolida informações de projetos, tarefas e equipes para
 * apresentação gerencial e acompanhamento de prazos.
 */
public class RelatorioController {

    private final Repositorio    repositorio;
    private final TarefaController tarefaController;

    public RelatorioController() {
        this.repositorio      = Repositorio.getInstancia();
        this.tarefaController = new TarefaController();
    }

    // =========================================================
    // Relatório por Projeto
    // =========================================================

    /**
     * Gera um relatório detalhado de desempenho de um projeto específico.
     *
     * Inclui: dados do projeto, progresso de tarefas, equipes alocadas
     * e detalhamento de cada tarefa.
     *
     * @param projetoId ID do projeto
     * @return String formatada com o relatório completo
     */
    public String gerarRelatorioProjeto(int projetoId) {
        Projeto projeto = null;
        for (Projeto p : repositorio.getProjetos()) {
            if (p.getId() == projetoId) { projeto = p; break; }
        }
        if (projeto == null) return "  Projeto não encontrado.";

        List<Tarefa> tarefas = tarefaController.listarPorProjeto(projetoId);
        int total        = tarefas.size();
        long concluidas  = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.CONCLUIDA).count();
        long emAndamento = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.EM_ANDAMENTO).count();
        long pendentes   = tarefas.stream().filter(t -> t.getStatus() == StatusTarefa.PENDENTE).count();
        double pct       = total > 0 ? (concluidas * 100.0 / total) : 0;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(62)).append("\n");
        sb.append("  RELATORIO DE DESEMPENHO\n");
        sb.append("  Projeto : ").append(projeto.getNome()).append("\n");
        sb.append("=".repeat(62)).append("\n");
        sb.append(String.format("  Gerente Responsavel : %s%n",   projeto.getGerente().getNome()));
        sb.append(String.format("  Status              : %s%n",   projeto.getStatus().getDescricao()));
        sb.append(String.format("  Inicio              : %s%n",   projeto.getDataInicio()));
        sb.append(String.format("  Prazo de Entrega    : %s%n",   projeto.getDataTerminoPrevista()));
        sb.append(String.format("  Descricao           : %s%n",   projeto.getDescricao()));
        sb.append("-".repeat(62)).append("\n");
        sb.append("  PROGRESSO DAS TAREFAS\n");
        sb.append("-".repeat(62)).append("\n");
        sb.append(String.format("  Total       : %d%n", total));
        sb.append(String.format("  Concluidas  : %d%n", concluidas));
        sb.append(String.format("  Em Andamento: %d%n", emAndamento));
        sb.append(String.format("  Pendentes   : %d%n", pendentes));
        sb.append(String.format("  Progresso   : %.1f%%%n", pct));

        if (!tarefas.isEmpty()) {
            sb.append("-".repeat(62)).append("\n");
            sb.append("  DETALHAMENTO DAS TAREFAS\n");
            sb.append("-".repeat(62)).append("\n");
            for (Tarefa t : tarefas) {
                sb.append("  ").append(t).append("\n");
            }
        }

        sb.append("-".repeat(62)).append("\n");
        sb.append("  EQUIPES ALOCADAS\n");
        sb.append("-".repeat(62)).append("\n");
        if (projeto.getEquipes().isEmpty()) {
            sb.append("  Nenhuma equipe alocada.\n");
        } else {
            for (Equipe e : projeto.getEquipes()) {
                sb.append("  ").append(e.getNome())
                  .append(" (").append(e.getMembros().size()).append(" membros)\n");
                for (Colaborador c : e.getMembros()) {
                    sb.append("    → ").append(c.getNome())
                      .append(" | ").append(c.exibirPerfil()).append("\n");
                }
            }
        }
        sb.append("=".repeat(62)).append("\n");
        return sb.toString();
    }

    // =========================================================
    // Relatório Geral
    // =========================================================

    /**
     * Gera um relatório resumido de todos os projetos cadastrados.
     *
     * @return String formatada com visão consolidada
     */
    public String gerarRelatorioGeral() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(62)).append("\n");
        sb.append("  RELATORIO GERAL DE PROJETOS\n");
        sb.append("=".repeat(62)).append("\n");

        if (repositorio.getProjetos().isEmpty()) {
            sb.append("  Nenhum projeto cadastrado.\n");
        } else {
            sb.append(String.format("  %-4s %-28s %-14s %s%n",
                    "ID", "Projeto", "Status", "Progresso"));
            sb.append("-".repeat(62)).append("\n");
            for (Projeto p : repositorio.getProjetos()) {
                List<Tarefa> ts = tarefaController.listarPorProjeto(p.getId());
                long conc = ts.stream().filter(t -> t.getStatus() == StatusTarefa.CONCLUIDA).count();
                double pct = ts.size() > 0 ? (conc * 100.0 / ts.size()) : 0;
                sb.append(String.format("  %-4d %-28s %-14s %.1f%%%n",
                        p.getId(), p.getNome(), p.getStatus().getDescricao(), pct));
            }
        }
        sb.append("=".repeat(62)).append("\n");
        return sb.toString();
    }
}
