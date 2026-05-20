# Docker - Produto API

## Visão Geral

Este guia explica como construir, executar e gerenciar a **Produto API** usando **Docker** e **Docker Compose**.

---

## Pré-requisitos

- **Docker** instalado ([Download](https://www.docker.com/products/docker-desktop))
- **Docker Compose** (incluído no Docker Desktop)
- Ou use WSL2 no Windows para melhor performance

---

## Build da Imagem Docker

### Opção 1: Build Manual

```bash
# Navigate to the project root
cd /path/to/vscode_Springboot

# Build the image
docker build -t produto-api:1.0.0 .

# Verificar imagem criada
docker images | grep produto-api
```

### Opção 2: Build com Docker Compose

```bash
# Build via docker-compose (mais fácil)
docker-compose build

# Verificar imagem
docker images
```

---

## Executar a Aplicação

### Opção 1: Executar Manualmente com Docker

```bash
# Run the container
docker run -d \
  --name produto-api \
  -p 8088:8088 \
  -e JAVA_OPTS="-Xmx512m -Xms256m" \
  produto-api:1.0.0

# Ver logs
docker logs -f produto-api

# Parar o container
docker stop produto-api

# Remover o container
docker rm produto-api
```

### Opção 2: Executar com Docker Compose (Recomendado)

```bash
# Iniciar os serviços (build + run)
docker-compose up -d

# Ver status dos serviços
docker-compose ps

# Ver logs em tempo real
docker-compose logs -f produto-api

# Parar os serviços
docker-compose down

# Parar e remover volumes
docker-compose down -v
```

---

## Verificar se a Aplicação Está Rodando

### Health Check

```bash
# Via cURL
curl http://localhost:8088/api/produtos/health

# Via PowerShell
Invoke-WebRequest -Uri 'http://localhost:8088/api/produtos/health'

# Resposta esperada
{
  "status": "UP",
  "mensagem": "API de Produtos está rodando"
}
```

### Listar Produtos

```bash
curl http://localhost:8088/api/produtos

# Ou com Postman
# 1. Importe postman/Produto API.postman_collection.json
# 2. Selecione postman/postman_environment.json
# 3. Execute a requisição "Listar todos os produtos"
```

---

## Arquivos Docker Inclusos

### `Dockerfile`
- **Estratégia:** Multi-stage build (otimização de tamanho)
- **Estágio 1:** Builder — compila o projeto com Maven
- **Estágio 2:** Runtime — apenas JRE (imagem menor)
- **Tamanho:** ~500MB (JRE base) + ~50MB (aplicação)
- **Healthcheck:** Monitora `/api/produtos/health` a cada 30s

### `docker-compose.yml`
- **Serviço:** `produto-api`
- **Porta:** 8088 (mapeada para localhost:8088)
- **Restart policy:** `unless-stopped` (reinicia se falhar)
- **Healthcheck:** Integrado
- **Volumes:** Logs persistidos em `./logs`
- **Network:** Rede customizada `produto-network`

### `.dockerignore`
- Otimiza o build excluindo arquivos desnecessários
- Exclui: `.git`, `target/`, `.idea/`, documentação, etc.
- Reduz o tamanho do contexto do build

---

## Variáveis de Ambiente

### JAVA_OPTS
```bash
# Padrão
JAVA_OPTS="-Xmx512m -Xms256m"

# Aumentar heap para mais produtos em memória
JAVA_OPTS="-Xmx1024m -Xms512m"

# Adicionar debug
JAVA_OPTS="-Xmx512m -Xms256m -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

### SPRING_PROFILES_ACTIVE
```bash
# Produção
SPRING_PROFILES_ACTIVE=prod

# Desenvolvimento
SPRING_PROFILES_ACTIVE=dev
```

---

## Gerenciamento de Containers

### Ver Containers Rodando

```bash
docker ps

# Ver todos os containers (incluindo parados)
docker ps -a
```

### Inspecionar Container

```bash
# Ver detalhes do container
docker inspect produto-api

# Ver logs
docker logs produto-api

# Ver logs em tempo real
docker logs -f produto-api

# Ver últimas 100 linhas
docker logs --tail 100 produto-api
```

### Executar Comando dentro do Container

```bash
# Acessar shell do container
docker exec -it produto-api bash

# Testar health check
docker exec produto-api curl http://localhost:8088/api/produtos/health

# Ver processes
docker exec produto-api ps aux
```

---

## Exemplo Completo de Workflow

```bash
# 1. Clonar/navegar para o repositório
cd /path/to/vscode_Springboot

# 2. Build da imagem
docker-compose build

# 3. Iniciar a aplicação
docker-compose up -d

# 4. Aguardar startup (5-10 segundos)
sleep 10

# 5. Verificar health
curl http://localhost:8088/api/produtos/health

# 6. Testar endpoint de listagem
curl http://localhost:8088/api/produtos

# 7. Ver logs
docker-compose logs -f

# 8. Parar quando terminar
docker-compose down
```

---

## Troubleshooting

### Container não inicia

```bash
# Ver logs de erro
docker logs produto-api

# Solução comum: Porta 8088 já está em uso
docker ps  # Ver quem está usando
lsof -i :8088  # (em Linux/Mac)
netstat -ano | findstr :8088  # (em Windows)
```

### Health check falha

```bash
# Verificar se o container está ativo
docker ps | grep produto-api

# Entrar no container e testar
docker exec -it produto-api curl http://localhost:8088/api/produtos/health

# Aumentar timeout do healthcheck em docker-compose.yml se necessário
```

### Erro de memória (OutOfMemoryError)

```bash
# Aumentar heap size em docker-compose.yml
environment:
  - JAVA_OPTS=-Xmx1024m -Xms512m
  
# Depois: docker-compose up -d --force-recreate
```

### Limpar espaço em disco

```bash
# Remover containers inativos
docker container prune

# Remover imagens não utilizadas
docker image prune

# Limpeza completa (CUIDADO!)
docker system prune -a
```

---

## Deployment em Produção

### Amazon EC2

```bash
# SSH para o servidor
ssh -i key.pem ec2-user@your-instance-ip

# Instalar Docker
sudo yum update -y && sudo yum install docker -y
sudo systemctl start docker

# Clonar repositório
git clone https://github.com/seu-usuario/projeto.git
cd projeto

# Iniciar com docker-compose
docker-compose up -d
```

### Google Cloud Run

```bash
# Fazer login no GCP
gcloud auth login

# Build e push da imagem
docker build -t gcr.io/seu-projeto/produto-api:1.0.0 .
docker push gcr.io/seu-projeto/produto-api:1.0.0

# Deploy no Cloud Run
gcloud run deploy produto-api \
  --image gcr.io/seu-projeto/produto-api:1.0.0 \
  --port 8088 \
  --memory 512Mi
```

### Docker Hub (para compartilhar)

```bash
# Login no Docker Hub
docker login

# Tag da imagem
docker tag produto-api:1.0.0 seu-usuario/produto-api:1.0.0

# Push para o Hub
docker push seu-usuario/produto-api:1.0.0

# Qualquer um pode fazer pull e rodar
docker pull seu-usuario/produto-api:1.0.0
docker run -p 8088:8088 seu-usuario/produto-api:1.0.0
```

---

## Performance

### Otimização de Imagem

```dockerfile
# Usar alpine (mais leve)
FROM eclipse-temurin:17-jre-alpine

# Reduz o tamanho final para ~200MB
```

### Caching do Docker

```bash
# Docker cacheia camadas; se não mudar pom.xml, reutiliza
# Para forçar rebuild sem cache
docker build --no-cache -t produto-api:1.0.0 .
```

---

## Monitoramento

### Prometheus + Grafana (opcional)

Adicionar ao `docker-compose.yml`:

```yaml
  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'

  grafana:
    image: grafana/grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
```

---

## Referências

- [Docker Docs](https://docs.docker.com/)
- [Docker Compose Docs](https://docs.docker.com/compose/)
- [Best Practices for Java Applications](https://docs.docker.com/language/java/)
- [Spring Boot Docker Documentation](https://spring.io/guides/gs/spring-boot-docker/)

---

**Arquivo:** `Dockerfile`  
**Versão:** 1.0  
**Criado em:** 3 de dezembro de 2025  
**Imagem Base:** eclipse-temurin:17-jre-jammy
