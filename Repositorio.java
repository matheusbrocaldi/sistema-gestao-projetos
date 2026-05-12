package com.gestao.repository;

import com.gestao.model.*;
import com.gestao.model.enums.Cargo;
import com.gestao.model.enums.StatusTarefa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório central de dados do sistema — padrão Singleton.
 *
 * Armazena todas as entidades em memória (ArrayList) durante a execução.
 * Garante que exista apenas uma instância do repositório em toda a aplicação.
 *
 * Pré-populado com dados de exemplo para facilitar testes e demonstrações.
 */
public class Repositorio {

    // Instância única — padrão Singleton
    private static Repositorio instancia;

    private final List<Usuario> usuarios;
    private final List<Projeto> projetos;
    private final List<Tarefa>  tarefas;
    private final List<Equipe>  equipes;

    // =========================================================
    // Construtor privado — garante Singleton
    // =========================================================

    private Repositorio() {
        usuarios = new ArrayList<>();
        projetos = new ArrayList<>();
        tarefas  = new ArrayList<>();
        equipes  = new ArrayList<>();
        carregarDadosIniciais();
    }

    /**
     * Retorna a instância única do repositório (lazy initialization).
     */
    public static Repositorio getInstancia() {
        if (instancia == null) {
            instancia = new Repositorio();
        }
        return instancia;
    }

    // =========================================================
    // Dados Iniciais para demonstração
    // =========================================================

    /**
     * Pré-popula o sistema com usuários, projetos, equipes e tarefas de exemplo.
     * Isso permite que o sistema seja testado imediatamente após a execução.
     *
     * Credenciais de acesso padrão:
     *  Admin      → login: admin      / senha: admin123
     *  Gerente 1  → login: gerente    / senha: ger123
     *  Gerente 2  → login: roberto    / senha: rob123
     *  Colab 1    → login: joao       / senha: joao123
     *  Colab 2    → login: maria      / senha: maria123
     *  Colab 3    → login: pedro      / senha: pedro123
     */
    private void carregarDadosIniciais() {

        // --- Usuários ---
        Administrador admin = new Administrador(
                "Carlos Silva", "000.000.000-00", "carlos@oracle.com", "admin", "admin123");

        Gerente gerente1 = new Gerente(
                "Ana Paula Ramos", "111.111.111-11", "ana@oracle.com", "gerente", "ger123");

        Gerente gerente2 = new Gerente(
                "Roberto Lima", "222.222.222-22", "roberto@oracle.com", "roberto", "rob123");

        Colaborador colab1 = new Colaborador(
                "Joao Souza", "333.333.333-33", "joao@oracle.com", "joao", "joao123",
                Cargo.DESENVOLVEDOR);

        Colaborador colab2 = new Colaborador(
                "Maria Costa", "444.444.444-44", "maria@oracle.com", "maria", "maria123",
                Cargo.ANALISTA_SISTEMAS);

        Colaborador colab3 = new Colaborador(
                "Pedro Alves", "555.555.555-55", "pedro@oracle.com", "pedro", "pedro123",
                Cargo.DESIGNER_INTERFACE);

        usuarios.add(admin);
        usuarios.add(gerente1);
        usuarios.add(gerente2);
        usuarios.add(colab1);
        usuarios.add(colab2);
        usuarios.add(colab3);

        // --- Projetos ---
        Projeto projeto1 = new Projeto(
                "Portal do Cliente Oracle",
                "Desenvolvimento do portal de autoatendimento para clientes Oracle.",
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 6, 30),
                gerente1);
        gerente1.adicionarProjeto(projeto1);

        Projeto projeto2 = new Projeto(
                "Sistema de BI Corporativo",
                "Implementacao de dashboards analiticos e relatorios executivos.",
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 8, 31),
                gerente2);
        gerente2.adicionarProjeto(projeto2);

        projetos.add(projeto1);
        projetos.add(projeto2);

        // --- Equipes ---
        Equipe alpha = new Equipe("Equipe Alpha", "Time de desenvolvimento front-end e back-end.");
        alpha.adicionarMembro(colab1);
        alpha.adicionarMembro(colab2);
        alpha.adicionarProjeto(projeto1.getId());
        projeto1.adicionarEquipe(alpha);

        Equipe beta = new Equipe("Equipe Beta", "Time de analise de dados e design de interface.");
        beta.adicionarMembro(colab2);
        beta.adicionarMembro(colab3);
        beta.adicionarProjeto(projeto2.getId());
        projeto2.adicionarEquipe(beta);

        equipes.add(alpha);
        equipes.add(beta);

        // --- Tarefas ---
        Tarefa t1 = new Tarefa(
                "Modulo de autenticacao", "Implementar login com validacao de perfis.",
                LocalDate.of(2025, 3, 15), colab1, projeto1.getId());

        Tarefa t2 = new Tarefa(
                "Design da tela principal", "Criar mockups e prototipos da home.",
                LocalDate.of(2025, 3, 20), colab3, projeto1.getId());
        t2.setStatus(StatusTarefa.EM_ANDAMENTO);

        Tarefa t3 = new Tarefa(
                "Levantamento de indicadores", "Mapear KPIs para os dashboards de BI.",
                LocalDate.of(2025, 4, 10), colab2, projeto2.getId());

        projeto1.adicionarTarefa(t1);
        projeto1.adicionarTarefa(t2);
        projeto2.adicionarTarefa(t3);

        tarefas.add(t1);
        tarefas.add(t2);
        tarefas.add(t3);
    }

    // =========================================================
    // Acesso às coleções
    // =========================================================

    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Projeto> getProjetos() { return projetos; }
    public List<Tarefa>  getTarefas()  { return tarefas; }
    public List<Equipe>  getEquipes()  { return equipes; }
}
