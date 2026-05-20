# API REST de Produtos - Spring Boot

## Descrição
API REST desenvolvida com Spring Boot 2.7.14 e Java 8 para gerenciar um CRUD completo de produtos, seguindo o padrão arquitetural MVC.

## Requisitos
- Java 8 ou superior
- Maven 3.6.0 ou superior
- Windows 10/11 (ou outro SO compatível)

## Estrutura do Projeto

```
vscode_Springboot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/demo/
│   │   │       ├── Application.java           # Classe principal
│   │   │       ├── controller/
│   │   │       │   └── ProdutoController.java # REST Controller
│   │   │       ├── service/
│   │   │       │   └── ProdutoService.java    # Lógica de negócio
│   │   │       ├── model/
│   │   │       │   └── Produto.java           # Entidade JPA
│   │   │       ├── repository/
│   │   │       │   └── ProdutoRepository.java # Acesso a dados
│   │   │       └── dto/
│   │   │           └── ProdutoDTO.java        # Transfer Object
│   │   └── resources/
│   │       └── application.properties          # Configurações
│   └── test/                                   # Testes unitários
├── pom.xml                                     # Dependências Maven
└── README.md                                   # Este arquivo
```

## Configurações

- **Porta**: 8088
- **Banco de Dados**: H2 (em memória)
- **JPA Hibernate**: Auto-create schema
- **Contexto**: /

## Dependências Principais

- Spring Boot Web
- Spring Data JPA
- H2 Database
- Lombok
- JUnit 5

## Como Executar

### 1. Clone ou acesse a pasta do projeto
```powershell
cd c:\Users\HOME\Downloads\vscode_Springboot
```

### 2. Compile o projeto
```powershell
mvn clean compile
```

### 3. Execute a aplicação
```powershell
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8088`

## Endpoints da API

### 1. Listar Todos os Produtos
```
GET /api/produtos
```
Retorna todos os produtos cadastrados.

### 2. Listar Produtos Ativos
```
GET /api/produtos/ativos
```
Retorna apenas produtos com status ativo.

### 3. Buscar Produto por ID
```
GET /api/produtos/{id}
```
Exemplo: `GET /api/produtos/1`

### 4. Buscar Produto por SKU
```
GET /api/produtos/sku/{sku}
```
Exemplo: `GET /api/produtos/sku/SKU123`

### 5. Buscar Produtos por Nome
```
GET /api/produtos/buscar?nome=notebook
```
Retorna produtos que contenham o nome especificado.

### 6. Criar Novo Produto
```
POST /api/produtos
Content-Type: application/json

{
  "nome": "Notebook Dell",
  "descricao": "Notebook com processador i7",
  "preco": 3500.00,
  "quantidade": 10,
  "sku": "NOTEBOOK-DELL-001",
  "ativo": true
}
```

### 7. Atualizar Produto
```
PUT /api/produtos/{id}
Content-Type: application/json

{
  "nome": "Notebook Dell Atualizado",
  "descricao": "Notebook com processador i7",
  "preco": 3200.00,
  "quantidade": 15,
  "sku": "NOTEBOOK-DELL-001",
  "ativo": true
}
```

### 8. Desativar Produto
```
PATCH /api/produtos/{id}/desativar
```

### 9. Ativar Produto
```
PATCH /api/produtos/{id}/ativar
```

### 10. Deletar Produto
```
DELETE /api/produtos/{id}
```

### 11. Health Check
```
GET /api/produtos/health
```
Verifica se a API está rodando.

## Exemplo de Requisição (cURL)

### Criar um produto
```bash
curl -X POST http://localhost:8088/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Mouse Logitech",
    "descricao": "Mouse sem fio",
    "preco": 85.50,
    "quantidade": 50,
    "sku": "MOUSE-LGT-001",
    "ativo": true
  }'
```

### Listar todos os produtos
```bash
curl http://localhost:8088/api/produtos
```

### Buscar por ID
```bash
curl http://localhost:8088/api/produtos/1
```

### Atualizar produto
```bash
curl -X PUT http://localhost:8088/api/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Mouse Logitech Pro",
    "descricao": "Mouse sem fio profissional",
    "preco": 120.00,
    "quantidade": 30,
    "sku": "MOUSE-LGT-001",
    "ativo": true
  }'
```

### Deletar produto
```bash
curl -X DELETE http://localhost:8088/api/produtos/1
```

## Validações Implementadas

- **Nome**: Obrigatório e não pode ser vazio
- **Preço**: Obrigatório e deve ser maior que zero
- **Quantidade**: Não pode ser negativa
- **SKU**: Deve ser único
- **Data de Criação**: Preenchida automaticamente
- **Data de Atualização**: Atualizada automaticamente

## Modelo de Dados - Produto

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | Long | ID único (PK) |
| nome | String | Nome do produto (obrigatório) |
| descricao | String | Descrição detalhada |
| preco | BigDecimal | Preço do produto |
| quantidade | Integer | Quantidade em estoque |
| sku | String | Código único de identificação |
| ativo | Boolean | Status do produto |
| dataCriacao | LocalDateTime | Data de criação |
| dataAtualizacao | LocalDateTime | Data da última atualização |

## Tratamento de Erros

A API retorna os seguintes status HTTP:

- **200 OK**: Requisição bem-sucedida
- **201 CREATED**: Recurso criado com sucesso
- **400 BAD REQUEST**: Erro de validação
- **404 NOT FOUND**: Recurso não encontrado
- **500 INTERNAL SERVER ERROR**: Erro no servidor

Exemplo de resposta de erro:
```json
{
  "mensagem": "Produto não encontrado com ID: 999"
}
```

## H2 Console (Debug)

Para acessar o console H2 e visualizar/gerenciar o banco de dados:

1. Com a aplicação rodando, acesse: `http://localhost:8088/h2-console`
2. Dados de conexão:
   - **JDBC URL**: jdbc:h2:mem:produtosdb
   - **User Name**: sa
   - **Password**: (deixar em branco)

## Tecnologias Utilizadas

- **Java 8**: Linguagem de programação
- **Spring Boot 2.7.14**: Framework web
- **Spring Data JPA**: ORM
- **Hibernate**: JPA Implementation
- **H2 Database**: Banco de dados em memória
- **Lombok**: Redução de boilerplate
- **Maven**: Gerenciador de dependências

## Padrões de Projeto

1. **MVC**: Model-View-Controller
   - Model: Classe Produto
   - Service: Lógica de negócio
   - Controller: Endpoints REST

2. **DTO Pattern**: Transfer Object para comunicação
3. **Repository Pattern**: Acesso a dados abstrato
4. **Dependency Injection**: Injeção de dependências do Spring

## Desenvolvedor

Projeto demo criado com Spring Boot Java 8.

## Licença

MIT License
