# Projeto OdontoPrev - CI/CD Pipeline

## Visão Geral

Este projeto é uma aplicação Java Spring Boot para gerenciamento de Pacientes e Profissionais. Este repositório inclui a configuração das pipelines de Integração Contínua (CI) e Entrega Contínua (CD) utilizando Azure DevOps.

## Estrutura do Projeto

- Código fonte da aplicação está na pasta `odontoprev`.
- Pipelines Azure DevOps:
  - `azure-pipelines-ci.yml`: Pipeline de Integração Contínua (build, testes, empacotamento).
  - `azure-pipelines-cd.yml`: Pipeline de Entrega Contínua (deploy para Azure App Service).
- Scripts JSON para operações CRUD:
  - `odontoprev/src/main/resources/paciente-crud.json`
  - `odontoprev/src/main/resources/profissional-crud.json`

## Configuração das Pipelines no Azure DevOps

### Pipeline CI

- Disparada automaticamente em push para a branch `main`.
- Executa:
  - Build do projeto com Maven.
  - Execução dos testes.
  - Publicação dos artefatos gerados.

### Pipeline CD

- Disparada manualmente ou por gatilho após CI.
- Executa:
  - Download dos artefatos da build.
  - Deploy do artefato `.jar` para Azure App Service.
- Requer configuração do serviço Azure no Azure DevOps (`azureSubscription` e `appName`).

## Como Executar

1. Faça o push do código para o repositório GitHub.
2. Configure o projeto no Azure DevOps e importe os arquivos `azure-pipelines-ci.yml` e `azure-pipelines-cd.yml`.
3. Configure a conexão com o Azure App Service no Azure DevOps.
4. Execute a pipeline CI para build e testes.
5. Execute a pipeline CD para deploy.

## Testes

- Os testes são executados automaticamente na pipeline CI.
- Para executar localmente:
  ```bash
  cd odontoprev
  ./mvnw test
  ```

## Scripts JSON para CRUD

- Os arquivos JSON em `src/main/resources` contêm exemplos de requisições para as operações CRUD das entidades Paciente e Profissional.
- Utilize ferramentas como Postman para importar e testar as APIs.

## Observações

- Atualize os placeholders `<YOUR_AZURE_SERVICE_CONNECTION>` e `<YOUR_APP_SERVICE_NAME>` no arquivo `azure-pipelines-cd.yml` com suas configurações reais.
- Certifique-se de que o Azure App Service esteja configurado para rodar aplicações Java.

---
Este README foi criado para facilitar a reprodução e testes das pipelines e da aplicação.
