@echo off
REM ============================================================================
REM Script para parar a aplicacao Spring Boot
REM ============================================================================

echo.
echo ====================================================================
echo           Parando a Aplicacao Spring Boot
echo ====================================================================
echo.

REM Verificar se ha processos Java rodando
tasklist | findstr java >nul
if %ERRORLEVEL% EQU 0 (
    echo [INFO] Encontrado processo Java rodando...
    echo.
    echo [PROCESSO] Matando todos os processos Java...
    taskkill /IM java.exe /F
    echo.
    echo [SUCESSO] Aplicacao parada com sucesso!
    echo.
) else (
    echo [AVISO] Nenhum processo Java encontrado rodando.
    echo.
)

REM Aguardar um momento para garantir que tudo foi finalizado
timeout /t 2 /nobreak

REM Verificar se a porta 8088 foi liberada
echo [INFO] Verificando se a porta 8088 foi liberada...
netstat -ano | findstr :8088 >nul
if %ERRORLEVEL% EQU 0 (
    echo [AVISO] Porta 8088 ainda esta em uso. Aguarde alguns segundos...
    timeout /t 3 /nobreak
) else (
    echo [SUCESSO] Porta 8088 foi liberada!
)

echo.
echo ====================================================================
echo           Aplicacao parada com sucesso!
echo ====================================================================
echo.

REM Manter a janela aberta para ver as mensagens
pause
