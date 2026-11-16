# 🍔 HamburgueriaAPI

API REST desenvolvida em Java com Spring Boot para gerenciamento de produtos, pedidos e fluxo de atendimento de uma hamburgueria com serviço de delivery.

O projeto faz parte de uma atividade acadêmica de desenvolvimento de APIs RESTful, utilizando arquitetura em três camadas, persistência de dados e documentação interativa com Swagger.

## 🎯 Objetivo

Desenvolver uma API capaz de gerenciar o cardápio de uma hamburgueria e o ciclo de vida dos pedidos, aplicando conceitos de desenvolvimento backend, orientação a objetos, persistência de dados e regras de negócio.

## Tecnologias

| Tecnologia | Utilização |
|---|---|
| Java 17 | Linguagem de programação |
| Spring Boot 4.0.9 | Framework principal |
| Maven | Gerenciamento de dependências |
| Spring WebMVC | Desenvolvimento dos endpoints REST |
| Spring Data JPA | Persistência e comunicação com o banco |
| H2 Database | Banco de dados |
| Springdoc OpenAPI | Documentação da API |
| Swagger UI | Interface para testar os endpoints |

## Arquitetura

O projeto utiliza uma arquitetura dividida em três camadas:

- **Controller:** recebe as requisições HTTP, direciona as operações e retorna as respostas.
- **Service:** concentra as regras de negócio, validações e cálculos.
- **Repository:** realiza o acesso e a persistência dos dados.

Também serão utilizados DTOs para controlar os dados recebidos e enviados pela API, além de uma camada de tratamento de exceções.

## Entidades

### Produto

Representa os produtos disponíveis no cardápio.

| Atributo | Descrição |
|---|---|
| id | Identificador do produto |
| nome | Nome do produto |
| categoria | Categoria do produto |
| precoUnitario | Preço unitário |
| disponivel | Disponibilidade para venda |

### Pedido

Representa um pedido realizado por um cliente.

| Atributo | Descrição |
|---|---|
| id | Identificador do pedido |
| cliente | Nome do cliente |
| itens | Lista de itens do pedido |
| valorTotal | Valor calculado do pedido |
| status | Estado atual do pedido |

### ItemPedido

Representa cada produto e sua quantidade dentro de um pedido.

| Atributo | Descrição |
|---|---|
| id | Identificador do item |
| produto | Produto selecionado |
| quantidade | Quantidade solicitada |
| precoSubtotal | Subtotal do item |

## Endpoints previstos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/produtos` | Listar produtos disponíveis |
| POST | `/api/produtos` | Cadastrar produto |
| POST | `/api/pedidos` | Criar pedido |
| PATCH | `/api/pedidos/{id}/status` | Atualizar status do pedido |

## Regras de negócio

- O valor total do pedido deve ser calculado automaticamente pelo servidor.
- O subtotal de cada item corresponde à quantidade multiplicada pelo preço unitário do produto.
- O cliente não pode informar diretamente o valor total do pedido.
- Somente produtos disponíveis podem ser adicionados aos pedidos.
- Os pedidos devem respeitar uma sequência válida de estados.

### Fluxo de status

O pedido deve seguir obrigatoriamente esta sequência:

`RECEBIDO` → `EM_PREPARO` → `PRONTO` → `ENTREGUE`

Transições inválidas devem ser rejeitadas pela API, retornando HTTP 400.

## Documentação da API

A documentação interativa será disponibilizada pelo Swagger UI após a implementação e inicialização da aplicação.

Endereço previsto:

`http://localhost:8080/swagger-ui/index.html`

## Como executar

### Pré-requisitos

- JDK 17 ou superior.
- Maven.
- IntelliJ IDEA ou outra IDE compatível com Java.

### Executando o projeto

Clone o repositório:

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta:

```bash
cd hamburgueria-api
```

Execute a aplicação:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação será iniciada, por padrão, em:

`http://localhost:8080`

## Desenvolvimento

- [ ] Configuração inicial do Spring Boot e Maven.
- [ ] Configuração do banco de dados H2.
- [ ] Criação das entidades.
- [ ] Implementação dos repositories.
- [ ] Implementação das regras de negócio nos services.
- [ ] Implementação dos controllers.
- [ ] Criação dos DTOs.
- [ ] Tratamento global de exceções.
- [ ] Configuração do Swagger/OpenAPI.
- [ ] Criação do script `data.sql`.
- [ ] Testes dos endpoints e das regras de negócio.
- [ ] Revisão e documentação final.

---

**Projeto acadêmico — API de Pedidos de Hamburgueria**

Desenvolvido com Java e Spring Boot.
