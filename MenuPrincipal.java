package com.gestao.view;

import com.gestao.controller.UsuarioController;
import com.gestao.model.*;
import com.gestao.util.Util;

import java.util.Scanner;

/**
 * Menu principal do sistema.
 *
 * Responsável por exibir a tela de login, autenticar o usuário
 * e redirecioná-lo ao menu correspondente ao seu perfil.
 *
 * Padrão MVC: esta é a camada View de entrada da aplicação.
 */
public class MenuPrincipal {

    private final UsuarioController usuarioController;
    private final Scanner scanner;

    public MenuPrincipal(Scanner scanner) {
        this.scanner            = scanner;
        this.usuarioController  = new UsuarioController();
    }

    // =========================================================
    // Inicialização do sistema
    // =========================================================

    /**
     * Inicia o loop principal: exibe login e roteia ao menu correto.
     */
    public void iniciar() {
        cabecalho();
        System.out.println("  Credenciais padrao para teste:");
        System.out.println("    Admin       : login=admin    / senha=admin123");
        System.out.println("    Gerente 1   : login=gerente  / senha=ger123");
        System.out.println("    Gerente 2   : login=roberto  / senha=rob123");
        System.out.println("    Colaborador : login=joao     / senha=joao123");
        Util.linhaDupla();

        boolean executando = true;
        while (executando) {
            Usuario logado = realizarLogin();
            if (logado == null) {
                System.out.println("\n  Credenciais invalidas. Tente novamente.\n");
                continue;
            }
            System.out.println("\n  Bem-vindo(a), " + logado.getNome() + "!");
            System.out.println("  " + logado.exibirPerfil());
            executando = !rotearPorPerfil(logado);
        }

        System.out.println("\n  Sistema encerrado. Ate logo!\n");
    }

    // =========================================================
    // Login
    // =========================================================

    private Usuario realizarLogin() {
        Util.linha();
        System.out.println("  ACESSO AO SISTEMA");
        Util.linha();
        System.out.print("  Login : ");
        String login = scanner.nextLine().trim();
        System.out.print("  Senha : ");
        String senha = scanner.nextLine().trim();
        return usuarioController.autenticar(login, senha);
    }

    // =========================================================
    // Roteamento por perfil
    // =========================================================

    /**
     * Redireciona ao menu adequado conforme o perfil do usuário.
     * Utiliza instanceof para identificar o tipo (polimorfismo).
     *
     * @return true se o sistema deve encerrar, false para nova sessão de login
     */
    private boolean rotearPorPerfil(Usuario usuario) {
        if (usuario instanceof Administrador) {
            return new AdminView(scanner).exibir();
        } else if (usuario instanceof Gerente gerente) {
            return new GerenteView(scanner, gerente).exibir();
        } else if (usuario instanceof Colaborador colaborador) {
            return new ColaboradorView(scanner, colaborador).exibir();
        }
        return true;
    }

    // =========================================================
    // Cabeçalho visual
    // =========================================================

    private void cabecalho() {
        System.out.println();
        Util.linhaDupla();
        System.out.println("    SISTEMA DE GESTAO DE PROJETOS E EQUIPES");
        System.out.println("    Oracle  |  Solucoes Computacionais  |  v1.0");
        Util.linhaDupla();
    }
}
