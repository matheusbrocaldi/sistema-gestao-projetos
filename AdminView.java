package com.gestao.view;

import com.gestao.util.Util;
import java.util.Scanner;

/**
 * View do menu principal para usuários com perfil Administrador.
 *
 * O Administrador tem acesso irrestrito a todos os módulos do sistema:
 * usuários, projetos, equipes, tarefas e relatórios.
 */
public class AdminView {

    private final Scanner scanner;

    public AdminView(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Exibe o menu do administrador e redireciona para os submenus.
     *
     * @return true se o sistema deve ser encerrado, false para logout
     */
    public boolean exibir() {
        int opcao;
        do {
            Util.titulo("MENU ADMINISTRADOR");
            System.out.println("  1. Gerenciar Usuarios");
            System.out.println("  2. Gerenciar Projetos");
            System.out.println("  3. Gerenciar Equipes");
            System.out.println("  4. Gerenciar Tarefas");
            System.out.println("  5. Relatorios de Desempenho");
            System.out.println("  8. Logout");
            System.out.println("  0. Encerrar sistema");
            Util.linha();
            opcao = Util.lerInteiro(scanner, "  Opcao: ");

            switch (opcao) {
                case 1 -> new UsuarioView(scanner).exibir();
                case 2 -> new ProjetoView(scanner).exibir();
                case 3 -> new EquipeView(scanner).exibir();
                case 4 -> new TarefaView(scanner).exibir();
                case 5 -> new RelatorioView(scanner).exibir();
                case 8 -> { System.out.println("  Logout realizado."); return false; }
                case 0 -> { return true; }
                default -> System.out.println("  Opcao invalida.");
            }
        } while (opcao != 8 && opcao != 0);
        return false;
    }
}
