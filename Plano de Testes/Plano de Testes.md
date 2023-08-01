#  - Plano de Testes Sprint 3 [Sicredi API Simulação de Crédito v1] - ![compass.svg](/uploads/217944039619357e305958145c080112/compass.svg)


# US 001: [API] Restrições
------
"Como um consumidor da API eu gostaria de pode consultar os CPFs para saber quais deles possuem restrições"


## RESUMO
No contexto de funcionalidades da API "Simulação de Crédito v1", principalmente na rota Restrições, é necessário que as seguintes regras sejam funcionais.

1. Caso o CPF consultado possua restrição uma mensagem deve ser retornada informando que o CPF pesquisado possui restrição
2. Não deve ser possível realizar um GET e obter a lista de CPFs com restrições.
3. em caso de CPF com restrição a aplicação deve responder com status code 200
4. em caso do CPF não conter restrições aplicação deve responder com status code 204
5. apenas é possível usar GET na rota /restricoes

A fim de agregar valor, na aplicação uma série de testes e análises serão feitas em cima da aplicação em questão.


## Caso de testes US 001 ![test.svg](/uploads/fd41e63fe3e35b359609782a377bb8b9/test.svg)

Nomenclatura dos testes:

CTUxRx_xxx (Caso de Teste User Storie X, Regra X, xxx número do teste)

Ex: CTU1R1_001
CT = Caso de Teste, U1 = User Story 001, R1 = Regra de negócio número 1, _001 = Número do teste.

### R1
- CTU1R1_001 Teste consultando um CPF com restrição e esperar uma mensagem e o CPF consultado afirmando que ele está com restrição
- CTU1R1_002 Teste consultando um CPF sem restrição
- CTU1R1_003 Teste consultando um CPF com restrição e resposta deve conter um Json com apenas strings
- CTU1R1_003 Teste consultando um CPF no padrão /99999999999
- CTU1R1_003 Teste consultando um CPF no padrão /999.999.999-99
- CTU1R1_003 Teste consultando um CPF em padrões fora do esperado (letras, caracteres inválidos, query entre outros)

### R2
- CTU1R2_001 Teste consultando a rota /restricoes sem informar um cpf e esperar um erro

### R3
- CTU1R3_001 Teste consultando um CPF com restrição e esperar um status code 200
- CTU1R3_002 Teste consultando um CPF sem restrição e esperar que o status code não seja 200

### R4
- CTU1R4_001 Teste consultando um CPF sem restrição e esperar um status code 204
- CTU1R4_002 Teste consultando um CPF com restrição e esperar que o status code não seja 204

### R5
- CTU1R5_001 Teste consultando um CPF com restrição via POST
- CTU1R5_001 Teste consultando um CPF sem restrição via POST
- CTU1R5_001 Teste consultando um CPF com restrição via PUT
- CTU1R5_001 Teste consultando um CPF sem restrição via PUT
- CTU1R5_001 Teste consultando um CPF com restrição via DELETE
- CTU1R5_001 Teste consultando um CPF sem restrição via PATCH
- CTU1R5_001 Teste consultando um CPF com restrição via PATCH


## Testes candidatos a automação ![automação.svg](/uploads/b6cedbe066a9ca4267da2df9467d08f6/automação.svg)

Esses testes são os que mais se repetem no processo de teste da aplicação, por causa disso sua automação pode ser necessária.

### US1

- CTU1R1_001 Teste consultando um CPF com restrição e esperar uma mensagem e o CPF consultado afirmando que ele está com restrição
- CTU1R1_002 Teste consultando um CPF sem restrição
- CTU1R1_003 Teste consultando um CPF com restrição e resposta deve conter um json com apenas strings
- CTU1R1_003 Teste consultando um CPF no padrão /99999999999
- CTU1R1_003 Teste consultando um CPF no padrão /999.999.999-99
- CTU1R1_003 Teste consultando um CPF em padrões fora do esperado (letras, caracteres inválidos, query entre outros)
---
- CTU1R2_001 Teste consultando a rota /restricoes sem informar um cpf e esperar um erro
---
- CTU1R3_001 Teste consultando um CPF com restrição e esperar um status code 200
- CTU1R3_002 Teste consultando um CPF sem restrição e esperar que o status code não seja 200
---
- CTU1R4_001 Teste consultando um CPF sem restrição e esperar um status code 204
- CTU1R4_002 Teste consultando um CPF com restrição e esperar que o status code não seja 204
---
- CTU1R5_001 Teste consultando um CPF com restrição via POST
- CTU1R5_001 Teste consultando um CPF sem restrição via POST
- CTU1R5_001 Teste consultando um CPF com restrição via PUT
- CTU1R5_001 Teste consultando um CPF sem restrição via PUT
- CTU1R5_001 Teste consultando um CPF com restrição via DELETE
- CTU1R5_001 Teste consultando um CPF sem restrição via PATCH
- CTU1R5_001 Teste consultando um CPF com restrição via PATCH


## Escopo:

Funções de Simulação de créditos, principalmente funções como, consultar CPFs, consultar e realizar simulações de créditos.


## Cobertura

Restrições (GET)
Simulações (GET, GET{id}, POST, PUT, DELETE)

Testes manuais testando outras rotas e funções básicas da API.


## DoR

US 001: [API] Restrições
- Banco de dados e infraestrutura para desenvolvimento disponibilizados;
- Ambiente de testes disponibilizado;
- Massa de dados com lista de CPFs com restrições disponíveis para testes.


## DoD

US 001: [API] Restrições
- CRUD de Consultas de restrições, cobrindo o verbo GET
- Análise de testes cobrindo todos verbos;
- Matriz de rastreabilidade atualizada;
- Automação de testes baseado na análise realizada;


### Pessoas que testaram ![peaple.svg](/uploads/b26e028cf288aa9832b11739bec243ef/peaple.svg)

| Equipe pb_Sprint_6 |
| --- |
| Higor Milani |


### Ferramentas ![ferramenta.svg](/uploads/589fc388735633e4e612964c7734af88/ferramenta.svg)

* Postman Version 10.15.4
* Jira Software
* GitLab
* Xmind
* Swagger
> * Eclipse IDE 2023-03 (4.27.0)
>> * Junit Jupiter 5.9.0
>> * Junit 5
>> * Rest-Assured 5.1.1
>> * Javafaker 1.0.2
>> * Jackson 2.13.3
>> * Wiremock 2.33.2
>> * Allure Reports 2.18.1
* Java JDK 20.0.1 2023-04-18
* Apache Maven 3.9.2
* Project Lombok 1.18.28
* Git 2.41.0.windows.3

(Mais informações de versões no arquivo pom.xml na pasta raiz do projeto)


### Versão da aplicação

* Simulação de Crédito v1


![fututre.svg](/uploads/855ce7c4249236fae104ced55d70cd8a/fututre.svg)