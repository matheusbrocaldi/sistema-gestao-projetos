# Sistema de Gestão de Projetos e Equipes

Projeto acadêmico desenvolvido para a disciplina de **Soluções Computacionais**, atendendo à demanda da Oracle por um sistema de gestão de projetos, equipes, tarefas e relatórios de desempenho.

---

## 📋 Sobre o Projeto

Sistema desenvolvido em **Java 17** seguindo os princípios de **Programação Orientada a Objetos (POO)** e o padrão arquitetônico **MVC (Model-View-Controller)**. Permite o cadastro e gestão completa de usuários (com três perfis distintos), projetos, equipes, tarefas e a geração de relatórios de desempenho.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17 (LTS)
- **Paradigma:** Programação Orientada a Objetos
- **Arquitetura:** MVC (Model-View-Controller)
- **Build:** Maven
- **Interface:** CLI (Command Line Interface) via `Scanner`
- **Persistência:** Em memória (Coleções Java)
- **Versionamento:** Git + GitHub

---

## 📁 Estrutura do Projeto

```
gestao-projetos/
├── pom.xml
└── src/main/java/com/gestao/
    ├── Main.java                          # Ponto de entrada da aplicação
    ├── model/                             # CAMADA MODEL
    │   ├── Usuario.java                   # Classe abstrata
    │   ├── Administrador.java
    │   ├── Gerente.java
    │   ├── Colaborador.java
    │   ├── Projeto.java
    │   ├── Tarefa.java
    │   ├── Equipe.java
    │   └── enums/
    │       ├── StatusProjeto.java
    │       ├── StatusTarefa.java
    │       └── Cargo.java
    ├── controller/                        # CAMADA CONTROLLER
    │   ├── UsuarioController.java
    │   ├── ProjetoController.java
    │   ├── EquipeController.java
    │   ├── TarefaController.java
    │   └── RelatorioController.java
    ├── view/                              # CAMADA VIEW
    │   ├── MenuPrincipal.java
    │   ├── AdminView.java
    │   ├── GerenteView.java
    │   ├── ColaboradorView.java
    │   ├── UsuarioView.java
    │   ├── ProjetoView.java
    │   ├── EquipeView.java
    │   ├── TarefaView.java
    │   └── RelatorioView.java
    ├── repository/
    │   └── Repositorio.java               # Singleton com dados em memória
    └── util/
        └── Util.java                      # Utilitários de entrada do usuário
```

---

## 🎯 Funcionalidades Implementadas

### 1. Cadastro de Usuários
- Nome completo, CPF, e-mail, login, senha
- Três perfis disponíveis: **Administrador**, **Gerente** e **Colaborador**
- Colaboradores possuem cargo: Desenvolvedor, Analista de Sistemas ou Designer de Interface

### 2. Cadastro de Projetos
- Nome, descrição, datas de início e término previsto
- Status: Planejado, Em Andamento, Concluído, Cancelado
- **Cada projeto possui um gerente responsável**
- Ao cadastrar um projeto, **apenas usuários com perfil Gerente são exibidos** para seleção

### 3. Cadastro de Equipes
- Nome, descrição e membros (colaboradores vinculados)
- Uma equipe pode atuar em **vários projetos** simultaneamente

### 4. Gerenciamento de Tarefas
- Distribuição de tarefas entre colaboradores
- Controle de status: Pendente, Em Andamento, Concluída
- Acompanhamento de prazos individuais

### 5. Relatórios de Desempenho
- Relatório geral consolidado de todos os projetos
- Relatório detalhado por projeto: progresso, equipes alocadas e detalhamento de tarefas

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior instalado
- (Opcional) Maven 3.6+

### Compilação manual
```bash
javac -encoding UTF-8 -d target/classes $(find src -name "*.java")
```

### Execução
```bash
java -cp target/classes com.gestao.Main
```

### Via Maven
```bash
mvn compile exec:java -Dexec.mainClass="com.gestao.Main"
```

### Via JAR executável
```bash
java -jar gestao-projetos.jar
```

---

## 🔑 Credenciais de Teste

O sistema é pré-populado com dados para facilitar a avaliação:

| Perfil | Login | Senha |
|---|---|---|
| Administrador | `admin` | `admin123` |
| Gerente | `gerente` | `ger123` |
| Gerente | `roberto` | `rob123` |
| Colaborador (Dev) | `joao` | `joao123` |
| Colaborador (Analista) | `maria` | `maria123` |
| Colaborador (Designer) | `pedro` | `pedro123` |

---

## 🧱 Aplicação dos Princípios de POO

| Pilar | Implementação |
|---|---|
| **Abstração** | Classe abstrata `Usuario` com método abstrato `exibirPerfil()` |
| **Encapsulamento** | Atributos `private` com acesso via getters/setters e validações nos setters (ex: `Projeto.setStatus()`) |
| **Herança** | `Administrador`, `Gerente` e `Colaborador` estendem `Usuario` |
| **Polimorfismo** | Sobrescrita de `exibirPerfil()` em cada subclasse; uso de referências `Usuario` para tratar qualquer perfil |

---

## 🏛️ Aplicação do Padrão MVC

- **Model:** entidades do domínio com regras de negócio encapsuladas
- **Controller:** orquestra as operações entre Model e View, valida regras
- **View:** menus interativos via terminal, separados por perfil de usuário

---

## 👥 Autores

Trabalho acadêmico — Soluções Computacionais — Oracle.

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.
