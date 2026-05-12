package com.gestao.model;

/**
 * Classe abstrata que representa um usuário do sistema.
 *
 * Aplica os princípios de POO:
 *  - Abstração: define o contrato comum para todos os perfis de usuário
 *  - Encapsulamento: atributos privados com acesso via getters/setters
 *  - Herança: subclasses (Administrador, Gerente, Colaborador) estendem esta classe
 */
public abstract class Usuario {

    // Contador estático para geração automática de IDs únicos
    private static int contadorId = 1;

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String senha;

    // =========================================================
    // Construtor
    // =========================================================

    public Usuario(String nome, String cpf, String email, String login, String senha) {
        this.id    = contadorId++;
        this.nome  = nome;
        this.cpf   = cpf;
        this.email = email;
        this.login = login;
        this.senha = senha;
    }

    // =========================================================
    // Método abstrato — polimorfismo de sobrescrita
    // =========================================================

    /**
     * Cada subclasse implementa este método retornando informações
     * específicas do seu perfil.
     */
    public abstract String exibirPerfil();

    // =========================================================
    // Comportamentos concretos
    // =========================================================

    /**
     * Verifica se as credenciais fornecidas correspondem a este usuário.
     *
     * @param login login informado
     * @param senha senha informada
     * @return true se autenticado com sucesso
     */
    public boolean autenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    // =========================================================
    // Getters e Setters
    // =========================================================

    public int getId()              { return id; }

    public String getNome()         { return nome; }
    public void setNome(String n)   { this.nome = n; }

    public String getCpf()          { return cpf; }
    public void setCpf(String c)    { this.cpf = c; }

    public String getEmail()        { return email; }
    public void setEmail(String e)  { this.email = e; }

    public String getLogin()        { return login; }
    public void setLogin(String l)  { this.login = l; }

    public String getSenha()        { return senha; }
    public void setSenha(String s)  { this.senha = s; }

    // =========================================================
    // toString
    // =========================================================

    @Override
    public String toString() {
        return String.format("[%d] %-25s | %-35s | Login: %s",
                id, nome, exibirPerfil(), login);
    }
}
