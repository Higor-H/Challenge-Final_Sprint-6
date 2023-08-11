#  - Plano de Testes Sprint 3 [Sicredi API Simulação de Crédito v1] - ![Compass UOL Logo](img_plano_de_testes/compassUOL.svg)


# US 001: [API] Restrições ![User Story Icon](img_plano_de_testes/user.svg)
------
"Como um consumidor da API eu gostaria de poder consultar os CPFs para saber quais deles possuem restrições"

## RESUMO
No contexto de funcionalidades da API "Simulação de Crédito v1", principalmente na rota Restrições, é necessário que as seguintes regras sejam funcionais.

(Acceptance Criteria)

1. Caso o CPF consultado possua restrição uma mensagem deve ser retornada informando que o CPF pesquisado possui restrição
2. Não deve ser possível realizar um GET e obter a lista de CPFs com restrições
3. Em caso de CPF com restrição a aplicação deve responder com status code 200
4. Em caso do CPF não conter restrições aplicação deve responder com status code 204
5. Apenas é possível usar GET na rota /restricoes

A fim de agregar valor, na aplicação uma série de testes e análises serão feitas em cima da aplicação em questão.


## Caso de testes US 001 ![Testes Icon](img_plano_de_testes/test.svg)

Nomenclatura dos testes:

CTUxRx_xxx (Caso de Teste User Storie X, Regra X, xxx número do teste)

Ex: CTU1R1_001
CT = Caso de Teste, U1 = User Story 001, R1 = Regra de negócio número 1, _001 = Número do teste.

### R1
- CTU1R1_001 Teste consultando um CPF com restrição e esperar uma mensagem e o CPF consultado afirmando que ele está com restrição
- CTU1R1_002 Teste consultando um CPF sem restrição
- CTU1R1_003 Teste consultando um CPF com restrição e resposta deve conter um Json com apenas strings
- CTU1R1_004 Teste consultando um CPF no padrão /99999999999
- CTU1R1_005 Teste consultando um CPF no padrão /999.999.999-99
- CTU1R1_006 Teste consultando um CPF em padrões fora do esperado (letras, caracteres inválidos, query entre outros)

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
- CTU1R5_002 Teste consultando um CPF sem restrição via POST
- CTU1R5_003 Teste consultando um CPF com restrição via PUT
- CTU1R5_004 Teste consultando um CPF sem restrição via PUT
- CTU1R5_005 Teste consultando um CPF com restrição via DELETE
- CTU1R5_006 Teste consultando um CPF sem restrição via PATCH
- CTU1R5_007 Teste consultando um CPF com restrição via PATCH


# US 002: [API] Simulações ![User Story Icon](img_plano_de_testes/user.svg)
------
"Como um consumidor da API eu gostaria de poder consultar, inserir, atualizar e remover simulações de tomada de empréstimo"


## RESUMO
No contexto de funcionalidades da API "Simulação de Crédito v1", principalmente na rota Simulações, é necessário que as seguintes regras sejam funcionais.

(Acceptance Criteria)

1. CPFs com restrições não devem poder realizar ações de simulações de crédito
2. O CPF deve ter o formato 99999999999 e não deve ter o formato 999.999.999-99
3. O nome da pessoa deve ser um nome válido 
4. O e-mail deve ser um e-mail válido
5. O valor da simulação deve ser >= R$1000 e <= R$40000
6. O número de parcelas deve ser >= 2 e <= 48
7. O campo seguro deve ser um booleano
8. Não se deve ter mais de 1 simulação por CPF
9. Se há um problema com o envio de uma nova simulação o status code 400 e uma lista de erros são retornados
10. Ao realizar um PUT em um CPF sem simulação cadastrada deve retornar um status code 404 e uma mensagem "CPF não encontrado" (Não deve criar uma nova simulação com PUT)
11. Caso não existam simulações cadastradas ao realizar um GET em /simulacoes um status code 204 deve ser retornado
12. Apenas deve ser possível deletar uma simulação pelo seu ID

A fim de agregar valor, na aplicação uma série de testes e análises serão feitas em cima da aplicação em questão.


## Caso de testes US 002 ![Testes Icon](img_plano_de_testes/test.svg)

Nomenclatura dos testes:

CTUxRx_xxx (Caso de Teste User Storie X, Regra X, xxx número do teste)

Ex: CTU1R1_001
CT = Caso de Teste, U1 = User Story 001, R1 = Regra de negócio número 1, _001 = Número do teste.

### R1
- CTU2R1_001 Teste tentando realizar um POST informando um CPF com restrição e esperar um erro
- CTU2R1_002 Teste tentando realizar um GET informando um CPF com restrição e esperar um erro
- CTU2R1_003 Teste tentando realizar um PATCH informando um CPF com restrição e esperar um erro
- CTU2R1_004 Teste tentando realizar um PUT em uma simulação que tem um CPF válido e tentar alterar ele para um CPF com restrição
- CTU2R1_005 Teste tentando realizar um DELETE informando um CPF com restrição e esperar um erro 

### R2
- CTU2R2_001 Teste tentando realizar um POST de CPF usando o formato 999.999.999-99 e esperar um erro
- CTU2R2_002 Teste tentando realizar um GET de CPF usando o formato 999.999.999-99 e esperar um erro
- CTU2R2_003 Teste tentando realizar um DELETE de CPF usando o formato 999.999.999-99 e esperar um erro 
- CTU2R2_004 Teste tentando realizar um PATCH de CPF usando o formato 999.999.999-99 e esperar um erro
- CTU2R2_005 Teste tentando realizar um PUT de CPF válido para o formato 999.999.999-99 e esperar um erro
- CTU2R2_006 Teste tentando realizar um POST de usando o formato 99999999999 e esperar um sucesso
- CTU2R2_007 Teste tentando realizar um POST de usando o formato 99999999999 sendo string e esperar um erro
- CTU2R2_008 Teste tentando realizar um PUT de CPF válido para o formato 99999999999 sendo string e esperar um erro
- CTU2R2_009 Teste tentando realizar um POST de usando o passando letras e outros caracteres esperar um erro
- CTU2R2_010 Teste tentando realizar um PUT de CPF valido o passando letras e outros caracteres esperar um erro

### R3
- CTU2R3_001 Teste tentando realizar um POST com o campo nome sendo um caractere em branco
- CTU2R3_002 Teste tentando realizar um POST com o campo nome sendo um valor nulo
- CTU2R3_003 Teste tentando realizar um POST com o campo nome sendo um caracteres invalido
- CTU2R3_004 Teste tentando realizar um POST com o campo nome sendo um apenas números
- CTU2R3_005 Teste tentando realizar um POST sem informar o campo nome
- CTU2R3_006 Teste tentando realizar um POST com um nome muito longo
- CTU2R3_007 Teste tentando realizar um POST com um nome sendo apenas 1 caractere
- CTU2R3_008 Teste tentando realizar um PUT com nome invalido

### R4
- CTU2R4_001 Teste tentando realizar um POST com um email válido (xx.xx@xx.xx)
- CTU2R4_002 Teste tentando realizar um POST com o campo email sendo um caractere em branco
- CTU2R4_003 Teste tentando realizar um POST com o campo email sendo um valor nulo
- CTU2R4_004 Teste tentando realizar um POST com o campo email sendo um caracteres invalido
- CTU2R4_005 Teste tentando realizar um POST com o campo email sendo um apenas números 
- CTU2R4_006 Teste tentando realizar um POST com email com excesso de caracteres aceitos
- CTU2R4_007 Teste tentando realizar um POST com email com o mínimo de caracteres aceitos
- CTU2R4_008 Teste tentando realizar um PUT com email invalido

### R5
- CTU2R5_001 Teste tentando realizar um POST com o campo valor sendo menor que 1000
- CTU2R5_002 Teste tentando realizar um POST com o campo valor sendo maior que 40000
- CTU2R5_003 Teste tentando realizar um POST com o campo valor sendo maior que 999 e menor que 40001
- CTU2R5_004 Teste tentando realizar um POST com o campo valor uma string 
- CTU2R6_005 Teste tentando realizar um PUT com o campo valor sendo menor 1000 ou maior 40000

### R6

- CTU2R6_001 Teste tentando realizar um POST com o campo parcelas sendo menor que 2
- CTU2R6_002 Teste tentando realizar um POST com o campo parcelas sendo maior que 48
- CTU2R6_003 Teste tentando realizar um POST com o campo parcelas sendo maior que 1 e menor que 49
- CTU2R6_004 Teste tentando realizar um POST com com o campo parcelas uma string
- CTU2R6_005 Teste tentando realizar um PUT com o campo parcelas sendo menor 2 ou maior 48

### R7
- CTU2R7_001 Teste tentando realizar um POST com o campo seguro não sendo um booleano
- CTU2R7_002 Teste tentando realizar um POST com o campo seguro sendo um booleano
- CTU2R7_003 Teste tentando realizar um POST com o campo seguro sendo string
- CTU2R7_004 Teste tentando realizar um PUT com o campo seguro não sendo um booleano 

### R8
- CTU2R8_001 Teste tentando realizar um POST passando um CPF já utilizado
- CTU2R8_002 Teste realizando um POST de uma simulação, tentando realizar outro POST no mesmo CPF, realizar um DELETE, e então realizar outro POST

### R9
- CTU2R9_001 Teste tentando realizar um POST e PUT com erros no body e esperar uma mensagem de erro

### R10
- CTU2R10_001 Teste tentando realizar um PUT em um CPF que não tem simulações cadastradas e esperar um erro

### R11
- CTU2R11_001 Caso não existam simulações cadastradas ao realizar um GET em /simulacoes um status code 204 deve ser retornado

### R12
- CTU2R12_001 Teste tentando realizar um DELETE em uma simulação pelo CPF, Nome, email ou outro dado do cadastro
- CTU2R12_002 Teste tentando realizar um DELETE em uma simulação pelo ID
- CTU2R12_003 Teste tentando realizar um DELETE em uma simulação pelo ID via query
- CTU2R12_004 Teste tentando realizar um DELETE em uma simulação pelo CPF via query
- CTU2R12_005 Teste tentando realizar um DELETE em uma simulação sem passar um valor e esperar que o banco de dados não seja deletado


## Testes candidatos a automação ![Automação Icon](img_plano_de_testes/automação.svg)

Esses testes são os que mais se repetem no processo de teste da aplicação, por causa disso sua automação pode ser necessária.

### US1

- CTU1R1_001 Teste consultando um CPF com restrição e esperar uma mensagem e o CPF consultado afirmando que ele está com restrição
- CTU1R1_002 Teste consultando um CPF sem restrição
- CTU1R1_003 Teste consultando um CPF com restrição e resposta deve conter um json com apenas strings
- CTU1R1_004 Teste consultando um CPF no padrão /99999999999
- CTU1R1_005 Teste consultando um CPF no padrão /999.999.999-99
- CTU1R1_006 Teste consultando um CPF em padrões fora do esperado (letras, caracteres inválidos, query entre outros)
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
- CTU1R5_002 Teste consultando um CPF sem restrição via POST
- CTU1R5_003 Teste consultando um CPF com restrição via PUT
- CTU1R5_004 Teste consultando um CPF sem restrição via PUT
- CTU1R5_005 Teste consultando um CPF com restrição via DELETE
- CTU1R5_006 Teste consultando um CPF sem restrição via PATCH
- CTU1R5_007 Teste consultando um CPF com restrição via PATCH

### US2

- CTU2R1_001 Teste tentando realizar um POST informando um CPF com restrição e esperar um erro
- CTU2R1_002 Teste tentando realizar um GET informando um CPF com restrição e esperar um erro
- CTU2R1_004 Teste tentando realizar um PUT em uma simulação que tem um CPF válido e tentar alterar ele para um CPF com restrição
---
- CTU2R2_001 Teste tentando realizar um POST de CPF usando o formato 999.999.999-99 e esperar um erro
- CTU2R2_002 Teste tentando realizar um GET de CPF usando o formato 999.999.999-99 e esperar um erro
- CTU2R2_005 Teste tentando realizar um PUT de CPF válido para o formato 999.999.999-99 e esperar um erro
- CTU2R2_006 Teste tentando realizar um POST de usando o formato 99999999999 e esperar um sucesso
- CTU2R2_007 Teste tentando realizar um POST de usando o formato 99999999999 sendo string e esperar um erro
- CTU2R2_008 Teste tentando realizar um PUT de CPF válido para o formato 99999999999 sendo string e esperar um erro
- CTU2R2_009 Teste tentando realizar um POST de usando o passando letras e outros caracteres esperar um erro
- CTU2R2_010 Teste tentando realizar um PUTde CPF valido o passando letras e outros caracteres esperar um erro
---
- CTU2R3_001 Teste tentando realizar um POST com o campo nome sendo um caractere em branco
- CTU2R3_003 Teste tentando realizar um POST com o campo nome sendo um caracteres invalido
- CTU2R3_004 Teste tentando realizar um POST com o campo nome sendo um apenas números
- CTU2R3_006 Teste tentando realizar um POST com um nome muito longo
- CTU2R3_007 Teste tentando realizar um POST com um nome sendo apenas 1 caractere
---
- CTU2R4_001 Teste tentando realizar um POST com um email válido (xx.xx@xx.xx)
- CTU2R4_002 Teste tentando realizar um POST com o campo email sendo um caractere em branco
- CTU2R4_003 Teste tentando realizar um POST com o campo nome sendo um valor nulo
- CTU2R4_004 Teste tentando realizar um POST com o campo nome sendo um caracteres invalido
- CTU2R4_006 Teste tentando realizar um POST com email com excesso de caracteres aceitos
- CTU2R4_008 Teste tentando realizar um PUT com email invalido
---
- CTU2R5_001 Teste tentando realizar um POST com o campo valor sendo menor que 1000
- CTU2R5_002 Teste tentando realizar um POST com o campo valor sendo maior que 40000
- CTU2R5_003 Teste tentando realizar um POST com o campo valor sendo maior que 999 e menor que 40001
- CTU2R5_005 Teste tentando realizar um PUT com o campo valor sendo menor 1000 ou maior 40000
---
- CTU2R6_001 Teste tentando realizar um POST com o campo parcelas sendo menor que 2
- CTU2R6_002 Teste tentando realizar um POST com o campo parcelas sendo maior que 48
- CTU2R6_003 Teste tentando realizar um POST com o campo parcelas sendo maior que 1 e menor que 49
- CTU2R6_005 Teste tentando realizar um PUT com o campo parcelas sendo menor 2 ou maior 48
---
- CTU2R8_001 Teste tentando realizar um POST passando um CPF já utilizado
- CTU2R8_002 Teste realizando um POST de uma simulação, tentando realizar outro POST no mesmo CPF, realizar um DELETE, e então realizar outro POST
---
- CTU2R9_001 Teste tentando realizar um POST e PUT com erros no body e esperar uma mensagem de erro
---
- CTU2R10_001 Teste tentando realizar um PUT em um CPF que não tem simulações cadastradas e esperar um erro
---
- CTU2R11_001 Caso não existam simulações cadastradas ao realizar um GET em /simulacoes um status code 204 deve ser retornado
---
- CTU2R12_001 Teste tentando realizar um DELETE em uma simulação pelo CPF, Nome, email ou outro dado do cadastro
- CTU2R12_002 Teste tentando realizar um DELETE em uma simulação pelo ID
- CTU2R12_005 Teste tentando realizar um DELETE em uma simulação sem passar um valor e esperar que o banco de dados não seja deletado


## Escopo:

Funções de Simulação de créditos, principalmente funções como, consultar CPFs, consultar e realizar, atualizar e deletar simulações de créditos.


## Cobertura

Restrições (GET)

Simulações (GET, GET{id}, POST, PUT, DELETE)

Testes manuais testando outras rotas e funções básicas da API.


## DoR

US 001: [API] Restrições
- Banco de dados e infraestrutura para desenvolvimento disponibilizados;
- Ambiente de testes disponibilizado;
- Massa de dados com lista de CPFs com restrições disponíveis para testes.

US 002: [API] Simulações
- Banco de dados e infraestrutura para desenvolvimento disponibilizados;
- Ambiente de testes disponibilizado;
- Massa de dados com lista de CPFs com restrições disponíveis para testes.


## DoD

US 001: [API] Restrições
- CRUD de Consultas de restrições, cobrindo o verbo GET
- Análise de testes cobrindo todos verbos;
- Matriz de rastreabilidade atualizada;
- Automação de testes baseado na análise realizada;
- Reports realizados e relatados.

US 002: [API] Simulações
- CRUD de Consultas de simulações, cobrindo o verbo GET, GET{id}, POST, PUT, DELETE
- Análise de testes cobrindo todos verbos;
- Matriz de rastreabilidade atualizada;
- Automação de testes baseado na análise realizada;
- Reports realizados e relatados.

### Links para materiais complementares

[Links Uteis Doc](https://docs.google.com/document/d/1V4Ivsur7kMg_KByYb0foMhyQZ419nKII07iXW0mnAxo/edit?usp=sharing)

[Mapa Mental Link](https://xmind.works/share/jbV437G7)
![Mapa Mental](img_plano_de_testes/API%20-%20Sicredi-Map%201.png)

### Pessoas que testaram ![Pessoas Icon](img_plano_de_testes/people.svg)

| Equipe pb_Sprint_6 |
| --- |
| Higor Milani |


### Ferramentas ![Ferramentas Icon](img_plano_de_testes/ferramenta.svg)
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

(Mais informações de versões no arquivo pom.xml na pasta raiz do projeto no GitLab )
[Veja o Pom.xml Aqui!](https://gitlab.com/higor_sprints/challenge-final-sprint-6/-/blob/Projeto_de_automa%C3%A7%C3%A3o/ChallengeFinal/pom.xml?ref_type=heads)


### Versão da aplicação

* Simulação de Crédito v1


![Sempre olhe para o Futuro](img_plano_de_testes/future.svg) 

![Restritro](img_plano_de_testes/r.png) Documento Restrito! ![Restritro](img_plano_de_testes/docRestrito.svg)