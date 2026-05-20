# Use uma imagem base oficial do Java 17
FROM eclipse-temurin:17-jdk-jammy AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e o projeto
COPY pom.xml .
COPY src ./src

# Compila o projeto com Maven (sem testes para acelerar)
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

# -----------------------------------
# Estágio 2: Runtime (imagem menor)
# -----------------------------------
FROM eclipse-temurin:17-jre-jammy

# Define variáveis de metadados
LABEL maintainer="seu-email@example.com"
LABEL version="1.0.0"
LABEL description="Spring Boot Produto API - Docker Image"

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR compilado do estágio anterior
COPY --from=builder /app/target/springboot-api-produto-1.0.0.jar app.jar

# Expõe a porta 8088
EXPOSE 8088

# Define variáveis de ambiente (opcional)
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=prod

# Healthcheck para monitoramento
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8088/api/produtos/health || exit 1

# Comando para iniciar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
