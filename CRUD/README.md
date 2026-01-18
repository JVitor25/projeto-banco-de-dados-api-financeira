# Sistema de Controle Financeiro - API REST

Este projeto é uma API REST desenvolvida com **Spring Boot** para gerenciamento financeiro pessoal. O sistema permite o controle de usuários, contas bancárias, cartões de crédito, faturas, lançamentos e transferências.

## 🚀 Tecnologias Utilizadas

*   **Java 21**
*   **Spring Boot 4.0.1**
*   **Spring Data JPA** (Hibernate)
*   **MySQL 8.0** (Banco de Dados)
*   **Docker & Docker Compose** (Containerização)
*   **SpringDoc OpenAPI** (Swagger UI para documentação)
*   **Lombok**

## 🛠️ Pré-requisitos

*   Java JDK 21 instalado.
*   Maven instalado.
*   Docker e Docker Compose instalados.

## 📦 Como Rodar o Projeto

### 1. Subir o Banco de Dados
O projeto utiliza o Docker para rodar o MySQL e o PhpMyAdmin. Execute o comando abaixo na raiz do projeto:

```bash
docker-compose up -d
```

Isso irá iniciar:
*   **MySQL** na porta `3307` (mapeada para a 3306 do container).
*   **PhpMyAdmin** na porta `8081`.

### 2. Executar a Aplicação
Com o banco de dados rodando, inicie a aplicação Spring Boot:

```bash
./mvnw spring-boot:run
```
Ou execute a classe `DemoApplication.java` diretamente pela sua IDE (IntelliJ/Eclipse).

## 📄 Documentação da API (Swagger)

A documentação interativa da API é gerada automaticamente pelo Swagger. Após iniciar a aplicação, acesse:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

Lá você poderá testar todos os endpoints diretamente pelo navegador.

## 🗄️ Acesso ao Banco de Dados (PhpMyAdmin)

Para visualizar as tabelas e dados diretamente, acesse o PhpMyAdmin:

*   **URL:** [http://localhost:8081](http://localhost:8081)
*   **Servidor:** `mysqldb`
*   **Usuário:** `user_financas`
*   **Senha:** `IFMGadmin123`

## endpoints Principais

A API fornece operações CRUD para os seguintes recursos:

*   `/api/usuarios` - Gerenciamento de Usuários
*   `/api/grupos` - Gerenciamento de Grupos
*   `/api/contas` - Contas Bancárias
*   `/api/cartoes-credito` - Cartões de Crédito
*   `/api/faturas` - Faturas de Cartão
*   `/api/lancamentos` - Receitas e Despesas
*   `/api/transferencias` - Transferências entre contas

## ⚙️ Configuração

O arquivo de configuração principal está em `src/main/resources/application.yaml`.
A conexão com o banco está configurada para:
*   **URL:** `jdbc:mysql://localhost:3307/db_financas`
*   **Usuário:** `user_financas`
*   **Senha:** `IFMGadmin123`
