# 📺 API REST de Animes com Spring Boot

Projeto de backend desenvolvido com Java 17 e Spring Boot que expõe uma API RESTful para gerenciamento de animes. Permite criar, listar, atualizar e deletar registros no banco de dados MySQL. A aplicação segue boas práticas de arquitetura em camadas, com uso de DTOs, tratamento global de exceções e documentação automática via Swagger.

---

## 🚀 Tecnologias Utilizadas

- Java 17  
- Spring Boot  
- Spring Data JPA  
- MySQL  
- Docker & Docker Compose  
- MapStruct  
- Lombok  
- Swagger / OpenAPI  

---

## 🧱 Estrutura do Projeto

- **Controller**: camada responsável pelas requisições HTTP  
- **Service**: lógica de negócio central da aplicação  
- **Repository**: acesso ao banco de dados via JPA  
- **Domain (Entity)**: representação das tabelas do banco  
- **DTOs**: transporte de dados com validação  
- **Mapper**: conversão entre DTOs e entidades usando MapStruct  
- **Exception Handling**: tratamento global de erros personalizados  
- **Config**: configuração do Swagger para documentação da API  

---

## 🎯 Objetivo

Este projeto foi criado com fins educacionais e tem como objetivo praticar:

- Estruturação de uma API REST com boas práticas  
- Separação de responsabilidades por camadas  
- Integração de banco de dados com Docker  
- Aplicação de recursos modernos do ecossistema Spring  

---

Fique à vontade para clonar, testar, sugerir melhorias ou deixar uma ⭐ no repositório!

---

### 🏷️ Tags

#Java #SpringBoot #Docker #MySQL #API #Backend #Desenvolvimento #Estudo #Portfólio
