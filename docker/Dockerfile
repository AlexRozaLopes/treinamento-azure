# ---------------------------
# Etapa 1: Build da aplicação
# ---------------------------
FROM eclipse-temurin:21-jdk AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo de configuração e dependências
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Faz o download das dependências (para usar cache eficiente)
RUN ./mvnw dependency:go-offline -B

# Copia o código-fonte
COPY src src

# Compila e empacota a aplicação
RUN ./mvnw clean package -DskipTests

# ----------------------------
# Etapa 2: Imagem de execução
# ----------------------------
FROM eclipse-temurin:21-jre

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas o .jar gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
