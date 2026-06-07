# Parking System

![Java](https://img.shields.io/badge/Java-21-blue)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-6DB33F)
![Vaadin](https://img.shields.io/badge/Vaadin-24.3.7-00B4F0)

A web-based parking management system built for PUCRS university, handling vehicle access control, client account management, and billing for three client categories.

## Features

- **Vehicle entry/exit tracking** with real-time occupancy display (500-space lot)
- **Three client types** with distinct billing models:
  - **Students (Estudante):** Pre-paid credit system (R$15/hour; credits reload in fixed denominations)
  - **PUCRS Employees (Funcionário PUCRS):** Free parking
  - **Tech Park Tenants (Tecnopuc):** Hourly billing (R$1.50/hr) with monthly invoice generation
- **Client management:** Register, edit, and search clients by CPF; up to 2 vehicles per student/employee account
- **Financial reports:** Total revenue, invoice payment tracking, credit recharge history
- **Management reports:** Usage history filtered by client and date range; full entry/exit log

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+

Or use the included Dev Container (requires Docker and VS Code with the Dev Containers extension).

### Running Locally

```bash
git clone <repo-url>
cd parking-system
mvn spring-boot:run
```

Open your browser at `http://localhost:8080`.

### Dev Container

1. Open the repository in VS Code
2. When prompted, click **Reopen in Container** (or run `Dev Containers: Reopen in Container` from the command palette)
3. The container configures Java 21, Maven, and all required VS Code extensions automatically
4. Run `mvn spring-boot:run` inside the container terminal

### Build

```bash
mvn clean install
```

## Project Structure

```
parking-system/
├── src/main/java/pucrs/poo/estacionamento/
│   ├── modelo/          # Business logic (clients, parking service, invoices, history)
│   └── vaadin/          # Vaadin UI views (entry/exit, registration, reports, finance)
├── clientes.dat         # Persisted client records
├── entradas.dat         # Active vehicle entries
├── TicketsLog.dat       # Historical parking records
└── pom.xml
```

Data is persisted in flat CSV-like `.dat` files loaded at startup — no external database required.

## Navigation

```
Home
├── Clientes         — Register and edit clients
├── Estacionamento   — Vehicle check-in / check-out
├── Financeiro       — Credit recharge, invoice generation, revenue report
└── Gerencial        — Usage report, full entry log
```

## Maintainers And Contributions

- Developed by:
[@brunopedralli](github.com/brunopedralli), [@joaopiece](github.com/joaopiece), [@dajosohn](github.com/dajosohn)

- Maintainer:
[@brunopedralli](github.com/brunopedralli)
