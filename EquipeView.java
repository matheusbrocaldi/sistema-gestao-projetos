package com.gestao.view;

import com.gestao.controller.EquipeController;
import com.gestao.controller.UsuarioController;
import com.gestao.model.*;
import com.gestao.util.Util;

import java.util.List;
import java.util.Scanner;

/**
 * View responsável pela interface de gerenciamento de Equipes.
 */
public class EquipeView {

    private final EquipeController  equipeController;
    private final UsuarioController usuarioController;
    private final Scanner scanner;

    public EquipeView(Scanner scanner) {
        this.scanner            = scanner;
        this.equipeController   = new EquipeController();
        this.usuarioController  = new UsuarioController();
    }

    // =========================================================
    // Menu principal de equipes
    // =========================================================

    public void exibir() {
        int opcao;
        do {
            Util.titulo("GERENCIAMENTO DE EQUIPES");
            System.out.println("  1. Listar todas as equipes");
            System.out.println("  2. Cadastrar equipe");
            System.out.println("  3. Atualizar equipe");
            System.out.println("  4. Adicionar membro a equipe");
            System.out.println("  5. Remover membro da equipe");
            System.out.println("  6. Remover equipe");
            System.out.println("  0. Voltar");
            Util.linha();
            opcao = Util.lerInteiro(scanner, "  Opcao: ");

            switch (opcao) {
                case 1 -> listarTodas();
                case 2 -> cadastrar();
                case 3 -> atualizar();
                case 4 -> adicionarMembro();
                case 5 -> removerMembro();
                case 6 -> remover();
                case 0 -> System.out.println("  Voltando...");
                default -> System.out.println("  Opcao invalida.");
            }
        } while (opcao != 0);
    }

    // =========================================================
    // Listagem
    // =========================================================

    private void listarTodas() {
        Util.titulo("LISTA DE EQUIPES");
        List<Equipe> lista = equipeController.listarTodas();
        if (lista.isEmpty()) {
            System.out.println("  Nenhuma equipe cadastrada.");
        } else {
            for (Equipe e : lista) {
                System.out.println("  " + e);
                if (!e.getMembros().isEmpty()) {
                    for (Colaborador c : e.getMembros()) {
                        System.out.println("    → " + c.getNome() + " | " + c.getCargo().getDescricao());
                    }
                }
            }
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Cadastro
    // =========================================================

    private void cadastrar() {
        Util.titulo("CADASTRAR EQUIPE");
        try {
            String nome      = Util.lerTexto(scanner, "  Nome da equipe : ");
            String descricao = Util.lerTexto(scanner, "  Descricao      : ");
            Equipe e = equipeController.cadastrar(nome, descricao);
            System.out.println("  Equipe cadastrada: " + e);
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Atualização
    // =========================================================

    private void atualizar() {
        Util.titulo("ATUALIZAR EQUIPE");
        listarTodas();
        try {
            int id           = Util.lerInteiro(scanner, "  ID da equipe: ");
            String nome      = Util.lerTexto(scanner, "  Novo nome: ");
            String descricao = Util.lerTexto(scanner, "  Nova descricao: ");
            equipeController.atualizar(id, nome, descricao);
            System.out.println("  Equipe atualizada com sucesso.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Membros
    // =========================================================

    private void adicionarMembro() {
        Util.titulo("ADICIONAR MEMBRO A EQUIPE");
        try {
            List<Equipe> equipes = equipeController.listarTodas();
            if (equipes.isEmpty()) { System.out.println("  Nenhuma equipe."); Util.pausar(scanner); return; }
            equipes.forEach(e -> System.out.println("  " + e));
            int equipeId = Util.lerInteiro(scanner, "  ID da equipe: ");

            List<Colaborador> colaboradores = usuarioController.listarColaboradores();
            if (colaboradores.isEmpty()) { System.out.println("  Nenhum colaborador cadastrado."); Util.pausar(scanner); return; }
            colaboradores.forEach(c -> System.out.println("  " + c));
            int colabId = Util.lerInteiro(scanner, "  ID do colaborador: ");

            Usuario u = usuarioController.buscarPorId(colabId);
            if (!(u instanceof Colaborador colab)) {
                System.out.println("  ERRO: Usuario nao e um Colaborador.");
                Util.pausar(scanner);
                return;
            }

            equipeController.adicionarMembro(equipeId, colab);
            System.out.println("  Membro adicionado com sucesso.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    private void removerMembro() {
        Util.titulo("REMOVER MEMBRO DA EQUIPE");
        try {
            List<Equipe> equipes = equipeController.listarTodas();
            if (equipes.isEmpty()) { System.out.println("  Nenhuma equipe."); Util.pausar(scanner); return; }
            equipes.forEach(e -> System.out.println("  " + e));
            int equipeId = Util.lerInteiro(scanner, "  ID da equipe: ");

            Equipe equipe = equipeController.buscarPorId(equipeId);
            if (equipe == null || equipe.getMembros().isEmpty()) {
                System.out.println("  Equipe sem membros.");
                Util.pausar(scanner);
                return;
            }

            equipe.getMembros().forEach(c -> System.out.println("  " + c));
            int colabId = Util.lerInteiro(scanner, "  ID do colaborador a remover: ");

            Usuario u = usuarioController.buscarPorId(colabId);
            if (!(u instanceof Colaborador colab)) {
                System.out.println("  ERRO: Usuario nao e um Colaborador.");
                Util.pausar(scanner);
                return;
            }

            equipeController.removerMembro(equipeId, colab);
            System.out.println("  Membro removido com sucesso.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Remoção
    // =========================================================

    private void remover() {
        Util.titulo("REMOVER EQUIPE");
        listarTodas();
        try {
            int id = Util.lerInteiro(scanner, "  ID da equipe a remover: ");
            boolean ok = equipeController.remover(id);
            System.out.println(ok ? "  Equipe removida." : "  Equipe nao encontrada.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }
}
