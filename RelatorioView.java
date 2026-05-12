package com.gestao.view;

import com.gestao.controller.ProjetoController;
import com.gestao.controller.RelatorioController;
import com.gestao.model.Projeto;
import com.gestao.util.Util;

import java.util.List;
import java.util.Scanner;

/**
 * View responsável pela interface de exibição de Relatórios de Desempenho.
 */
public class RelatorioView {

    private final RelatorioController relatorioController;
    private final ProjetoController   projetoController;
    private final Scanner scanner;

    public RelatorioView(Scanner scanner) {
        this.scanner              = scanner;
        this.relatorioController  = new RelatorioController();
        this.projetoController    = new ProjetoController();
    }

    // =========================================================
    // Menu de relatórios
    // =========================================================

    public void exibir() {
        int opcao;
        do {
            Util.titulo("RELATORIOS DE DESEMPENHO");
            System.out.println("  1. Relatorio geral de projetos");
            System.out.println("  2. Relatorio detalhado por projeto");
            System.out.println("  0. Voltar");
            Util.linha();
            opcao = Util.lerInteiro(scanner, "  Opcao: ");

            switch (opcao) {
                case 1 -> relatorioGeral();
                case 2 -> relatorioDetalhado();
                case 0 -> System.out.println("  Voltando...");
                default -> System.out.println("  Opcao invalida.");
            }
        } while (opcao != 0);
    }

    // =========================================================
    // Relatórios
    // =========================================================

    private void relatorioGeral() {
        System.out.println(relatorioController.gerarRelatorioGeral());
        Util.pausar(scanner);
    }

    private void relatorioDetalhado() {
        Util.titulo("RELATORIO DETALHADO");
        List<Projeto> projetos = projetoController.listarTodos();
        if (projetos.isEmpty()) {
            System.out.println("  Nenhum projeto cadastrado.");
            Util.pausar(scanner);
            return;
        }
        projetos.forEach(p -> System.out.println("  " + p));
        int id = Util.lerInteiro(scanner, "  ID do projeto: ");
        System.out.println(relatorioController.gerarRelatorioProjeto(id));
        Util.pausar(scanner);
    }
}
