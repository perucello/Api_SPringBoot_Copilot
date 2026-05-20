# Diagrama de Arquitetura - API de Produtos

## Arquitetura MVC

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT (Navegador/Postman/cURL)            │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                    HTTP Requests/Responses
                           │
        ┌──────────────────▼──────────────────┐
        │     REST Controller Layer            │
        │   (ProdutoController)                │
        │                                      │
        │  - GET /api/produtos                │
        │  - GET /api/produtos/{id}           │
        │  - POST /api/produtos               │
        │  - PUT /api/produtos/{id}           │
        │  - DELETE /api/produtos/{id}        │
        │  - PATCH /api/produtos/{id}/...     │
        └──────────────┬───────────────────────┘
                       │
            ┌──────────▼──────────┐
            │  Request Validation  │
            │   & Error Handling   │
            └──────────┬───────────┘
                       │
        ┌──────────────▼──────────────────┐
        │    Business Logic Layer          │
        │     (ProdutoService)             │
        │                                  │
        │  - listarTodos()                │
        │  - listarAtivos()               │
        │  - buscarPorId(id)              │
        │  - criar(produtoDTO)            │
        │  - atualizar(id, produtoDTO)    │
        │  - deletar(id)                  │
        │  - desativar(id)                │
        │  - ativar(id)                   │
        │  - validarProduto()             │
        │  - converterParaDTO()           │
        └──────────────┬───────────────────┘
                       │
        ┌──────────────▼──────────────────┐
        │  Data Access Layer               │
        │ (ProdutoRepository/JpaRepository)│
        │                                  │
        │  - findById(id)                 │
        │  - findAll()                    │
        │  - findBySku(sku)               │
        │  - findByAtivoTrue()            │
        │  - buscarPorNome(nome)          │
        │  - save(produto)                │
        │  - delete(produto)              │
        └──────────────┬───────────────────┘
                       │
        ┌──────────────▼──────────────────┐
        │    Data Model Layer              │
        │                                  │
        │  ┌─────────────────────────┐    │
        │  │    Produto Entity        │    │
        │  │  @Entity                 │    │
        │  │  @Table(tb_produtos)     │    │
        │  │                          │    │
        │  │  - id (PK)              │    │
        │  │  - nome                 │    │
        │  │  - descricao            │    │
        │  │  - preco                │    │
        │  │  - quantidade           │    │
        │  │  - sku                  │    │
        │  │  - ativo                │    │
        │  │  - dataCriacao          │    │
        │  │  - dataAtualizacao      │    │
        │  └─────────────────────────┘    │
        │                                  │
        │  ┌─────────────────────────┐    │
        │  │  ProdutoDTO              │    │
        │  │ (Transfer Object)        │    │
        │  │                          │    │
        │  │ - Mesmos campos que      │    │
        │  │   a Entity (sem JPA)     │    │
        │  └─────────────────────────┘    │
        └──────────────┬───────────────────┘
                       │
        ┌──────────────▼──────────────────┐
        │     Database Layer               │
        │                                  │
        │     H2 Database (Em memória)    │
        │     jdbc:h2:mem:produtosdb      │
        │                                  │
        │  ┌──────────────────────────┐   │
        │  │   tb_produtos (Table)    │   │
        │  │                          │   │
        │  │  id         (BIGINT PK)  │   │
        │  │  nome       (VARCHAR)    │   │
        │  │  descricao  (VARCHAR)    │   │
        │  │  preco      (DECIMAL)    │   │
        │  │  quantidade (INT)        │   │
        │  │  sku        (VARCHAR UK) │   │
        │  │  ativo      (BOOLEAN)    │   │
        │  │  data_criacao (TIMESTAMP)│   │
        │  │  data_atualizacao (TIME) │   │
        │  └──────────────────────────┘   │
        └──────────────────────────────────┘
```

## Padrão MVC Detalhado

### Model (Camada de Dados)
```
┌─────────────────────────────────────┐
│  Produto (Entity/Model)             │
│  - Representa a tabela do BD        │
│  - Anotações JPA (@Entity, etc)     │
│  - Relacionamentos com BD           │
└─────────────────────────────────────┘
```

### View (Camada de Apresentação)
```
┌─────────────────────────────────────┐
│  ProdutoDTO                         │
│  - Transferência de dados           │
│  - JSON para requisições HTTP       │
│  - Isolamento da API da Entity      │
└─────────────────────────────────────┘
```

### Controller (Camada de Controle)
```
┌─────────────────────────────────────┐
│  ProdutoController                  │
│  - Recebe requisições HTTP          │
│  - Processa requests/responses      │
│  - Delega para Service              │
│  - Retorna respostas HTTP           │
└─────────────────────────────────────┘
```

## Fluxo de Requisição - GET /api/produtos/1

```
1. Cliente envia: GET /api/produtos/1
                          │
                          ▼
2. ProdutoController.buscarPorId(1)
   - Extrai parametro ID
   - Valida entrada
                          │
                          ▼
3. ProdutoService.buscarPorId(1)
   - Busca a lógica de negócio
   - Valida permissões (opcional)
   - Converte de Entity para DTO
                          │
                          ▼
4. ProdutoRepository.findById(1)
   - Interface JPA
   - Gera SQL automaticamente
                          │
                          ▼
5. H2 Database
   - SELECT * FROM tb_produtos WHERE id = 1
   - Retorna registro
                          │
                          ▼
6. ProdutoService converte para DTO
                          │
                          ▼
7. ProdutoController retorna ResponseEntity
                          │
                          ▼
8. Jackson serializa para JSON
                          │
                          ▼
9. Cliente recebe: 
   {
     "id": 1,
     "nome": "Notebook",
     ...
   }
```

## Fluxo de Requisição - POST /api/produtos

```
1. Cliente envia: POST /api/produtos
   Body: { "nome": "Mouse", ... }
                          │
                          ▼
2. Jackson desserializa JSON para ProdutoDTO
                          │
                          ▼
3. ProdutoController.criar(produtoDTO)
   - Recebe DTO
                          │
                          ▼
4. ProdutoService.criar(produtoDTO)
   - Valida dados (nome, preço, etc)
   - Converte DTO para Entity
   - Verifica SKU duplicado
                          │
                          ▼
5. ProdutoRepository.save(produto)
   - Salva no banco (INSERT)
                          │
                          ▼
6. H2 Database
   - INSERT INTO tb_produtos (...)
   - Retorna ID gerado (auto-increment)
                          │
                          ▼
7. ProdutoService converte para DTO
                          │
                          ▼
8. ProdutoController retorna ResponseEntity(201)
                          │
                          ▼
9. Cliente recebe: 
   { "id": 9, "nome": "Mouse", ... }
   Status: 201 CREATED
```

## Dependências de Classes

```
┌──────────────────────────────────────────┐
│ Application                              │
│ (Inicializa Spring Boot)                 │
└──────────────┬───────────────────────────┘
               │
       ┌───────▼────────┐
       │                │
┌──────▼──────┐   ┌─────▼──────────┐
│ Controller  │   │  Service       │
└──────┬──────┘   └─────┬──────────┘
       │                │
       └────────┬───────┘
                │
        ┌───────▼────────┐
        │ Repository     │
        └────────┬───────┘
                 │
        ┌────────▼──────┐
        │ Model/Entity  │
        └───────────────┘
```

## Tecnologias e Componentes

```
┌─────────────────────────────────────┐
│     Spring Boot 2.7.14              │
│  ┌───────────────────────────────┐  │
│  │  Spring Web (REST API)        │  │
│  │  Spring Data JPA (ORM)        │  │
│  │  Hibernate (JPA Provider)     │  │
│  │  Jackson (JSON Serialization) │  │
│  │  Tomcat (Servlet Container)   │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
                  │
         ┌────────▼────────┐
         │  H2 Database    │
         │  (In-Memory)    │
         └─────────────────┘
```

## Padrões de Projeto Utilizados

1. **MVC Pattern**: Separação em Model, View, Controller
2. **Repository Pattern**: Abstração de acesso a dados
3. **DTO Pattern**: Transfer Object para API
4. **Service Layer**: Lógica de negócio centralizada
5. **Dependency Injection**: Spring IoC Container
6. **Bean Lifecycle**: Managed by Spring

## Segurança (Escalável para Futuro)

```
Current Implementation:
├── Validação de Entrada
├── Tratamento de Exceções
├── CORS Habilitado
└── Transações gerenciadas

Future Enhancements:
├── Spring Security
├── JWT Authentication
├── OAuth2
├── API Key Validation
├── Rate Limiting
└── Logging detalhado
```

---

**Versão**: 1.0.0  
**Data**: Dezembro 2025  
**Compatibilidade**: Java 8+
