# Projeto de Vota��o

## Stack de Tecnologia

- Java 11
- Spring Boot 2.7.17
- Spring Data JPA
- H2 Database
- Lombok
- Spring Kafka
- JaCoCo para cobertura de testes

## Configura��o

Este projeto � configurado com Gradle. As principais depend�ncias incluem Spring Boot,Spring Data JPA para persist�ncia de dados, 
H2 como banco de dados em mem�ria mas como especificado foi configurado para N�O apagar os dados na inicializa��o do projeto,
Lombok para reduzir o boilerplate de c�digo, Kafka para comunica��o assincrona entre servi�os e JaCoCo para an�lise de cobertura de testes.

## Executando o Projeto

Para executar o projeto, use o seguinte comando no terminal:

```bash
./gradlew bootRun
```
Isso ir� iniciar a aplica��o na porta padr�o do Spring Boot (8080). Acesse a aplica��o via http://localhost:8080.


## Executando os Testes
Para executar os testes do projeto, utilize o comando:
```bash
./gradlew test
```
Este comando ir� executar todos os testes unit�rios e de integra��o configurados no projeto.

## Gerando o Relat�rio de Cobertura de Testes com JaCoCo
Para gerar o relat�rio de cobertura de testes com JaCoCo, execute:
```bash
./gradlew jacocoTestReport
```

## Verifica��o da Cobertura de Testes
Para verificar se a cobertura de testes atinge o limite m�nimo estabelecido (20% neste projeto), execute:
N�o � a porcentagem ideal, o certo � cobertura de pelo menos 80% mas neste projeto foi incluido testes apenas como exemplo de um projeto real.
```bash
./gradlew check
```
Este comando ir� falhar se a cobertura de testes estiver abaixo do limite especificado.


# Evid�ncias de Teste
Arquivo collection/env para import no postman pode ser encontrado no projeto junto com arquivo docker compose para subir o kafka localmente.
```bash
LOCAL - API Vota��o.postman_environment.json
API - Vota��o.postman_collection.json

docker-compose.yml
```
Cria��o de Pauta
![img.png](img.png)

Abertura de Sess�o
![img_1.png](img_1.png)

Vota��o
![img_2.png](img_2.png)

Consulta de resultado
![img_3.png](img_3.png)

Produ��o de mensagem no Kafka
![img_4.png](img_4.png)
