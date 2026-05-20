# Postman Environment - Produto API

## Visão Geral

O arquivo `postman_environment.json` contém todas as variáveis necessárias para usar a coleção Postman **"Produto API"** de forma mais dinâmica e flexível.

---

## Como Importar no Postman

### Método 1: Via Interface Gráfica

1. Abra o **Postman**
2. Clique em **"Environments"** (lado esquerdo) ou **"Import"** (canto superior)
3. Selecione **"File"** → escolha `postman_environment.json`
4. Clique em **"Import"**

### Método 2: Via URL (se o arquivo estiver em um servidor)

```
Importar a partir de uma URL: https://seu-servidor.com/postman_environment.json
```

---

## Variáveis Disponíveis

### Conectividade

| Variável | Valor Padrão | Descrição |
|----------|---|---|
| `baseUrl` | `http://localhost:8088` | URL base da API (host + porta) |
| `apiPath` | `/api/produtos` | Caminho raiz dos endpoints |

### Headers HTTP

| Variável | Valor Padrão | Descrição |
|----------|---|---|
| `contentType` | `application/json` | Header Content-Type para requisições POST/PUT |
| `accept` | `application/json` | Header Accept para requisições GET |

### Busca e Filtros

| Variável | Valor Padrão | Descrição |
|----------|---|---|
| `productId` | `1` | ID do produto para operações de busca/atualização/deleção |
| `productSku` | `ARZ-001` | SKU do produto para busca específica |
| `productName` | `Arroz` | Nome do produto para busca por texto |

### Criação de Novo Produto

| Variável | Valor Padrão | Descrição |
|----------|---|---|
| `newProductName` | `Novo Produto` | Nome do novo produto a ser criado |
| `newProductSku` | `NEW-PROD-001` | SKU único do novo produto |
| `newProductPrice` | `29.90` | Preço do novo produto (formato: 0.00) |
| `newProductQuantity` | `10` | Quantidade em estoque |
| `newProductDescription` | `Descrição do novo produto` | Descrição detalhada |

---

## Como Usar as Variáveis

### Exemplo 1: Buscar Produto por ID

1. Abra a requisição **"Buscar produto por ID"**
2. Note que a URL está como: `{{baseUrl}}{{apiPath}}/{{productId}}`
3. O Postman substituirá:
   - `{{baseUrl}}` → `http://localhost:8088`
   - `{{apiPath}}` → `/api/produtos`
   - `{{productId}}` → `1`
4. **URL final:** `http://localhost:8088/api/produtos/1`

### Exemplo 2: Criar Novo Produto

1. Abra a requisição **"Criar produto"**
2. O body do JSON usa variáveis:
   ```json
   {
     "nome": "{{newProductName}}",
     "descricao": "{{newProductDescription}}",
     "preco": {{newProductPrice}},
     "quantidade": {{newProductQuantity}},
     "sku": "{{newProductSku}}",
     "ativo": true
   }
   ```
3. Postman substitui as variáveis pelos valores do environment
4. Altere os valores no environment se quiser testar com diferentes dados

---

## Modificar Variáveis

### Temporariamente (apenas durante a sessão)

1. Clique no **"eye"** (ícone de olho) no canto superior direito
2. Selecione **"Produto API - Environment"**
3. Clique no ambiente para expandir
4. Altere os valores desejados
5. Feche a janela (as alterações serão aplicadas apenas para a sessão atual)

### Permanentemente (salve no arquivo)

1. Altere a variável conforme acima
2. Clique em **"Save"** (ou Ctrl+S)
3. O arquivo será atualizado

---

## Cenários de Uso

### Cenário 1: Testar com Múltiplos Ambientes

Crie múltiplos environments para diferentes ambientes:

**Produção:**
```json
"baseUrl": "https://api.producao.com.br"
```

**Desenvolvimento:**
```json
"baseUrl": "http://localhost:8088"
```

**Staging:**
```json
"baseUrl": "https://api.staging.com.br"
```

Alterne entre eles no Postman e execute os mesmos testes em diferentes ambientes.

### Cenário 2: Criar Múltiplos Produtos para Testes

1. Altere `newProductSku` para um valor único (ex: `PROD-002`, `PROD-003`)
2. Altere `newProductName` conforme necessário
3. Execute a requisição **"Criar produto"** múltiplas vezes
4. Cada execução criará um novo produto com dados diferentes

### Cenário 3: Atualizar um Produto Específico

1. Defina `productId` como o ID do produto que deseja atualizar
2. Altere `newProductName`, `newProductPrice`, etc. com os novos valores
3. Execute a requisição **"Atualizar produto (PUT)"**

---

## Automação com Scripts (Pre-request e Tests)

### Pre-request Script (executado antes da requisição)

Você pode adicionar um script no Postman para definir variáveis dinamicamente:

```javascript
// Gerar um SKU único com timestamp
const timestamp = Date.now();
pm.environment.set("newProductSku", `PROD-${timestamp}`);

// Ou logar a URL que será chamada
console.log("URL será: " + pm.environment.get("baseUrl") + pm.environment.get("apiPath"));
```

### Test Script (executado após a resposta)

```javascript
// Validar status code
pm.test("Status code é 200", function () {
    pm.response.to.have.status(200);
});

// Salvar ID da resposta em uma variável
pm.test("Salvar ID do produto criado", function () {
    const responseJson = pm.response.json();
    pm.environment.set("productId", responseJson.id);
});
```

---

## Dicas Importantes

1. **Sensibilidade:** As variáveis são **case-sensitive**. Use `{{baseUrl}}` e não `{{BaseUrl}}`

2. **Scope:** Variáveis de environment têm escopo global para a coleção

3. **Valores não definidos:** Se uma variável não está definida, o Postman mostrará `{{variavel}}` literal na URL/body

4. **Debug:** Use **"Postman Console"** (Ctrl+Alt+C) para ver os valores das variáveis durante execução

5. **Backup:** Faça backup do `postman_environment.json` se for usá-lo em múltiplos projetos

---

## Exemplo Completo de Workflow

1. **Importar** `postman_environment.json`
2. **Selecionar** o environment "Produto API - Environment" (canto superior direito)
3. **Executar** "Listar todos os produtos"
4. **Modificar** `newProductName`, `newProductSku`, `newProductPrice`
5. **Executar** "Criar produto"
6. **Copiar** o ID da resposta e colocar em `productId`
7. **Executar** "Buscar produto por ID" (agora com o novo ID)
8. **Executar** "Atualizar produto" com novos dados
9. **Executar** "Deletar produto" para limpar

---

## Troubleshooting

| Problema | Solução |
|----------|---------|
| "Ambiente não carregado" | Certifique-se de que importou corretamente e selecionou o environment no canto superior |
| "Variáveis aparecem como literal" | Verifique se o environment está selecionado (deve estar em destaque) |
| "URL incorreta" | Verifique se `baseUrl` começa com `http://` ou `https://` |
| "Header Content-Type não reconhecido" | Verifique se `contentType` está exatamente como `application/json` |

---

**Arquivo:** `postman/postman_environment.json`  
**Versão:** 1.0  
**Criado em:** 3 de dezembro de 2025  
**API Base:** http://localhost:8088/api/produtos
