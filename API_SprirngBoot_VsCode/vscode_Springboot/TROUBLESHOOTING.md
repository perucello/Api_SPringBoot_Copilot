# Troubleshooting - Guia de Solução de Problemas

## 🔴 Erros Comuns e Soluções

### 1. **Erro: "mvn is not recognized"**

**Mensagem:**
```
'mvn' is not recognized as an internal or external command
```

**Causas:**
- Maven não instalado
- Maven não está no PATH do Windows
- Variável MAVEN_HOME não configurada

**Soluções:**

a) Instalar Maven:
```powershell
# Baixar Maven em: https://maven.apache.org/download.cgi
# Extrair em: C:\apache-maven-3.8.1
```

b) Adicionar ao PATH (Windows):
```
1. Painel de Controle > Sistema > Variáveis de Ambiente
2. Adicionar MAVEN_HOME = C:\apache-maven-3.8.1
3. Adicionar %MAVEN_HOME%\bin ao PATH
4. Restart do Windows
```

c) Usar caminho completo:
```powershell
C:\apache-maven-3.8.1\bin\mvn clean compile
```

d) Verificar instalação:
```powershell
mvn -v
# Deve mostrar versão do Maven
```

---

### 2. **Erro: "java is not recognized"**

**Mensagem:**
```
'java' is not recognized as an internal or external command
```

**Causas:**
- Java não instalado
- Java não está no PATH
- JAVA_HOME não configurado

**Soluções:**

a) Instalar JDK 8:
```
Baixar em: https://www.oracle.com/java/technologies/downloads/#java8
```

b) Configurar variáveis (Windows):
```
1. Painel de Controle > Variáveis de Ambiente
2. Adicionar JAVA_HOME = C:\Program Files\Java\jdk1.8.0_xxx
3. Adicionar %JAVA_HOME%\bin ao PATH
4. Restart do Windows
```

c) Verificar instalação:
```powershell
java -version
javac -version
```

---

### 3. **Erro: "Port 8088 already in use"**

**Mensagem:**
```
Address [0.0.0.0]:8088 is already in use
```

**Causas:**
- Outra aplicação usando a porta
- Aplicação anterior ainda rodando

**Soluções:**

a) Encontrar processo usando a porta:
```powershell
netstat -ano | findstr :8088
# Exemplo de saída: TCP 0.0.0.0:8088 0.0.0.0:0 LISTENING 5432
```

b) Finalizar processo:
```powershell
taskkill /PID 5432 /F
# Substitua 5432 pelo PID encontrado
```

c) Usar porta diferente:

Editar `src/main/resources/application.properties`:
```properties
server.port=8089
```

d) Verificar se porta está livre:
```powershell
netstat -ano | findstr :8088
# Se não retornar nada, porta está livre
```

---

### 4. **Erro: "Failed to resolve dependency"**

**Mensagem:**
```
[ERROR] Failed to resolve dependency for project
[ERROR] Could not find artifact org.springframework.boot:...
```

**Causas:**
- Problema com repositório Maven
- Cache corrompido
- Sem conexão de internet

**Soluções:**

a) Limpar cache Maven:
```powershell
mvn clean
```

b) Forçar atualização:
```powershell
mvn clean install -U
```

c) Verificar conexão internet:
```powershell
ping google.com
```

d) Configurar repositório alternativo:

Editar `~/.m2/settings.xml` ou usar:
```powershell
mvn clean install -DarchetypeRepository=https://repo.maven.apache.org/maven2
```

---

### 5. **Erro: "The declared package does not match"**

**Mensagem:**
```
The declared package "com.demo" does not match the expected package
```

**Causas:**
- Estrutura de diretórios incorreta
- Arquivo Java no lugar errado

**Solução:**
```
Estrutura correta deve ser:
src/main/java/com/demo/Application.java
                  ↑
            Matches com "package com.demo;"
```

---

### 6. **Erro: "B类cannot be resolved"**

**Mensagem:**
```
Spring cannot be resolved to a type
```

**Causas:**
- Dependências não foram baixadas
- Maven não foi executado

**Soluções:**

a) Executar Maven:
```powershell
mvn clean compile
# Vai baixar todas as dependências
```

b) Atualizar no VSCode:
- Cmd + Shift + P
- Java: Clean Language Server Workspace

c) Verificar pom.xml:
- Confirmar que arquivo não está corrompido
- Validar sintaxe XML

---

### 7. **Erro: "H2 Console não acessível"**

**Mensagem:**
```
404 Not Found - http://localhost:8088/h2-console
```

**Causas:**
- H2 Console não habilitado
- Configuração incorreta

**Solução:**

Verificar `application.properties`:
```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

### 8. **Erro: "Application não inicia"**

**Mensagem:**
```
Application failed to start
```

**Causas:**
- Erro de compilação
- Problema no código

**Soluções:**

a) Ver log completo:
```powershell
mvn spring-boot:run
# Ler a saída no final
```

b) Limpar e recompilar:
```powershell
mvn clean compile
mvn spring-boot:run
```

c) Verificar erros:
```powershell
mvn compile
# Deve indicar exatamente onde está o problema
```

---

### 9. **Erro: "Permission denied run.ps1"**

**Mensagem:**
```
File C:\...\run.ps1 cannot be loaded because running scripts is disabled
```

**Solução:**

Habilitar scripts PowerShell:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
# Responda: Y (Yes)
```

---

### 10. **Erro: "Database connection failed"**

**Mensagem:**
```
org.h2.jdbc.JdbcSQLException: Connection refused
```

**Causas:**
- Configuração H2 incorreta
- Aplicação iniciando muito rápido

**Solução:**

H2 é em memória, deve estar OK. Se persistir:
```properties
# Verificar em application.properties
spring.datasource.url=jdbc:h2:mem:produtosdb
```

---

## ⚠️ Problemas de Performance

### **Aplicação Lenta**

**Causas:**
- Falta de RAM
- SSD cheio
- Maven baixando dependências

**Soluções:**
```powershell
# Aumentar memória JVM
$env:MAVEN_OPTS = "-Xmx1024m"
mvn spring-boot:run
```

---

## 🔍 Debugging

### **Ver Logs Detalhados**

Editar `application.properties`:
```properties
logging.level.root=DEBUG
logging.level.com.demo=TRACE
logging.level.org.springframework=DEBUG
```

### **Ver SQL Gerado**

Editar `application.properties`:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### **Monitor em Tempo Real**

H2 Console:
```
http://localhost:8088/h2-console
```

---

## ✅ Checklist de Diagnóstico

```
[ ] Java instalado? (java -version)
[ ] Maven instalado? (mvn -version)
[ ] Variáveis JAVA_HOME e MAVEN_HOME configuradas?
[ ] Porta 8088 livre? (netstat -ano | findstr :8088)
[ ] pom.xml válido? (mvn validate)
[ ] Dependências carregadas? (mvn dependency:resolve)
[ ] Código compila? (mvn compile)
[ ] Aplicação inicia? (mvn spring-boot:run)
[ ] API responde? (curl http://localhost:8088/api/produtos/health)
```

---

## 🆘 Quando Nada Funciona

### **Reset Completo**

```powershell
# 1. Parar a aplicação (Ctrl+C no terminal)

# 2. Limpar cache Maven
Remove-Item -Recurse $env:USERPROFILE\.m2\repository

# 3. Limpar projeto
mvn clean

# 4. Reconstruir do zero
mvn clean install

# 5. Executar
mvn spring-boot:run
```

---

## 📞 Recursos de Ajuda

### **Sites Úteis**
- Spring Boot Docs: https://spring.io/projects/spring-boot
- Maven Central: https://mvnrepository.com/
- Stack Overflow: https://stackoverflow.com/questions/tagged/spring-boot
- Spring Forum: https://spring.io/questions

### **Comandos Úteis**

```powershell
# Ver versão do pom.xml
mvn help:effective-pom

# Árvore de dependências
mvn dependency:tree

# Validar pom.xml
mvn validate

# Listar plugins
mvn help:describe -Dplugin=spring-boot

# Executar com modo debug
mvn spring-boot:run -Dspring-boot.run.arguments="--debug"
```

---

## 📋 Checklist de Configuração

Após instalar Java e Maven, confirme:

```
JAVA_HOME = C:\Program Files\Java\jdk1.8.0_xxx
MAVEN_HOME = C:\apache-maven-3.8.1
PATH contém: %JAVA_HOME%\bin;%MAVEN_HOME%\bin
```

Verificar:
```powershell
echo $env:JAVA_HOME
echo $env:MAVEN_HOME
java -version     # Deve ser Java 8
mvn -version      # Deve ser Maven 3.6+
```

---

## 🎓 Dicas Profissionais

1. **Sempre limpar antes de compilar**
   ```powershell
   mvn clean compile
   ```

2. **Usar verbose para debug**
   ```powershell
   mvn -X clean compile
   ```

3. **Verificar dependências conflitantes**
   ```powershell
   mvn dependency:tree
   ```

4. **Skipped tests podem ocultar problemas**
   ```powershell
   mvn clean compile -DskipTests=false
   ```

5. **Spring Boot precisa de suficiente heap**
   ```powershell
   $env:MAVEN_OPTS = "-Xmx512m"
   ```

---

**Última atualização**: Dezembro 2025  
**Versão**: 1.0.0
