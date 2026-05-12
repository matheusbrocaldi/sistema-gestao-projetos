package com.gestao;

import com.gestao.view.MenuPrincipal;
import java.util.Scanner;

/**
 * Ponto de entrada do Sistema de Gestão de Projetos e Equipes.
 *
 * Instancia o Scanner compartilhado e inicia o MenuPrincipal.
 * O Scanner é criado aqui e passado às Views para evitar
 * múltiplas instâncias e problemas de leitura no console.
 *
 * Tecnologias: Java 17 | POO | Padrão MVC | CLI
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuPrincipal menu = new MenuPrincipal(scanner);
        menu.iniciar();
        scanner.close();
    }
}
