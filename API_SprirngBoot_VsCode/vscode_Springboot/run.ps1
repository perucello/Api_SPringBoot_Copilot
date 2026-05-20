#!/usr/bin/env pwsh

Write-Host "`n" -ForegroundColor Green
Write-Host "================================================" -ForegroundColor Green
Write-Host "  API REST de Produtos - Spring Boot" -ForegroundColor Green
Write-Host "  Porta: 8088" -ForegroundColor Green
Write-Host "================================================" -ForegroundColor Green
Write-Host "`n" -ForegroundColor Green

$projectPath = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $projectPath

Write-Host "Compilando o projeto..." -ForegroundColor Yellow
mvn clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "`nErro na compilacao!" -ForegroundColor Red
    exit 1
}

Write-Host "`nProjeto compilado com sucesso!" -ForegroundColor Green
Write-Host "`nIniciando a aplicacao..." -ForegroundColor Yellow
Write-Host "`nAcesse a API em: http://localhost:8088/api/produtos" -ForegroundColor Cyan
Write-Host "H2 Console: http://localhost:8088/h2-console`n" -ForegroundColor Cyan

mvn spring-boot:run
