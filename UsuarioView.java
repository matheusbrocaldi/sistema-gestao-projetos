package com.gestao.view;

import com.gestao.controller.UsuarioController;
import com.gestao.model.*;
import com.gestao.model.enums.Cargo;
import com.gestao.util.Util;

import java.util.List;
import java.util.Scanner;

/**
 * View responsável pela interface de gerenciamento de Usuários.
 * Exibe menus e captura entradas do usuário via console.
 */
public class UsuarioView {

    private final UsuarioController controller;
    private final Scanner scanner;

    public UsuarioView(Scanner scanner) {
        this.scanner    = scanner;
        this.controller = new UsuarioController();
    }

    // =========================================================
    // Menu principal de usuários
    // =========================================================

    public void exibir() {
        int opcao;
        do {
            Util.titulo("GERENCIAMENTO DE USUARIOS");
            System.out.println("  1. Listar todos os usuarios");
            System.out.println("  2. Cadastrar Administrador");
            System.out.println("  3. Cadastrar Gerente");
            System.out.println("  4. Cadastrar Colaborador");
            System.out.println("  5. Atualizar usuario");
            System.out.println("  6. Remover usuario");
            System.out.println("  0. Voltar");
            Util.linha();
            opcao = Util.lerInteiro(scanner, "  Opcao: ");

            switch (opcao) {
                case 1 -> listarTodos();
                case 2 -> cadastrarAdministrador();
                case 3 -> cadastrarGerente();
                case 4 -> cadastrarColaborador();
                case 5 -> atualizar();
                case 6 -> remover();
                case 0 -> System.out.println("  Voltando...");
                default -> System.out.println("  Opcao invalida.");
            }
        } while (opcao != 0);
    }

    // =========================================================
    // Listagem
    // =========================================================

    private void listarTodos() {
        Util.titulo("LISTA DE USUARIOS");
        List<Usuario> lista = controller.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("  Nenhum usuario cadastrado.");
        } else {
            lista.forEach(u -> System.out.println("  " + u));
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Cadastros
    // =========================================================

    private void cadastrarAdministrador() {
        Util.titulo("CADASTRAR ADMINISTRADOR");
        try {
            String nome  = Util.lerTexto(scanner, "  Nome completo : ");
            String cpf   = Util.lerTexto(scanner, "  CPF           : ");
            String email = Util.lerTexto(scanner, "  E-mail        : ");
            String login = Util.lerTexto(scanner, "  Login         : ");
            String senha = Util.lerTexto(scanner, "  Senha         : ");
            Administrador a = controller.cadastrarAdministrador(nome, cpf, email, login, senha);
            System.out.println("\n  Administrador cadastrado: " + a);
        } catch (IllegalArgumentException e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    private void cadastrarGerente() {
        Util.titulo("CADASTRAR GERENTE");
        try {
            String nome  = Util.lerTexto(scanner, "  Nome completo : ");
            String cpf   = Util.lerTexto(scanner, "  CPF           : ");
            String email = Util.lerTexto(scanner, "  E-mail        : ");
            String login = Util.lerTexto(scanner, "  Login         : ");
            String senha = Util.lerTexto(scanner, "  Senha         : ");
            Gerente g = controller.cadastrarGerente(nome, cpf, email, login, senha);
            System.out.println("\n  Gerente cadastrado: " + g);
        } catch (IllegalArgumentException e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    private void cadastrarColaborador() {
        Util.titulo("CADASTRAR COLABORADOR");
        try {
            String nome  = Util.lerTexto(scanner, "  Nome completo : ");
            String cpf   = Util.lerTexto(scanner, "  CPF           : ");
            String email = Util.lerTexto(scanner, "  E-mail        : ");
            String login = Util.lerTexto(scanner, "  Login         : ");
            String senha = Util.lerTexto(scanner, "  Senha         : ");

            System.out.println("  Cargo:");
            System.out.println("    1. Desenvolvedor");
            System.out.println("    2. Analista de Sistemas");
            System.out.println("    3. Designer de Interface");
            int op = Util.lerInteiro(scanner, "  Escolha o cargo: ");
            Cargo cargo = switch (op) {
                case 1 -> Cargo.DESENVOLVEDOR;
                case 2 -> Cargo.ANALISTA_SISTEMAS;
                case 3 -> Cargo.DESIGNER_INTERFACE;
                default -> throw new IllegalArgumentException("Cargo invalido.");
            };

            Colaborador c = controller.cadastrarColaborador(nome, cpf, email, login, senha, cargo);
            System.out.println("\n  Colaborador cadastrado: " + c);
        } catch (IllegalArgumentException e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Atualização
    // =========================================================

    private void atualizar() {
        Util.titulo("ATUALIZAR USUARIO");
        listarTodos();
        try {
            int id    = Util.lerInteiro(scanner, "  ID do usuario a atualizar: ");
            String nome  = Util.lerTexto(scanner, "  Novo nome (Enter para manter): ");
            String email = Util.lerTexto(scanner, "  Novo e-mail (Enter para manter): ");
            controller.atualizar(id, nome, email);
            System.out.println("  Usuario atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }

    // =========================================================
    // Remoção
    // =========================================================

    private void remover() {
        Util.titulo("REMOVER USUARIO");
        listarTodos();
        try {
            int id = Util.lerInteiro(scanner, "  ID do usuario a remover: ");
            boolean ok = controller.remover(id);
            System.out.println(ok ? "  Usuario removido com sucesso." : "  Usuario nao encontrado.");
        } catch (Exception e) {
            System.out.println("  ERRO: " + e.getMessage());
        }
        Util.pausar(scanner);
    }
}
