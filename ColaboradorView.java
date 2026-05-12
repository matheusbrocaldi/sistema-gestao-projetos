package com.gestao.view;

import com.gestao.model.Colaborador;
import com.gestao.util.Util;
import java.util.Scanner;

/**
 * View do menu principal para usuários com perfil Colaborador.
 *
 * O Colaborador possui acesso restrito: visualiza e atualiza
 * apenas as tarefas que foram atribuídas a ele.
 */
public class ColaboradorView {

    private final Scanner scanner;
    private final Colaborador colaborador;

    public ColaboradorView(Scanner scanner, Colaborador colaborador) {
        this.scanner     = scanner;
        this.colaborador = colaborador;
    }

    /**
     * Exibe o menu do colaborador.
     *
     * @return true para encerrar o sistema, false para logout
     */
    public boolean exibir() {
        int opcao;
        TarefaView tarefaView = new TarefaView(scanner);
        do {
            Util.titulo("MENU COLABORADOR — " + colaborador.getNome().toUpperCase());
            System.out.println("  Cargo : " + colaborador.getCargo().getDescricao());
            Util.linha();
            System.out.println("  1. Visualizar e atualizar minhas tarefas");
            System.out.println("  8. Logout");
            System.out.println("  0. Encerrar sistema");
            Util.linha();
            opcao = Util.lerInteiro(scanner, "  Opcao: ");

            switch (opcao) {
                case 1 -> tarefaView.exibirParaColaborador(colaborador);
                case 8 -> { System.out.println("  Logout realizado."); return false; }
                case 0 -> { return true; }
                default -> System.out.println("  Opcao invalida.");
            }
        } while (opcao != 8 && opcao != 0);
        return false;
    }
}
