# Web Services com Spring Boot, JPA e Hibernate

## Descrição
Este projeto tem como objetivo criar uma aplicação web utilizando Spring Boot com JPA/Hibernate para implementação de serviços REST. Ele abrange a estruturação das camadas lógicas, configuração do banco de dados de teste (H2) e o desenvolvimento das operações CRUD.

## Funcionalidades
- Criar projeto Spring Boot Java
- Implementar modelo de domínio
- Estruturar camadas lógicas: Resource, Service, Repository
- Configurar banco de dados de teste (H2)
- Povoar o banco de dados
- Operações CRUD (Create, Retrieve, Update, Delete)
- Tratamento de exceções

## Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 Database (ambiente de testes)
- PostgreSQL (deploy)
- Heroku (deploy)
- Maven

## Estrutura do Projeto
O projeto segue uma estrutura MVC adaptada para o uso de serviços REST:
```
/src/main/java/com/example/project
  |-- entities
  |-- repositories
  |-- services
  |-- resources
```

## Configuração e Execução

### Clonando o Repositório
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### Configurando Banco de Dados (H2 - Ambiente de Testes)
No arquivo `application.properties`, defina as configurações:
```properties
spring.profiles.active=test
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

### Executando o Projeto
Para rodar a aplicação, utilize o seguinte comando:
```bash
mvn spring-boot:run
```
A aplicação estará disponível em `http://localhost:8080`

### Deploy no Heroku
Para realizar o deploy no Heroku:
1. Crie uma conta e um novo app no Heroku.
2. Provisionar um banco de dados PostgreSQL.
3. Configurar variáveis de ambiente:
   - `DATABASE_URL`
   - `JWT_SECRET`
   - `JWT_EXPIRATION`
4. Configurar o perfil de produção (`application-prod.properties`):
```properties
spring.datasource.url=${DATABASE_URL}
spring.jpa.hibernate.ddl-auto=none
```
5. Fazer o deploy com os comandos:
```bash
git add .
git commit -m "Deploy Heroku"
git push heroku main
```

## API Endpoints
- `GET /users` - Lista todos os usuários
- `GET /users/{id}` - Busca um usuário por ID
- `POST /users` - Cadastra um novo usuário
- `PUT /users/{id}` - Atualiza os dados de um usuário
- `DELETE /users/{id}` - Remove um usuário

## Autor
Projeto baseado no curso [Java COMPLETO](https://devsuperior.com.br) do professor [Dr. Nelio Alves](https://github.com/acenelio).

## Licença
Este projeto está sob a licença MIT - veja o arquivo LICENSE para mais detalhes.

