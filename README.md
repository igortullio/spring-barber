# Spring Barber

API REST para cadastro de barbearias e agendamento de horários, desenvolvida com Spring Boot e arquitetura de permissões baseada em grupos.

## Tecnologias

- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Swagger / OpenAPI

## Diagrama de tabelas

![Spring Barber](https://user-images.githubusercontent.com/20797306/150369005-98e0fccd-4fe1-4261-9eda-c5edd3557254.png)

## Funcionalidades

- Cadastro e autenticação de usuários (JWT)
- CRUD de barbearias com endereço e geolocalização
- Gerenciamento de horários de funcionamento por dia da semana
- Agendamento com confirmação e cancelamento
- Sistema de permissões por grupo
- CRUD de estados e cidades

## Como executar

```bash
git clone https://github.com/igortullio/spring-barber
cd spring-barber
./mvnw spring-boot:run
```

## API

Documentação completa dos endpoints disponível no repositório (collections Postman/Insomnia).

### Principais recursos

| Recurso | Métodos |
|---------|---------|
| `/auth/login` | POST |
| `/users` | GET, POST, PUT, DELETE |
| `/barbershops` | GET, POST, PUT, DELETE |
| `/operations` | GET, POST, PUT, DELETE |
| `/schedules` | GET, POST, PUT, DELETE, PUT (confirm/cancel) |
| `/states` | GET, POST, PUT, DELETE |
| `/cities` | GET, POST, PUT, DELETE |
| `/addresses` | GET, POST, PUT, DELETE |
| `/groups` | GET, POST, PUT, DELETE |
| `/permissions` | GET, POST, PUT, DELETE |