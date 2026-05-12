package com.gestao.view;

import com.gestao.model.Gerente;
import com.gestao.util.Util;
import java.util.Scanner;

/**
 * View do menu principal para usuários com perfil Gerente.
 *
 * O Gerente pode gerenciar projetos, equipes, tarefas e
 * acessar relatórios de desempenho. Não gerencia outros usuários.
 */
public class GerenteView {

    private final Scanner scanner;
    private final Gerente gerente;

    public GerenteView(Scanner scanner, Gerente gerente) {
        this.scanner = scanner;
        this.gerente = gerente;
    }

    /**
     * Exibe o menu do gerente.
     *
     * @return true para encerrar o sistema, false para logout
     */
    public boolean exibir() {
        int opcao;
        do {
            Util.titulo("MENU GERENTE — " + gerente.getNome().toUpperCase());
            System.out.println("  1. Gerenciar Projetos");
            System.out.println("  2. Gerenciar Equipes");
            System.out.println("  3. Gerenciar Tarefas");
            System.out.println("  4. Relatorios de Desempenho");
            System.out.println("  8. Logout");
            System.out.println("  0. Encerrar sistema");
            Util.linha();
            opcao = Util.lerInteiro(scanner, "  Opcao: ");

            switch (opcao) {
                case 1 -> new ProjetoView(scanner).exibir();
                case 2 -> new EquipeView(scanner).exibir();
                case 3 -> new TarefaView(scanner).exibir();
                case 4 -> new RelatorioView(scanner).exibir();
                case 8 -> { System.out.println("  Logout realizado."); return false; }
                case 0 -> { return true; }
                default -> System.out.println("  Opcao invalida.");
            }
        } while (opcao != 8 && opcao != 0);
        return false;
    }
}
