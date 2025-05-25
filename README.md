# MonitoringMottu

## 📋 Descrição do Projeto

O projeto **MonitoringMottu** é uma aplicação desenvolvida em **Java com Spring Boot** que tem como objetivo monitorar a frota de motos da empresa Mottu. A aplicação permite o gerenciamento de motos, pátios e posicionamentos geográficos, contribuindo para maior controle logístico e operacional da empresa.

## 👨‍💻 Integrantes

- **Caetano Penafiel** — RM: 557984  
- **Victor Egidio** — RM: 556653  
- **Kauã Zipf** — RM: 558957

## 🚨 Endpoints da API

### 🛵 `/motos`
- `GET /motos` — Listar todas as motos  
- `POST /motos` — Cadastrar uma nova moto  
- `GET /motos/{id}` — Buscar moto por ID  
- `PUT /motos/{id}` — Atualizar moto  
- `DELETE /motos/{id}` — Remover moto

### 🏢 `/patios`
- `GET /patios` — Listar todos os pátios  
- `POST /patios` — Cadastrar um novo pátio  
- `GET /patios/{id_patio}` — Buscar pátio por ID  
- `PUT /patios/{id_patio}` — Atualizar pátio  
- `DELETE /patios/{id_patio}` — Remover pátio

### 📍 `/posicionamentos`
- `GET /posicionamentos` — Listar todos os posicionamentos  
- `POST /posicionamentos` — Registrar novo posicionamento  

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos

- Java 17+  
- Maven 3.8+  
- Banco de dados Oracle (ou outro, configurável)

### ⚙️ Execução

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/MonitoringMottu.git

   cd MonitoringMottu

2. **Execute a aplicação**

```bash
./mvnw spring-boot:run

Ou utilize sua IDE favorita para executar a classe principal do projeto.

