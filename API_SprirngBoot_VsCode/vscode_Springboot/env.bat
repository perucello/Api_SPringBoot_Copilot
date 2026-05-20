@echo off
REM Arquivo de variáveis de ambiente para a aplicação

REM Defina a porta desejada (padrão: 8088)
set SERVER_PORT=8088

REM URL do banco de dados H2
set DB_URL=jdbc:h2:mem:produtosdb

REM Usuário do banco de dados
set DB_USER=sa

REM Senha do banco de dados (vazio por padrão)
set DB_PASSWORD=

REM Ativar console H2 (true/false)
set H2_CONSOLE_ENABLED=true

REM Mostrar SQL (true/false)
set SHOW_SQL=false

REM Nível de logging
set LOG_LEVEL=INFO

REM Nível de logging específico para a aplicação
set APP_LOG_LEVEL=DEBUG

echo Variáveis de ambiente carregadas!
echo Porta: %SERVER_PORT%
echo Banco de dados: %DB_URL%
echo Console H2: %H2_CONSOLE_ENABLED%
