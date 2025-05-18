# Solari - Inventory Service

Este microsserviço é responsável por gerenciar o estoque de produtos do sistema, incluindo operações de criação, consulta, atualização e exclusão de inventários. Ele faz parte do sistema de gerenciamento de pedidos do projeto **Solari**, desenvolvido no **Tech Challenge - Fase 4** da pós-graduação em Arquitetura e Desenvolvimento Java - FIAP.

---

## 🧩 Tecnologias Utilizadas

- **Java 21**: Linguagem principal do projeto
- **Spring Boot**: Framework para criação de aplicações Java
- **Maven**: Gerenciador de dependências e build
- **Flyway**: Controle de versionamento do banco de dados
- **JPA / Hibernate**: Persistência de dados
- **Docker**: Containerização da aplicação
- **JaCoCo**: Análise de cobertura de testes
- **JUnit 5 + Mockito**: Frameworks para testes unitários e mocks

---

## 🧱 Estrutura do Projeto

O projeto segue a arquitetura hexagonal, dividindo responsabilidades em camadas bem definidas:

- **application**: Contém os casos de uso e regras de negócio
- **domain**: Representa as entidades e objetos de domínio
- **infrastructure**: Implementações de gateways, repositórios, controladores e configurações
- **tests**: Testes unitários e de integração

---

## 🚀 Como executar localmente

### Pré-requisitos

- Java 21+
- Maven
- Docker

### Passos

**1. Clonar o repositório**  
git clone https://github.com/BrunaCasagrande/solari-inventory-service.git  
cd solari-inventory-service

**2. Executar o projeto com Maven**  
mvn spring-boot:run

---

## 📌 Endpoints Principais

### Inventário

- **POST** `/solari/v1/inventory`  
  Cria um novo inventário

- **GET** `/solari/v1/inventory/id/{id}`  
  Busca um inventário pelo ID

- **GET** `/solari/v1/inventory/sku/{sku}`  
  Busca todos os inventários pelo SKU

- **PUT** `/solari/v1/inventory/sku/{sku}`  
  Atualiza a quantidade de inventários pelo SKU

- **DELETE** `/solari/v1/inventory/{id}`  
  Remove um inventário pelo ID

---

## ✅ Testes

Para executar os testes e gerar o relatório de cobertura com JaCoCo:

**1. Executar os testes**  
mvn test

**2. Gerar o relatório de cobertura**  
mvn verify

**3. Acessar o relatório JaCoCo**  
target/site/jacoco/index.html

---

## 🐳 Executando com Docker

**Build da imagem Docker**  
docker build -t solari-inventory-service .

**Executar o container**  
docker run -p 8084:8084 solari-inventory-service

**Acessar a aplicação**  
http://localhost:8084/solari/v1/inventory

---

## 👩‍💻 Autor

Desenvolvido por **Bruna Casagrande RM: 359536 ** – como parte do projeto **Solari**
"""

