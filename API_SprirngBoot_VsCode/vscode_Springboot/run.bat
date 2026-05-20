@echo off
REM Script para executar a API de Produtos Spring Boot

color 0A
cls

echo.
echo ====================================================
echo   API REST de Produtos - Spring Boot
echo   Porta: 8088
echo ====================================================
echo.

cd /d "%~dp0"

echo Compilando o projeto...
call mvn clean compile
if errorlevel 1 (
    echo.
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo.
echo Projeto compilado com sucesso!
echo.
echo Iniciando a aplicacao...
echo.
echo Acesse a API em: http://localhost:8088/api/produtos
echo H2 Console: http://localhost:8088/h2-console
echo.

call mvn spring-boot:run

pause
