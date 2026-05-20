# Guia de Uso da API de Produtos

## Teste com Postman

Você pode importar esta coleção no Postman ou executar manualmente.

### Base URL
```
http://localhost:8088/api/produtos
```

## 1. HEALTH CHECK

**Método**: GET  
**URL**: `http://localhost:8088/api/produtos/health`

**Resposta esperada (200)**:
```json
{
  "status": "UP",
  "mensagem": "API de Produtos está rodando"
}
```

---

## 2. LISTAR TODOS OS PRODUTOS

**Método**: GET  
**URL**: `http://localhost:8088/api/produtos`

**Resposta esperada (200)**:
```json
[
  {
    "id": 1,
    "nome": "Notebook Dell",
    "descricao": "Notebook com processador Intel i7",
    "preco": 3500.00,
    "quantidade": 5,
    "sku": "NOTEBOOK-DELL-001",
    "ativo": true
  },
  {
    "id": 2,
    "nome": "Mouse Logitech",
    "descricao": "Mouse sem fio 2.4GHz",
    "preco": 85.50,
    "quantidade": 20,
    "sku": "MOUSE-LOGI-001",
    "ativo": true
  }
]
```

---

## 3. LISTAR APENAS PRODUTOS ATIVOS

**Método**: GET  
**URL**: `http://localhost:8088/api/produtos/ativos`

**Resposta esperada (200)**:
```json
[
  {
    "id": 1,
    "nome": "Notebook Dell",
    "descricao": "Notebook com processador Intel i7",
    "preco": 3500.00,
    "quantidade": 5,
    "sku": "NOTEBOOK-DELL-001",
    "ativo": true
  }
]
```

---

## 4. BUSCAR PRODUTO POR ID

**Método**: GET  
**URL**: `http://localhost:8088/api/produtos/1`

**Resposta esperada (200)**:
```json
{
  "id": 1,
  "nome": "Notebook Dell",
  "descricao": "Notebook com processador Intel i7",
  "preco": 3500.00,
  "quantidade": 5,
  "sku": "NOTEBOOK-DELL-001",
  "ativo": true
}
```

**Resposta esperada (404 - Produto não encontrado)**:
```json
{
  "mensagem": "Produto não encontrado com ID: 999"
}
```

---

## 5. BUSCAR PRODUTO POR SKU

**Método**: GET  
**URL**: `http://localhost:8088/api/produtos/sku/NOTEBOOK-DELL-001`

**Resposta esperada (200)**:
```json
{
  "id": 1,
  "nome": "Notebook Dell",
  "descricao": "Notebook com processador Intel i7",
  "preco": 3500.00,
  "quantidade": 5,
  "sku": "NOTEBOOK-DELL-001",
  "ativo": true
}
```

---

## 6. BUSCAR PRODUTOS POR NOME

**Método**: GET  
**URL**: `http://localhost:8088/api/produtos/buscar?nome=notebook`

**Resposta esperada (200)**:
```json
[
  {
    "id": 1,
    "nome": "Notebook Dell",
    "descricao": "Notebook com processador Intel i7",
    "preco": 3500.00,
    "quantidade": 5,
    "sku": "NOTEBOOK-DELL-001",
    "ativo": true
  }
]
```

---

## 7. CRIAR NOVO PRODUTO

**Método**: POST  
**URL**: `http://localhost:8088/api/produtos`  
**Content-Type**: `application/json`

**Body**:
```json
{
  "nome": "Monitor Samsung 27",
  "descricao": "Monitor IPS 27 polegadas 4K",
  "preco": 1200.00,
  "quantidade": 7,
  "sku": "MONITOR-SAMSUNG-27",
  "ativo": true
}
```

**Resposta esperada (201)**:
```json
{
  "id": 9,
  "nome": "Monitor Samsung 27",
  "descricao": "Monitor IPS 27 polegadas 4K",
  "preco": 1200.00,
  "quantidade": 7,
  "sku": "MONITOR-SAMSUNG-27",
  "ativo": true
}
```

**Resposta esperada (400 - SKU duplicado)**:
```json
{
  "mensagem": "Já existe um produto com esse SKU: MONITOR-SAMSUNG-27"
}
```

**Resposta esperada (400 - Nome vazio)**:
```json
{
  "mensagem": "Nome do produto é obrigatório"
}
```

**Resposta esperada (400 - Preço inválido)**:
```json
{
  "mensagem": "Preço deve ser maior que zero"
}
```

---

## 8. ATUALIZAR PRODUTO

**Método**: PUT  
**URL**: `http://localhost:8088/api/produtos/1`  
**Content-Type**: `application/json`

**Body**:
```json
{
  "nome": "Notebook Dell Atualizado",
  "descricao": "Notebook com processador Intel i9",
  "preco": 4500.00,
  "quantidade": 3,
  "sku": "NOTEBOOK-DELL-001",
  "ativo": true
}
```

**Resposta esperada (200)**:
```json
{
  "id": 1,
  "nome": "Notebook Dell Atualizado",
  "descricao": "Notebook com processador Intel i9",
  "preco": 4500.00,
  "quantidade": 3,
  "sku": "NOTEBOOK-DELL-001",
  "ativo": true
}
```

**Resposta esperada (404 - Produto não encontrado)**:
```json
{
  "mensagem": "Produto não encontrado com ID: 999"
}
```

---

## 9. DESATIVAR PRODUTO

**Método**: PATCH  
**URL**: `http://localhost:8088/api/produtos/1/desativar`

**Resposta esperada (200)**:
```json
{
  "id": 1,
  "nome": "Notebook Dell",
  "descricao": "Notebook com processador Intel i7",
  "preco": 3500.00,
  "quantidade": 5,
  "sku": "NOTEBOOK-DELL-001",
  "ativo": false
}
```

---

## 10. ATIVAR PRODUTO

**Método**: PATCH  
**URL**: `http://localhost:8088/api/produtos/8/ativar`

**Resposta esperada (200)**:
```json
{
  "id": 8,
  "nome": "RAM DDR4 16GB",
  "descricao": "Memória RAM DDR4 16GB 3200MHz",
  "preco": 280.00,
  "quantidade": 25,
  "sku": "RAM-DDR4-16GB",
  "ativo": true
}
```

---

## 11. DELETAR PRODUTO

**Método**: DELETE  
**URL**: `http://localhost:8088/api/produtos/1`

**Resposta esperada (200)**:
```json
{
  "mensagem": "Produto deletado com sucesso"
}
```

**Resposta esperada (404 - Produto não encontrado)**:
```json
{
  "mensagem": "Produto não encontrado com ID: 999"
}
```

---

## Roteiro de Teste Completo

1. **Health Check** - Verificar se API está rodando
2. **Listar Todos** - Ver os produtos iniciais
3. **Buscar por ID** - Selecionar um produto específico
4. **Criar** - Adicionar um novo produto
5. **Atualizar** - Modificar dados do produto criado
6. **Listar Ativos** - Verificar apenas produtos ativos
7. **Desativar** - Inativar um produto
8. **Ativar** - Reativar um produto
9. **Buscar por Nome** - Procurar produtos por texto
10. **Deletar** - Remover um produto

---

## Códigos de Status HTTP

| Código | Significado |
|--------|------------|
| 200 | OK - Requisição bem-sucedida |
| 201 | CREATED - Recurso criado com sucesso |
| 400 | BAD REQUEST - Erro de validação nos dados |
| 404 | NOT FOUND - Recurso não encontrado |
| 500 | INTERNAL SERVER ERROR - Erro no servidor |

---

## Dicas para Testes

1. **Com cURL**:
```bash
curl -X GET http://localhost:8088/api/produtos
curl -X GET http://localhost:8088/api/produtos/1
curl -X POST http://localhost:8088/api/produtos -H "Content-Type: application/json" -d '{"nome":"Produto","preco":100,"quantidade":10,"sku":"SKU123"}'
```

2. **Com PowerShell**:
```powershell
$uri = "http://localhost:8088/api/produtos"
$body = @{
    nome = "Novo Produto"
    descricao = "Descrição"
    preco = 100
    quantidade = 10
    sku = "SKU123"
    ativo = $true
} | ConvertTo-Json

Invoke-RestMethod -Uri $uri -Method POST -ContentType "application/json" -Body $body
```

3. **Com Postman**:
   - Criar uma nova coleção
   - Importar estes exemplos
   - Usar variáveis de ambiente para a base URL
   - Testar cada endpoint sequencialmente

---

## Resolução de Problemas

### Erro: Connection refused
- Verifique se a aplicação está rodando: `mvn spring-boot:run`
- Confirme que está na porta correta: 8088

### Erro: SKU duplicado
- Use um SKU único para criar novos produtos
- Ou atualize um produto existente sem mudar o SKU

### Erro: Produto não encontrado (404)
- Verifique se o ID existe com `GET /api/produtos`
- Confirme que está usando o ID correto na URL

### Erro: Preço inválido
- Preço deve ser um número maior que zero
- Use formato decimal: "3500.00" (não "3500")

---

## Documentação Técnica

Para mais detalhes sobre a arquitetura e implementação, consulte o arquivo `README.md`.
