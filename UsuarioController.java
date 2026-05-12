package com.gestao.controller;

import com.gestao.model.*;
import com.gestao.model.enums.Cargo;
import com.gestao.repository.Repositorio;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsável por todas as operações relacionadas a Usuários.
 *
 * Segue o padrão MVC: recebe demandas da View, aciona o Model/Repositório
 * e retorna os resultados. Não interage diretamente com o console.
 */
public class UsuarioController {

    private final Repositorio repositorio;

    public UsuarioController() {
        this.repositorio = Repositorio.getInstancia();
    }

    // =========================================================
    // Autenticação
    // =========================================================

    /**
     * Autentica um usuário pelas credenciais de login e senha.
     *
     * @return Usuario autenticado, ou null se as credenciais forem inválidas.
     */
    public Usuario autenticar(String login, String senha) {
        for (Usuario u : repositorio.getUsuarios()) {
            if (u.autenticar(login, senha)) return u;
        }
        return null;
    }

    // =========================================================
    // Cadastros
    // =========================================================

    /**
     * Cadastra um novo Administrador no sistema.
     */
    public Administrador cadastrarAdministrador(String nome, String cpf, String email,
                                                String login, String senha) {
        validarLogin(login);
        Administrador a = new Administrador(nome, cpf, email, login, senha);
        repositorio.getUsuarios().add(a);
        return a;
    }

    /**
     * Cadastra um novo Gerente no sistema.
     */
    public Gerente cadastrarGerente(String nome, String cpf, String email,
                                    String login, String senha) {
        validarLogin(login);
        Gerente g = new Gerente(nome, cpf, email, login, senha);
        repositorio.getUsuarios().add(g);
        return g;
    }

    /**
     * Cadastra um novo Colaborador no sistema.
     */
    public Colaborador cadastrarColaborador(String nome, String cpf, String email,
                                            String login, String senha, Cargo cargo) {
        validarLogin(login);
        Colaborador c = new Colaborador(nome, cpf, email, login, senha, cargo);
        repositorio.getUsuarios().add(c);
        return c;
    }

    // =========================================================
    // Atualização
    // =========================================================

    /**
     * Atualiza os dados de um usuário existente.
     * Campos em branco são ignorados (mantém o valor anterior).
     */
    public void atualizar(int id, String nome, String email) {
        Usuario u = buscarPorId(id);
        if (u == null) throw new IllegalArgumentException("Usuário não encontrado.");
        if (nome  != null && !nome.isBlank())  u.setNome(nome);
        if (email != null && !email.isBlank()) u.setEmail(email);
    }

    // =========================================================
    // Remoção
    // =========================================================

    /**
     * Remove um usuário do sistema pelo ID.
     *
     * @return true se removido com sucesso
     */
    public boolean remover(int id) {
        return repositorio.getUsuarios().removeIf(u -> u.getId() == id);
    }

    // =========================================================
    // Consultas
    // =========================================================

    /** Retorna todos os usuários cadastrados. */
    public List<Usuario> listarTodos() {
        return repositorio.getUsuarios();
    }

    /** Retorna apenas os usuários com perfil Gerente. */
    public List<Gerente> listarGerentes() {
        List<Gerente> lista = new ArrayList<>();
        for (Usuario u : repositorio.getUsuarios()) {
            if (u instanceof Gerente) lista.add((Gerente) u);
        }
        return lista;
    }

    /** Retorna apenas os usuários com perfil Colaborador. */
    public List<Colaborador> listarColaboradores() {
        List<Colaborador> lista = new ArrayList<>();
        for (Usuario u : repositorio.getUsuarios()) {
            if (u instanceof Colaborador) lista.add((Colaborador) u);
        }
        return lista;
    }

    /** Busca um usuário pelo ID. */
    public Usuario buscarPorId(int id) {
        for (Usuario u : repositorio.getUsuarios()) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    // =========================================================
    // Validações internas
    // =========================================================

    /** Lança exceção se o login já estiver em uso. */
    private void validarLogin(String login) {
        for (Usuario u : repositorio.getUsuarios()) {
            if (u.getLogin().equalsIgnoreCase(login)) {
                throw new IllegalArgumentException("Login '" + login + "' já está em uso.");
            }
        }
    }
}
