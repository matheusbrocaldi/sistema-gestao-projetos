package com.gestao.view;

import com.gestao.controller.EquipeController;
import com.gestao.controller.ProjetoController;
import com.gestao.controller.UsuarioController;
import com.gestao.model.*;
import com.gestao.model.enums.StatusProjeto;
import com.gestao.util.Util;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * View responsável pela interface de gerenciamento de Projetos.
 *
 * Requisito d: Ao cadastrar um projeto, lista APENAS os gerentes
 * cadastrados no sistema, impedindo a seleção de outros perfis.
 */
public class ProjetoView {

    private final ProjetoController projetoController;
    private final UsuarioController usuarioController;
    private final EquipeController  equipeController;
    private final Scanner scanner;

    public ProjetoView(Scanner scanner) {
        this.scanner            = scanner;
        this.projetoController  = new ProjetoController();
        this.usuarioController  = new UsuarioController();
        this.equipeController   = new EquipeController();
    }

    // =========================================================
    // Menu principal de projetos
    // =========================================================

    public void exibir() {
        int opcao;
        do {
            Util.titulo("GERENCIAMENTO DE PROJETOS");
            System.out.println("  1. Listar todos os projetos");
            System.out.println("  2. Cadastrar projeto");
            System.out.println("  3. Atualizar projeto");
            System.out.println("  4. Alterar status do projeto");
            System.out.println("  5. Alocar equipe em projeto");
            System.out.println("  6. Remover projeto");
            System.out.println("  0. Voltar");
            Util.linha();
            opcao = Util.lerInteiro(scanner, "  Opcao: ");

            switch (opcao) {
                case 1 -> listarTodos();
                case 2 -> cadastrar();
                case 3 -> atualizar();
                case 4 -> alterarStatus();
                case 5 -> alocarEquipe();
                case 6 -> remover();
                case 0 -> System.out.println("  Voltando...");
                default -> System.out.println("  Opcao invalida.");
            }
        } while (opcao != 0);
    }

    // =========================================================
    // Listagem
    // =========================================================

    public void listarTodos() {
        Util.titulo("LISTA DE PROJETOS");
        List<Projeto> lista = projetoController.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("  Nenhum projeto cadastrado.");
        } else {
            lista.forEach(p -> System.out.println("  " + p));
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Cadastro
    // =========================================================

    private void cadastrar() {
        Util.titulo("CADASTRAR PROJETO");
        try {
            String nome      = Util.lerTexto(scanner, "  Nome do projeto : ");
            String descricao = Util.lerTexto(scanner, "  Descricao       : ");
            LocalDate inicio = Util.lerData(scanner,  "  Data de inicio (dd/MM/yyyy) : ");
            LocalDate fim    = Util.lerData(scanner,  "  Data de termino prevista (dd/MM/yyyy) : ");

            // Requisito d — lista APENAS gerentes cadastrados
            List<Gerente> gerentes = usuarioController.listarGerentes();
            if (gerentes.isEmpty()) {
                System.out.println("  AVISO: Nenhum gerente cadastrado. Cadastre um gerente primeiro.");
                Util.pausar(scanner);
                return;
            }

            System.out.println("\n  Gerentes disponiveis:");
            for (Gerente g : gerentes) {
                System.out.printf("    [%d] %s | %s%n", g.getId(), g.getNome(), g.getEmail());
            }

            int gerenteId = Util.lerInteiro(scanner, "  ID do gerente responsavel: ");
            Usuario u = usuarioController.buscarPorId(gerenteId);
            if (!(u instanceof Gerente gerente)) {
                System.out.println("  ERRO: O usuario selecionado nao e um Gerente.");
                Util.pausar(scanner);
                return;
            }

            Projeto p = projetoController.cadastrar(nome, descricao, inicio, fim, gerente);
            System.out.println("\n  Projeto cadastrado com sucesso:\n  " + p);
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Atualização
    // =========================================================

    private void atualizar() {
        Util.titulo("ATUALIZAR PROJETO");
        listarTodos();
        try {
            int id           = Util.lerInteiro(scanner, "  ID do projeto: ");
            String nome      = Util.lerTexto(scanner, "  Novo nome (Enter para manter): ");
            String descricao = Util.lerTexto(scanner, "  Nova descricao (Enter para manter): ");
            LocalDate fim    = Util.lerData(scanner,  "  Novo prazo (dd/MM/yyyy): ");
            projetoController.atualizar(id, nome, descricao, fim);
            System.out.println("  Projeto atualizado com sucesso.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Alterar Status
    // =========================================================

    private void alterarStatus() {
        Util.titulo("ALTERAR STATUS DO PROJETO");
        listarTodos();
        try {
            int id = Util.lerInteiro(scanner, "  ID do projeto: ");
            System.out.println("  Status:");
            System.out.println("    1. Planejado");
            System.out.println("    2. Em Andamento");
            System.out.println("    3. Concluido");
            System.out.println("    4. Cancelado");
            int op = Util.lerInteiro(scanner, "  Escolha o novo status: ");
            StatusProjeto status = switch (op) {
                case 1 -> StatusProjeto.PLANEJADO;
                case 2 -> StatusProjeto.EM_ANDAMENTO;
                case 3 -> StatusProjeto.CONCLUIDO;
                case 4 -> StatusProjeto.CANCELADO;
                default -> throw new IllegalArgumentException("Status invalido.");
            };
            projetoController.alterarStatus(id, status);
            System.out.println("  Status atualizado com sucesso.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Alocar Equipe
    // =========================================================

    private void alocarEquipe() {
        Util.titulo("ALOCAR EQUIPE EM PROJETO");
        try {
            List<Projeto> projetos = projetoController.listarTodos();
            if (projetos.isEmpty()) { System.out.println("  Nenhum projeto."); Util.pausar(scanner); return; }
            projetos.forEach(p -> System.out.println("  " + p));
            int projetoId = Util.lerInteiro(scanner, "  ID do projeto: ");

            List<Equipe> equipes = equipeController.listarTodas();
            if (equipes.isEmpty()) { System.out.println("  Nenhuma equipe."); Util.pausar(scanner); return; }
            equipes.forEach(e -> System.out.println("  " + e));
            int equipeId = Util.lerInteiro(scanner, "  ID da equipe: ");

            Equipe equipe = equipeController.buscarPorId(equipeId);
            if (equipe == null) { System.out.println("  Equipe nao encontrada."); Util.pausar(scanner); return; }

            projetoController.adicionarEquipe(projetoId, equipe);
            System.out.println("  Equipe alocada com sucesso.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Remoção
    // =========================================================

    private void remover() {
        Util.titulo("REMOVER PROJETO");
        listarTodos();
        try {
            int id = Util.lerInteiro(scanner, "  ID do projeto a remover: ");
            boolean ok = projetoController.remover(id);
            System.out.println(ok ? "  Projeto removido." : "  Projeto nao encontrado.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }
}
