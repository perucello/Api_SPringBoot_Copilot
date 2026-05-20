# Guia de Instalação - API de Produtos Spring Boot

## Pré-requisitos

### 1. Java Development Kit (JDK)

**Versão requerida**: Java 8 ou superior

#### Windows:

1. Baixar o JDK em: https://www.oracle.com/java/technologies/downloads/#java8
   - Ou use: https://adoptopenjdk.net/

2. Instalar seguindo o assistente

3. Verificar instalação:
```powershell
java -version
javac -version
```

4. Configurar variáveis de ambiente (se necessário):
   - Painel de Controle > Variáveis de Ambiente
   - Nova variável: `JAVA_HOME` = `C:\Program Files\Java\jdk1.8.0_xxx`
   - Adicionar ao PATH: `%JAVA_HOME%\bin`

### 2. Maven

**Versão requerida**: Maven 3.6.0 ou superior

#### Windows:

1. Baixar em: https://maven.apache.org/download.cgi

2. Extrair em um diretório (ex: `C:\apache-maven-3.8.1`)

3. Configurar variáveis de ambiente:
   - Nova variável: `MAVEN_HOME` = `C:\apache-maven-3.8.1`
   - Adicionar ao PATH: `%MAVEN_HOME%\bin`

4. Verificar instalação:
```powershell
mvn -version
```

### 3. Git (Opcional)

Para clonar o repositório:
https://git-scm.com/download/win

---

## Instalação do Projeto

### Método 1: Via PowerShell

1. Navegue até a pasta do projeto:
```powershell
cd c:\Users\HOME\Downloads\vscode_Springboot
```

2. Execute o script:
```powershell
.\run.ps1
```

### Método 2: Via Prompt de Comando (CMD)

1. Navegue até a pasta do projeto:
```cmd
cd c:\Users\HOME\Downloads\vscode_Springboot
```

2. Execute o script:
```cmd
run.bat
```

### Método 3: Manual

1. Limpar builds anteriores:
```powershell
mvn clean
```

2. Compilar o projeto:
```powershell
mvn compile
```

3. Executar a aplicação:
```powershell
mvn spring-boot:run
```

---

## Verificação de Instalação

### 1. Health Check

Abra seu navegador e acesse:
```
http://localhost:8088/api/produtos/health
```

Você deve ver:
```json
{
  "status": "UP",
  "mensagem": "API de Produtos está rodando"
}
```

### 2. Acessar a API

```
http://localhost:8088/api/produtos
```

Deve retornar a lista de produtos iniciais.

### 3. H2 Console (Opcional)

```
http://localhost:8088/h2-console
```

Conexão:
- JDBC URL: `jdbc:h2:mem:produtosdb`
- User Name: `sa`
- Password: (deixar em branco)

---

## Solução de Problemas

### Erro: "mvn is not recognized"

**Solução**: Maven não está no PATH
- Verifique a variável MAVEN_HOME
- Restart do Windows pode ser necessário
- Ou use o caminho completo: `C:\apache-maven-3.8.1\bin\mvn`

### Erro: "java is not recognized"

**Solução**: Java não está no PATH
- Verifique a variável JAVA_HOME
- Restart do Windows pode ser necessário

### Erro: Port 8088 already in use

**Solução**: A porta já está em uso
- Mude a porta em `src/main/resources/application.properties`
- Ou finalize o processo usando a porta

```powershell
# Encontrar processo na porta 8088
netstat -ano | findstr :8088

# Finalizar processo (substitua PID pelo número encontrado)
taskkill /PID <PID> /F
```

### Erro: Permission denied no run.ps1

**Solução**: Habilitar execução de scripts
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Erro: "Failed to resolve dependency"

**Solução**: Problema com Maven cache
```powershell
mvn clean install -U
```

---

## Estrutura de Arquivos após Instalação

```
vscode_Springboot/
├── src/
│   ├── main/
│   │   ├── java/com/demo/
│   │   │   ├── Application.java
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── dto/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
├── target/               (criado após compilação)
├── pom.xml
├── README.md
├── GUIA_USO_API.md
├── run.bat
├── run.ps1
└── .gitignore
```

---

## Próximos Passos

1. Leia o `README.md` para entender a arquitetura
2. Consulte o `GUIA_USO_API.md` para exemplos de requisições
3. Use o `run.bat` ou `run.ps1` para iniciar a aplicação
4. Teste os endpoints usando Postman ou cURL

---

## Contato e Suporte

Para dúvidas sobre:
- Java: https://docs.oracle.com/javase/8/docs/
- Spring Boot: https://spring.io/
- Maven: https://maven.apache.org/

---

## Versão da Documentação

- Data: Dezembro 2025
- Versão: 1.0.0
- Compatibilidade: Java 8+, Maven 3.6+, Windows 10/11
