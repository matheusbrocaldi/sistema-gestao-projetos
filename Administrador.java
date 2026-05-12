package com.gestao.model;

/**
 * Representa um usuário com perfil de Administrador.
 *
 * O Administrador possui acesso irrestrito a todas as funcionalidades
 * do sistema: cadastrar, editar e remover usuários, projetos, equipes e tarefas.
 *
 * Demonstra o princípio de Herança — estende Usuario e implementa exibirPerfil().
 */
public class Administrador extends Usuario {

    public Administrador(String nome, String cpf, String email, String login, String senha) {
        super(nome, cpf, email, login, senha);
    }

    /**
     * Implementação do método abstrato — Polimorfismo de sobrescrita.
     */
    @Override
    public String exibirPerfil() {
        return "Perfil: Administrador";
    }
}
