# Sanos y Salvos — Sistema de Mascotas Perdidas

Plataforma para gestionar el registro, busqueda y reencuentro de mascotas perdidas y encontradas. Arquitectura de microservicios con frontend React, API Gateway, BFF y 3 microservicios independientes con base de datos propia.

## Arquitectura

```
Usuario
   |
Frontend (React + Nginx :80)
   | /api/*
API Gateway (Spring Cloud Gateway :8080)
   | JWT Auth + Circuit Breaker + Retry
   +----+----+----+----+
   |    |    |    |    |
  BFF  Pet  Geo  Match
(:8081)(:3001)(:3002)(:3003)
   |    |    |    |
  MySQL MySQL MySQL
 (3306)(3307)(3308)
```

## CI/CD Pipeline

```
GitHub Repo → GitHub Actions → Build Docker Image → Docker Hub (d4mag3/) → AWS EC2 (docker-compose)
```

Cada repositorio tiene su propio `docker.yml`. Push a `main` → build automatico → push a Docker Hub → EC2 hace `docker-compose pull && up -d`.

## Microservicios

| Servicio | Puerto | Stack | Docker Hub |
|----------|--------|-------|------------|
| **Frontend** | 80 (Nginx) | React 18 + TypeScript + Vite | `d4mag3/frontend` |
| **API Gateway** | 8080 | Spring Cloud Gateway + JWT + Resilience4j | `d4mag3/api-gateway` |
| **BFF** | 8081 | Spring Boot + RestTemplate | `d4mag3/bff` |
| **Pet Service** | 3001 | Spring Boot + JPA + Liquibase | `d4mag3/pet-service` |
| **Geo Service** | 3002 | Spring Boot + JPA + Liquibase | `d4mag3/geo-service` |
| **Match Service** | 3003 | Spring Boot + JPA + Liquibase | `d4mag3/match-service` |
| **Eureka Server** | 8761 | Spring Cloud Netflix | `d4mag3/eureka-server` |

## Bases de Datos

Cada microservicio tiene su propia base MySQL:

| Base | Tablas | Puerto |
|------|--------|--------|
| `pet_service` | pets, contacts, pet_report | 3306 |
| `geo_service` | locations, zones | 3307 |
| `match_service` | matches, match_criteria | 3308 |

## Requisitos

- Docker y Docker Compose
- Java 17+ (desarrollo)
- Node.js 18+ (desarrollo frontend)

## Inicio Rapido

### Con Docker Compose (produccion local)

```bash
docker compose up --build
```

### Desarrollo — Backend

```bash
# Cada microservicio individualmente
cd fullstack-ss-pet-service
mvn clean spring-boot:run
```

### Desarrollo — Frontend

```bash
cd fullstack-ss-frontend
npm install
npm run dev
```

## Pruebas

### Backend (cada microservicio)

```bash
mvn clean test           # solo pruebas
mvn clean verify         # pruebas + reporte JaCoCo
# Reporte: target/site/jacoco/index.html
```

### Frontend

```bash
npx vitest run                    # solo pruebas
npx vitest run --coverage         # pruebas + cobertura
# Reporte: coverage/index.html
```

**Total: 125 tests en 18 archivos de prueba.**

## Repositorios

| Componente | URL |
|------------|-----|
| Frontend | https://github.com/Axel-DaMage/fullstack-ss-frontend |
| BFF | https://github.com/Axel-DaMage/fullstack-ss-bff |
| Pet Service | https://github.com/Axel-DaMage/fullstack-ss-pet-service |
| Geo Service | https://github.com/Axel-DaMage/fullstack-ss-geo-service |
| Match Service | https://github.com/Axel-DaMage/fullstack-ss-match-service |
| API Gateway | https://github.com/Axel-DaMage/fullstack-ss-api-gateway |
| Docker Compose | https://github.com/Axel-DaMage/fullstack-ss |
