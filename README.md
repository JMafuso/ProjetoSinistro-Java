# Projeto OdontoPrev - CI/CD Pipeline

## Visão Geral

Este projeto é uma aplicação Java Spring Boot para gerenciamento de Pacientes e Profissionais. Este repositório inclui a configuração das pipelines de Integração Contínua (CI) e Entrega Contínua (CD) utilizando Azure DevOps.

## Desenho da Pipeline CI/CD

A pipeline foi desenhada para garantir a qualidade e entrega contínua da aplicação, com as seguintes etapas:

### Pipeline de Integração Contínua (CI)

1. **Disparo automático**: A pipeline é disparada automaticamente a cada push na branch `main`.
2. **Build**: O código é compilado utilizando Maven, garantindo que o projeto está construído corretamente.
3. **Testes**: São executados os testes automatizados para validar o comportamento da aplicação.
4. **Publicação de artefatos**: O artefato gerado (arquivo `.jar`) é publicado para ser utilizado na etapa de deploy.

### Pipeline de Entrega Contínua (CD)

1. **Disparo manual ou automático**: A pipeline pode ser disparada manualmente ou configurada para disparar após a conclusão da pipeline CI.
2. **Download do artefato**: O artefato gerado na pipeline CI é baixado para o ambiente de deploy.
3. **Deploy**: O artefato é implantado em um Azure App Service configurado para rodar aplicações Java.
4. **Monitoramento**: Após o deploy, a aplicação pode ser monitorada utilizando ferramentas como Prometheus (configurado no projeto).

## Configuração das Pipelines no Azure DevOps

- Os arquivos `azure-pipelines-ci.yml` e `azure-pipelines-cd.yml` definem as pipelines CI e CD, respectivamente.
- Estes arquivos estão localizados na pasta `odontoprev` do projeto.
- A pipeline CI está configurada para build, teste e publicação de artefatos.
- A pipeline CD está configurada para deploy do artefato no Azure App Service.
- É necessário configurar as variáveis `<YOUR_AZURE_SERVICE_CONNECTION>` e `<YOUR_APP_SERVICE_NAME>` no arquivo `azure-pipelines-cd.yml` para apontar para sua assinatura e aplicação no Azure.

## Código Fonte e Scripts JSON

- O código fonte da aplicação está na pasta `odontoprev/src`.
- Os scripts JSON para operações CRUD das entidades Paciente e Profissional estão em:
  - `odontoprev/src/main/resources/paciente-crud.json`
  - `odontoprev/src/main/resources/profissional-crud.json`
- Estes scripts podem ser utilizados para testar as APIs utilizando ferramentas como Postman.

## Como Executar

1. Faça o push do código para o repositório GitHub conectado ao Azure DevOps.
2. Crie as pipelines no Azure DevOps utilizando os arquivos YAML na pasta `odontoprev`.
3. Execute a pipeline CI para build e testes.
4. Execute a pipeline CD para deploy.
5. Utilize os scripts JSON para testar as APIs.
6. Consulte os logs das pipelines para monitorar a execução.

## Considerações Finais

Este setup garante uma entrega contínua eficiente, com validação automática do código e deploy rápido para o ambiente de produção.

---
Este README foi criado para facilitar a reprodução e testes das pipelines e da aplicação.
