# Parking System (Estacionamento PUCRS)

![Java](https://img.shields.io/badge/Java-21-blue)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-6DB33F)
![Vaadin](https://img.shields.io/badge/Vaadin-24.3.7-00B4F0)

Java-based parking management system designed to support software development courses. The main project lives in [EstacionamentoPUCRS](EstacionamentoPUCRS) and includes a Vaadin web UI, business rules for customer profiles, and a financial flow with usage history.

## What The Project Does

- Manages vehicle entry and exit in the parking lot.
- Tracks parking occupancy (configured capacity of 500 spots).
- Registers and updates customers by profile:
	- Student
	- PUCRS Staff
	- Tecnopuc
- Applies billing rules based on customer type.
- Generates and tracks financial data (invoices, credit top-ups, total revenue).
- Maintains usage history for management insights.

## Why This Project Is Useful

- Ready-to-use foundation for classes and exercises with Java 21, Maven, Spring Boot, and Vaadin.
- Clear separation between domain and web UI layers, making the code easier to evolve.
- Includes real-world object-oriented modeling scenarios (inheritance and polymorphism by customer type).
- Lets students explore file-based persistence and business rules in a practical context.

## Repository Structure

- [EstacionamentoPUCRS](EstacionamentoPUCRS): main application (Spring Boot + Vaadin).
- [create-project.sh](create-project.sh): script to generate a basic Maven/Java project.
- [create-vaadin-project.sh](create-vaadin-project.sh): script to generate a Maven/Spring Boot/Vaadin project.

## How To Get Started

### Prerequisites

- Java 21
- Maven 3.9+

### 1) Clone And Enter The Main Project

```bash
git clone https://github.com/brunopedralli/parking-system.git
cd parking-system/EstacionamentoPUCRS
```

### 2) Build

```bash
mvn clean install
```

### 3) Run

```bash
mvn spring-boot:run
```

Application available at:

- http://localhost:8080

### Quick Usage Example

1. Open the Home screen and go to Clientes to register a customer.
2. Go to Estacionamento to register a vehicle entry or exit.
3. Check Financeiro and Gerencial for revenue and reporting views.

## Scripts To Create New Projects

### Basic Java Project

```bash
chmod +x create-project.sh
./create-project.sh com.example my-project
```

### Vaadin + Spring Boot Project

```bash
chmod +x create-vaadin-project.sh
./create-vaadin-project.sh com.example my-project
```

## Where To Get Help

- Read the main module guide: [EstacionamentoPUCRS/README.md](EstacionamentoPUCRS/README.md).
- Check the official documentation:
	- Spring Boot: https://docs.spring.io/spring-boot/docs/current/reference/html/
	- Vaadin: https://vaadin.com/docs

## Maintainers And Contributions

Maintainer:

- [@brunopedralli](https://github.com/brunopedralli)
