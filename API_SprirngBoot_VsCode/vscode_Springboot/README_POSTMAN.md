Produto API - Coleção e cURLs

Visão geral
- Base URL: `http://localhost:8088`
- Resource root: `/api/produtos`

Instruções rápidas
- Importe `postman/Produto API.postman_collection.json` no Postman (File -> Import).
- A coleção contém exemplos para os 11 endpoints implementados em `ProdutoController`.

Endpoints e cURL exemplos

1) Listar todos os produtos
- Método: GET
- URL: `http://localhost:8088/api/produtos`

cURL:

```bash
curl -sS -X GET "http://localhost:8088/api/produtos" -H "Accept: application/json"
```
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
    },
    {
        "id": 3,
        "nome": "Teclado MecÃ¢nico",
        "descricao": "Teclado mecanico RGB",
        "preco": 450.00,
        "quantidade": 8,
        "sku": "TECLADO-MECH-001",
        "ativo": true
    },
    {
        "id": 4,
        "nome": "Monitor LG 24 polegadas",
        "descricao": "Monitor Full HD 1920x1080",
        "preco": 650.00,
        "quantidade": 3,
        "sku": "MONITOR-LG-24",
        "ativo": true
    },
    {
        "id": 5,
        "nome": "Webcam HD",
        "descricao": "Webcam 1080p com microfone",
        "preco": 120.00,
        "quantidade": 15,
        "sku": "WEBCAM-HD-001",
        "ativo": true
    },
    {
        "id": 6,
        "nome": "Headset Gamer",
        "descricao": "Headset com som surround 7.1",
        "preco": 250.00,
        "quantidade": 10,
        "sku": "HEADSET-GAME-001",
        "ativo": true
    },
    {
        "id": 7,
        "nome": "SSD 1TB",
        "descricao": "Unidade SSD NVMe 1TB",
        "preco": 400.00,
        "quantidade": 12,
        "sku": "SSD-NVMe-1TB",
        "ativo": true
    },
    {
        "id": 8,
        "nome": "RAM DDR4 16GB",
        "descricao": "MemÃ³ria RAM DDR4 16GB 3200MHz",
        "preco": 280.00,
        "quantidade": 25,
        "sku": "RAM-DDR4-16GB",
        "ativo": false
    }
]
2) Listar produtos ativos
- Método: GET
- URL: `http://localhost:8088/api/produtos/ativos`

cURL:

```bash
curl -sS -X GET "http://localhost:8088/api/produtos/ativos" -H "Accept: application/json"
```
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
    },
    {
        "id": 3,
        "nome": "Teclado MecÃ¢nico",
        "descricao": "Teclado mecanico RGB",
        "preco": 450.00,
        "quantidade": 8,
        "sku": "TECLADO-MECH-001",
        "ativo": true
    },
    {
        "id": 4,
        "nome": "Monitor LG 24 polegadas",
        "descricao": "Monitor Full HD 1920x1080",
        "preco": 650.00,
        "quantidade": 3,
        "sku": "MONITOR-LG-24",
        "ativo": true
    },
    {
        "id": 5,
        "nome": "Webcam HD",
        "descricao": "Webcam 1080p com microfone",
        "preco": 120.00,
        "quantidade": 15,
        "sku": "WEBCAM-HD-001",
        "ativo": true
    },
    {
        "id": 6,
        "nome": "Headset Gamer",
        "descricao": "Headset com som surround 7.1",
        "preco": 250.00,
        "quantidade": 10,
        "sku": "HEADSET-GAME-001",
        "ativo": true
    },
    {
        "id": 7,
        "nome": "SSD 1TB",
        "descricao": "Unidade SSD NVMe 1TB",
        "preco": 400.00,
        "quantidade": 12,
        "sku": "SSD-NVMe-1TB",
        "ativo": true
    }
]
3) Buscar produto por ID
- Método: GET
- URL: `http://localhost:8088/api/produtos/{id}`

Exemplo (id=1):

```bash
curl -sS -X GET "http://localhost:8088/api/produtos/1" -H "Accept: application/json"
```
{
    "id": 1,
    "nome": "Notebook Dell",
    "descricao": "Notebook com processador Intel i7",
    "preco": 3500.00,
    "quantidade": 5,
    "sku": "NOTEBOOK-DELL-001",
    "ativo": true
}
4) Buscar produto por SKU
- Método: GET
- URL: `http://localhost:8088/api/produtos/sku/{sku}`

Exemplo (sku=ARZ-001):

```bash
curl -sS -X GET "http://localhost:8088/api/produtos/sku/ARZ-001" -H "Accept: application/json"
```
{
    "mensagem": "Produto não encontrado com SKU: ARZ-001"
}
5) Buscar por nome (query param)
- Método: GET
- URL: `http://localhost:8088/api/produtos/buscar?nome=...`

Exemplo:

```bash
curl -sS -X GET "http://localhost:8088/api/produtos/buscar?nome=Arroz" -H "Accept: application/json"
```
[]
6) Criar produto
- Método: POST
- URL: `http://localhost:8088/api/produtos`
- Headers: `Content-Type: application/json`
- Body (JSON):

```json
{
  "nome": "Arroz Branco",
  "descricao": "Arroz tipo 1 - pacote 5kg",
  "preco": 12.5,
  "quantidade": 20,
  "sku": "ARZ-001",
  "ativo": true
}
```

cURL:

```bash
curl -sS -X POST "http://localhost:8088/api/produtos" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Arroz Branco","descricao":"Arroz tipo 1 - pacote 5kg","preco":12.5,"quantidade":20,"sku":"ARZ-001","ativo":true}'
```
{
    "id": 9,
    "nome": "Arroz Branco",
    "descricao": "Arroz tipo 1 - pacote 5kg",
    "preco": 12.5,
    "quantidade": 20,
    "sku": "ARZ-001",
    "ativo": true
}
7) Atualizar produto (PUT)
- Método: PUT
- URL: `http://localhost:8088/api/produtos/{id}`
- Headers: `Content-Type: application/json`
- Body (JSON): incluir os campos a atualizar (exemplo abaixo)

Exemplo (id=1):

```bash
curl -sS -X PUT "http://localhost:8088/api/produtos/1" \
  -H "Content-Type: application/json" \
  -d '{"id":1,"nome":"Arroz Integral","descricao":"Arroz integral - pacote 5kg","preco":14.0,"quantidade":15,"sku":"ARZ-001","ativo":true}'
```
{
    "mensagem": "Já existe um produto com esse SKU: ARZ-001"
}
8) Deletar produto
- Método: DELETE
- URL: `http://localhost:8088/api/produtos/{id}`

Exemplo (id=1):

```bash
curl -sS -X DELETE "http://localhost:8088/api/produtos/1"
```
{
    "mensagem": "Produto deletado com sucesso"
}
9) Desativar produto (PATCH)
- Método: PATCH
- URL: `http://localhost:8088/api/produtos/{id}/desativar`

Exemplo (id=1):

```bash
curl -sS -X PATCH "http://localhost:8088/api/produtos/1/desativar"
```

10) Ativar produto (PATCH)
- Método: PATCH
- URL: `http://localhost:8088/api/produtos/{id}/ativar`

Exemplo (id=1):

```bash
curl -sS -X PATCH "http://localhost:8088/api/produtos/1/ativar"
```

11) Health
- Método: GET
- URL: `http://localhost:8088/api/produtos/health`

cURL:

```bash
curl -sS -X GET "http://localhost:8088/api/produtos/health" -H "Accept: application/json"
```

Observações
- Substitua `localhost:8088` se estiver rodando em outro host/porta.
- Para requests com `Content-Type: application/json`, cuidado com as aspas ao construir o `-d` no Windows PowerShell: prefira usar um arquivo JSON com `-d @file.json` ou use single quotes no bash.
- Se quiser, eu importo a coleção no Postman do seu ambiente ou gero um arquivo `postman_environment.json` com variáveis (baseUrl).
