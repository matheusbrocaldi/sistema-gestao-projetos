package com.gestao.view;

import com.gestao.controller.ProjetoController;
import com.gestao.controller.TarefaController;
import com.gestao.controller.UsuarioController;
import com.gestao.model.*;
import com.gestao.model.enums.StatusTarefa;
import com.gestao.util.Util;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * View responsável pela interface de gerenciamento de Tarefas.
 */
public class TarefaView {

    private final TarefaController  tarefaController;
    private final ProjetoController projetoController;
    private final UsuarioController usuarioController;
    private final Scanner scanner;

    public TarefaView(Scanner scanner) {
        this.scanner            = scanner;
        this.tarefaController   = new TarefaController();
        this.projetoController  = new ProjetoController();
        this.usuarioController  = new UsuarioController();
    }

    // =========================================================
    // Menu principal de tarefas
    // =========================================================

    public void exibir() {
        int opcao;
        do {
            Util.titulo("GERENCIAMENTO DE TAREFAS");
            System.out.println("  1. Listar todas as tarefas");
            System.out.println("  2. Listar tarefas por projeto");
            System.out.println("  3. Cadastrar tarefa");
            System.out.println("  4. Atualizar tarefa");
            System.out.println("  5. Alterar status da tarefa");
            System.out.println("  6. Remover tarefa");
            System.out.println("  0. Voltar");
            Util.linha();
            opcao = Util.lerInteiro(scanner, "  Opcao: ");

            switch (opcao) {
                case 1 -> listarTodas();
                case 2 -> listarPorProjeto();
                case 3 -> cadastrar();
                case 4 -> atualizar();
                case 5 -> alterarStatus();
                case 6 -> remover();
                case 0 -> System.out.println("  Voltando...");
                default -> System.out.println("  Opcao invalida.");
            }
        } while (opcao != 0);
    }

    // =========================================================
    // Menu reduzido para Colaborador
    // =========================================================

    /**
     * Exibe apenas as tarefas do colaborador logado
     * com opção de atualizar o status.
     */
    public void exibirParaColaborador(Colaborador colaborador) {
        int opcao;
        do {
            Util.titulo("MINHAS TAREFAS — " + colaborador.getNome().toUpperCase());
            List<Tarefa> minhas = tarefaController.listarPorColaborador(colaborador.getId());
            if (minhas.isEmpty()) {
                System.out.println("  Nenhuma tarefa atribuida a voce.");
            } else {
                minhas.forEach(t -> System.out.println("  " + t));
            }
            Util.linha();
            System.out.println("  1. Atualizar status de uma tarefa");
            System.out.println("  0. Voltar");
            opcao = Util.lerInteiro(scanner, "  Opcao: ");

            if (opcao == 1) alterarStatus();
        } while (opcao != 0);
    }

    // =========================================================
    // Listagens
    // =========================================================

    private void listarTodas() {
        Util.titulo("LISTA DE TAREFAS");
        List<Tarefa> lista = tarefaController.listarTodas();
        if (lista.isEmpty()) {
            System.out.println("  Nenhuma tarefa cadastrada.");
        } else {
            lista.forEach(t -> System.out.println("  " + t));
        }
        Util.pausar(scanner);
    }

    private void listarPorProjeto() {
        Util.titulo("TAREFAS POR PROJETO");
        List<Projeto> projetos = projetoController.listarTodos();
        if (projetos.isEmpty()) { System.out.println("  Nenhum projeto."); Util.pausar(scanner); return; }
        projetos.forEach(p -> System.out.println("  " + p));
        int projetoId = Util.lerInteiro(scanner, "  ID do projeto: ");
        List<Tarefa> lista = tarefaController.listarPorProjeto(projetoId);
        if (lista.isEmpty()) {
            System.out.println("  Nenhuma tarefa para este projeto.");
        } else {
            lista.forEach(t -> System.out.println("  " + t));
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Cadastro
    // =========================================================

    private void cadastrar() {
        Util.titulo("CADASTRAR TAREFA");
        try {
            List<Projeto> projetos = projetoController.listarTodos();
            if (projetos.isEmpty()) { System.out.println("  Nenhum projeto."); Util.pausar(scanner); return; }
            projetos.forEach(p -> System.out.println("  " + p));
            int projetoId = Util.lerInteiro(scanner, "  ID do projeto: ");
            Projeto projeto = projetoController.buscarPorId(projetoId);
            if (projeto == null) { System.out.println("  Projeto nao encontrado."); Util.pausar(scanner); return; }

            List<Colaborador> colaboradores = usuarioController.listarColaboradores();
            if (colaboradores.isEmpty()) { System.out.println("  Nenhum colaborador."); Util.pausar(scanner); return; }
            colaboradores.forEach(c -> System.out.println("  " + c));
            int colabId = Util.lerInteiro(scanner, "  ID do colaborador responsavel: ");
            Usuario u = usuarioController.buscarPorId(colabId);
            if (!(u instanceof Colaborador colab)) { System.out.println("  Nao e um colaborador."); Util.pausar(scanner); return; }

            String titulo    = Util.lerTexto(scanner, "  Titulo       : ");
            String descricao = Util.lerTexto(scanner, "  Descricao    : ");
            LocalDate prazo  = Util.lerData(scanner,  "  Prazo (dd/MM/yyyy): ");

            Tarefa t = tarefaController.cadastrar(titulo, descricao, prazo, colab, projeto);
            System.out.println("  Tarefa cadastrada: " + t);
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Atualização
    // =========================================================

    private void atualizar() {
        Util.titulo("ATUALIZAR TAREFA");
        listarTodas();
        try {
            int id           = Util.lerInteiro(scanner, "  ID da tarefa: ");
            String titulo    = Util.lerTexto(scanner, "  Novo titulo: ");
            String descricao = Util.lerTexto(scanner, "  Nova descricao: ");
            LocalDate prazo  = Util.lerData(scanner,  "  Novo prazo (dd/MM/yyyy): ");
            tarefaController.atualizar(id, titulo, descricao, prazo);
            System.out.println("  Tarefa atualizada com sucesso.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Alterar Status
    // =========================================================

    private void alterarStatus() {
        Util.titulo("ALTERAR STATUS DA TAREFA");
        listarTodas();
        try {
            int id = Util.lerInteiro(scanner, "  ID da tarefa: ");
            System.out.println("  Status:");
            System.out.println("    1. Pendente");
            System.out.println("    2. Em Andamento");
            System.out.println("    3. Concluida");
            int op = Util.lerInteiro(scanner, "  Escolha o status: ");
            StatusTarefa status = switch (op) {
                case 1 -> StatusTarefa.PENDENTE;
                case 2 -> StatusTarefa.EM_ANDAMENTO;
                case 3 -> StatusTarefa.CONCLUIDA;
                default -> throw new IllegalArgumentException("Status invalido.");
            };
            tarefaController.alterarStatus(id, status);
            System.out.println("  Status atualizado.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Remoção
    // =========================================================

    private void remover() {
        Util.titulo("REMOVER TAREFA");
        listarTodas();
        try {
            int id = Util.lerInteiro(scanner, "  ID da tarefa a remover: ");
            boolean ok = tarefaController.remover(id);
            System.out.println(ok ? "  Tarefa removida." : "  Tarefa nao encontrada.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }
}
