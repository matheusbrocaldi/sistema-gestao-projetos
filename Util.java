package com.gestao.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Classe utilitária com métodos auxiliares para leitura segura
 * de entradas do usuário via console.
 *
 * Centraliza a lógica de validação de input para evitar
 * duplicação de código nas Views.
 */
public final class Util {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Construtor privado — impede instanciação de classe utilitária
    private Util() {}

    // =========================================================
    // Separador visual para menus
    // =========================================================

    public static void linha() {
        System.out.println("-".repeat(60));
    }

    public static void linhaDupla() {
        System.out.println("=".repeat(60));
    }

    public static void titulo(String texto) {
        linhaDupla();
        System.out.println("  " + texto);
        linhaDupla();
    }

    // =========================================================
    // Leitura de inteiro com validação
    // =========================================================

    /**
     * Solicita um número inteiro ao usuário com re-tentativa em caso de erro.
     *
     * @param scanner scanner ativo
     * @param prompt  texto exibido antes da entrada
     * @return inteiro válido digitado pelo usuário
     */
    public static int lerInteiro(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    // =========================================================
    // Leitura de data com validação de formato
    // =========================================================

    /**
     * Solicita uma data no formato dd/MM/yyyy com re-tentativa em caso de erro.
     *
     * @param scanner scanner ativo
     * @param prompt  texto exibido antes da entrada
     * @return LocalDate válida
     */
    public static LocalDate lerData(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine().trim();
            try {
                return LocalDate.parse(entrada, FMT);
            } catch (DateTimeParseException e) {
                System.out.println("  ⚠ Formato inválido. Use dd/MM/yyyy (ex: 31/12/2025).");
            }
        }
    }

    // =========================================================
    // Leitura de texto não vazio
    // =========================================================

    /**
     * Solicita um texto garantindo que não seja vazio.
     */
    public static String lerTexto(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) return entrada;
            System.out.println("  ⚠ Campo obrigatório. Por favor, preencha.");
        }
    }

    // =========================================================
    // Pausa após operação
    // =========================================================

    public static void pausar(Scanner scanner) {
        System.out.print("\n  Pressione ENTER para continuar...");
        scanner.nextLine();
    }
}
