# 🍔 Hamburgueria API

API REST desenvolvida em **Java 17** e **Spring Boot 4.0.9** para gerenciamento de categorias, produtos e pedidos de uma hamburgueria. O projeto contempla cadastro e consulta do cardápio, criação de pedidos com cálculo automático dos valores e controle do fluxo de atendimento.

> Projeto acadêmico — Projeto Integrador I  
> **Integrantes:** Miguel Souza e Francisco Garbi  
> **Repositório:** https://github.com/souzamgz/HamburgueriaAPI

---

## Sumário

- [Sobre o projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Modelo de domínio](#-modelo-de-domínio)
- [Regras de negócio](#-regras-de-negócio)
- [Executando o projeto](#-executando-o-projeto)
- [Configuração do banco de dados](#-configuração-do-banco-de-dados)
- [Documentação interativa](#-documentação-interativa)
- [Endpoints](#-endpoints)
- [Exemplos de requisição](#-exemplos-de-requisição)
- [Tratamento de erros](#-tratamento-de-erros)
- [Testes](#-testes)
- [Estrutura de pastas](#-estrutura-de-pastas)

## Sobre o projeto

A **Hamburgueria API** tem como objetivo fornecer os recursos backend necessários para organizar o cardápio e administrar pedidos de uma hamburgueria com serviço de delivery.

A aplicação disponibiliza endpoints REST para gerenciar categorias e produtos, registrar pedidos compostos por vários itens, calcular os subtotais e o valor total no servidor e atualizar o status dos pedidos de acordo com uma sequência definida.

O projeto utiliza arquitetura em camadas, persistência com Spring Data JPA e banco de dados H2. Os contratos de entrada e saída são representados por DTOs, e a documentação dos endpoints é disponibilizada com OpenAPI e Swagger UI.

## Funcionalidades

### Categorias

- Cadastrar uma categoria ou reutilizar uma categoria existente com o mesmo nome.
- Listar todas as categorias.
- Consultar uma categoria pelo ID.
- Atualizar os dados de uma categoria.
- Excluir uma categoria quando não houver produtos associados.

### Produtos

- Cadastrar produtos associados a uma categoria.
- Listar somente produtos disponíveis no cardápio.
- Consultar um produto pelo ID.
- Atualizar os dados de um produto.
- Ativar ou desativar a disponibilidade de um produto.
- Excluir produtos.

### Pedidos

- Criar pedidos com um ou mais itens.
- Consultar todos os pedidos ou um pedido específico.
- Calcular automaticamente o subtotal de cada item e o valor total.
- Impedir que produtos indisponíveis sejam adicionados a novos pedidos.
- Atualizar o status respeitando a sequência permitida.

### Recursos complementares

- Endpoint para verificar se a API está em execução.
- Validação dos dados recebidos com Jakarta Bean Validation.
- Tratamento centralizado de exceções.
- Documentação interativa com Swagger UI.
- Dados iniciais de demonstração carregados pelo `data.sql`.

## Tecnologias

| Tecnologia | Utilização |
|---|---|
| Java 17 | Linguagem de programação |
| Spring Boot 4.0.9 | Framework principal da aplicação |
| Spring WebMVC | Construção dos endpoints REST |
| Spring Data JPA | Persistência e acesso aos dados |
| Hibernate / JPA | Mapeamento objeto-relacional |
| H2 Database | Banco de dados relacional local |
| Springdoc OpenAPI 3.0.2 | Geração da documentação OpenAPI |
| Swagger UI | Interface para explorar e testar os endpoints |
| Jakarta Bean Validation | Validação dos dados de entrada |
| Lombok | Redução de código repetitivo nas classes Java |
| Maven Wrapper | Gerenciamento e execução do build Maven |

## Arquitetura

A aplicação está organizada em camadas, separando responsabilidades:

| Camada | Responsabilidade |
|---|---|
| **Controller** | Recebe as requisições HTTP, chama os serviços e retorna as respostas. |
| **Service** | Concentra as regras de negócio, validações e cálculos. |
| **Repository** | Realiza consultas e operações de persistência por meio do Spring Data JPA. |
| **Entity** | Representa as entidades persistidas no banco de dados. |
| **DTO** | Define os dados recebidos e devolvidos pela API. |
| **Exception** | Contém exceções específicas e o tratamento global de erros. |
| **Config** | Reúne configurações da aplicação, incluindo os metadados do OpenAPI. |

### Fluxo de uma requisição

```text
Cliente HTTP
    │
    ▼
Controller
    │
    ▼
Service ───────► Regras de negócio e validações
    │
    ▼
Repository
    │
    ▼
Banco de dados H2
    │
    ▼
Resposta HTTP (DTO + status)
```

## Modelo de domínio

O domínio principal é composto pelas seguintes estruturas:

| Entidade | Descrição |
|---|---|
| **Categoria** | Classifica os produtos do cardápio por nome e descrição. |
| **Produto** | Representa um item comercializado, com nome, categoria, preço e disponibilidade. |
| **Pedido** | Representa o pedido de um cliente, com itens, valor total e status. |
| **ItemPedido** | Representa um produto e a quantidade solicitada dentro de um pedido, armazenando o subtotal. |
| **StatusPedido** | Enum que representa os estados possíveis do pedido. |

### Relacionamentos

- Uma **Categoria** pode estar associada a vários **Produtos**.
- Um **Pedido** possui uma lista de **ItensPedido**.
- Cada **ItemPedido** referencia um **Produto**.

## Regras de negócio

### Cálculo dos pedidos

O cliente informa o nome e os itens do pedido. Para cada item, a aplicação localiza o produto, verifica sua disponibilidade e calcula o subtotal:

```text
subtotal = preço unitário × quantidade
valor total = soma dos subtotais dos itens
```

Os campos `precoSubtotal` e `valorTotal` são calculados no servidor. Portanto, não devem ser enviados no corpo da requisição de criação do pedido.

### Disponibilidade dos produtos

- A listagem `GET /api/produtos` retorna apenas produtos com `disponivel = true`.
- A consulta por ID permite localizar o produto independentemente de sua disponibilidade.
- Um produto indisponível não pode ser adicionado a um novo pedido.

### Fluxo de status do pedido

Todo pedido é criado com status `RECEBIDO` e deve avançar pela sequência:

```text
RECEBIDO → EM_PREPARO → PRONTO → ENTREGUE
```

A API rejeita transições que pulem etapas, repitam o status ou tentem avançar depois de `ENTREGUE`.

### Categorias

Ao cadastrar um produto, a categoria pode ser informada pelo ID de uma categoria existente ou pelo nome. Quando o nome é informado, o serviço procura uma categoria com o mesmo nome sem diferenciar maiúsculas e minúsculas; se não encontrar, cria uma nova. Uma categoria vinculada a produtos não pode ser excluída.

## Executando o projeto

### Pré-requisitos

- **JDK 17** instalado.
- Git para clonar o repositório.
- Terminal compatível com o sistema operacional.

O projeto inclui Maven Wrapper, portanto não é necessário instalar o Maven globalmente.

### 1. Clonar o repositório

```bash
git clone https://github.com/souzamgz/HamburgueriaAPI.git
```

### 2. Entrar na pasta do projeto

```bash
cd HamburgueriaAPI
```

### 3. Executar a aplicação

**Windows (PowerShell):**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

A aplicação está configurada para iniciar na porta **8081**.

- API: http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui/index.html
- Console H2: http://localhost:8081/h2-console

## Configuração do banco de dados

O projeto utiliza o banco H2 em modo arquivo, com as propriedades configuradas em `src/main/resources/application.properties`.

| Propriedade | Configuração |
|---|---|
| URL JDBC | `jdbc:h2:file:./data/HamburgueriaAPI` |
| Driver | `org.h2.Driver` |
| Usuário | `sa` |
| Senha | Vazia |
| Console web | Habilitado |
| Caminho do console | `/h2-console` |
| Porta da aplicação | `8081` |

### Acessar o H2 Console

1. Inicie a aplicação.
2. Acesse http://localhost:8081/h2-console.
3. Informe os dados abaixo:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:file:./data/HamburgueriaAPI` |
| User Name | `sa` |
| Password | Deixe em branco |

O banco é baseado em arquivo, então os dados são mantidos entre reinicializações. O arquivo `src/main/resources/data.sql` contém a criação das tabelas e registros iniciais de demonstração.

## Documentação interativa

A documentação OpenAPI é gerada pelo Springdoc. Com a aplicação em execução, acesse:

**[Abrir Swagger UI](http://localhost:8081/swagger-ui/index.html)**

A interface permite visualizar os endpoints, consultar os modelos de requisição e resposta e executar chamadas diretamente na API.

A especificação OpenAPI em JSON também pode ser acessada em:

http://localhost:8081/v3/api-docs

## Endpoints

Todas as rotas utilizam o prefixo `/api`.

### Status

| Método | Endpoint | Descrição | Sucesso |
|---|---|---|---|
| `GET` | `/api/status` | Verifica se a API está em execução. | `200 OK` |

### Categorias

| Método | Endpoint | Descrição | Sucesso |
|---|---|---|---|
| `POST` | `/api/categorias` | Cadastra uma categoria ou reutiliza uma existente com o mesmo nome. | `201 Created` |
| `GET` | `/api/categorias` | Lista todas as categorias. | `200 OK` |
| `GET` | `/api/categorias/{id}` | Busca uma categoria pelo ID. | `200 OK` |
| `PUT` | `/api/categorias/{id}` | Atualiza os dados informados da categoria. | `200 OK` |
| `DELETE` | `/api/categorias/{id}` | Exclui uma categoria sem produtos associados. | `200 OK` |

### Produtos

| Método | Endpoint | Descrição | Sucesso |
|---|---|---|---|
| `POST` | `/api/produtos` | Cadastra um produto. | `201 Created` |
| `GET` | `/api/produtos` | Lista produtos disponíveis. | `200 OK` |
| `GET` | `/api/produtos/{id}` | Busca um produto pelo ID. | `200 OK` |
| `PUT` | `/api/produtos/{id}` | Atualiza os dados informados do produto. | `200 OK` |
| `PATCH` | `/api/produtos/{id}/disponibilidade` | Ativa ou desativa a disponibilidade do produto. | `200 OK` |
| `DELETE` | `/api/produtos/{id}` | Exclui um produto. | `204 No Content` |

### Pedidos

| Método | Endpoint | Descrição | Sucesso |
|---|---|---|---|
| `POST` | `/api/pedidos` | Cria um pedido e calcula seus valores. | `201 Created` |
| `GET` | `/api/pedidos` | Lista todos os pedidos. | `200 OK` |
| `GET` | `/api/pedidos/{id}` | Busca um pedido pelo ID. | `200 OK` |
| `PATCH` | `/api/pedidos/{id}/status` | Atualiza o status para a próxima etapa permitida. | `200 OK` |

## Exemplos de requisição

Os exemplos abaixo utilizam a URL base local `http://localhost:8081`.

### Cadastrar uma categoria

`POST /api/categorias`

```json
{
  "nome": "Lanche",
  "descricao": "Hambúrgueres artesanais"
}
```

A API também aceita o ID de uma categoria existente nos fluxos em que a categoria é associada a um produto:

```json
{
  "id": 1
}
```

### Cadastrar um produto

`POST /api/produtos`

```json
{
  "nome": "X-Bacon",
  "categoria": {
    "id": 1
  },
  "precoUnitario": 55.90
}
```

Também é possível informar o nome da categoria, para que o serviço encontre ou crie a categoria:

```json
{
  "nome": "X-Salada",
  "categoria": {
    "nome": "Lanche",
    "descricao": "Hambúrgueres artesanais"
  },
  "precoUnitario": 42.90
}
```

### Alterar disponibilidade de um produto

`PATCH /api/produtos/1/disponibilidade`

```json
{
  "disponivel": false
}
```

Para reativar, envie `true`.

### Criar um pedido

`POST /api/pedidos`

```json
{
  "cliente": "João Silva",
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 2,
      "quantidade": 1
    }
  ]
}
```

A resposta contém os itens com seus subtotais, o valor total calculado e o status inicial `RECEBIDO`. Os valores são calculados com base nos preços cadastrados dos produtos.

### Atualizar o status de um pedido

`PATCH /api/pedidos/1/status`

```json
{
  "status": "EM_PREPARO"
}
```

Os valores possíveis são `RECEBIDO`, `EM_PREPARO`, `PRONTO` e `ENTREGUE`. Deve ser informado somente o próximo status válido.

## Tratamento de erros

A aplicação possui um `GlobalExceptionHandler` com `@RestControllerAdvice`, responsável por converter exceções em respostas HTTP.

| Código | Situação |
|---|---|
| `400 Bad Request` | Dados inválidos, corpo JSON inválido, produto indisponível ou transição de status não permitida. |
| `404 Not Found` | Produto, categoria ou pedido não encontrado. |
| `409 Conflict` | Tentativa de excluir categoria em uso ou conflito de integridade dos dados. |

As mensagens de erro são retornadas como texto no corpo da resposta.

## Testes

O projeto possui testes automatizados na pasta `src/test/java`, incluindo testes de carregamento do contexto e testes do serviço de pedidos.

Para executar a suíte de testes:

**Windows:**

```powershell
.\mvnw.cmd test
```

**Linux / macOS:**

```bash
./mvnw test
```

Entre os comportamentos cobertos pelos testes de `PedidoService` estão o cálculo do valor total, o bloqueio de transições inválidas, a sequência correta de status, o bloqueio de produtos indisponíveis e a reutilização de categorias com o mesmo nome.

## Estrutura de pastas

```text
src/
├── main/
│   ├── java/com/hamburgueria/
│   │   ├── config/        # Configurações, incluindo OpenAPI
│   │   ├── controller/    # Endpoints REST
│   │   ├── dto/            # Objetos de requisição e resposta
│   │   ├── entity/         # Entidades JPA e enumerações
│   │   ├── exception/      # Exceções e handler global
│   │   ├── repository/     # Interfaces Spring Data JPA
│   │   ├── service/        # Regras de negócio
│   │   └── HamburgueriaApiApplication.java
│   └── resources/
│       ├── application.properties
│       └── data.sql
└── test/java/com/hamburgueria/
    ├── HamburgueriaApiApplicationTests.java
    └── PedidoServiceTests.java
```

---

## Integrantes

- **Miguel Souza**
- **Francisco Garbi**

**Projeto Integrador I — Hamburgueria API**  
Desenvolvido com Java 17 e Spring Boot.
