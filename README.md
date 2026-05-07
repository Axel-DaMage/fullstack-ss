# Sanos y Salvos - Sistema de Mascotas Perdidas

Plataforma para gestionar el registro, busqueda y reencontracion de mascotas perdidas y encontradas.

## Estado de Build

[![CI / Compilacion y testing](https://github.com/Axel-DaMage/fullstack-ss/actions/workflows/ci.yml/badge.svg)](https://github.com/Axel-DaMage/fullstack-ss/actions/workflows/ci.yml)
[![CD / Deploy a AWS](https://github.com/Axel-DaMage/fullstack-ss/actions/workflows/cd.yml/badge.svg)](https://github.com/Axel-DaMage/fullstack-ss/actions/workflows/cd.yml)

## Arquitectura

```
Frontend (NPM)
     |
  API Gateway
     |
 +----+----+----+
 |    |    |    |
Pet  Geo Match BFF
 |    |    |
 BD  BD   BD
```

## Microservicios

| Servicio | Puerto | Descripcion |
|----------|--------|--------------|
| Pet Service | 3001 | Gestion de mascotas |
| Geo Service | 3002 | Geolocalizacion |
| Match Service | 3003 | Motor de coincidencias |
| BFF | 8081 | Backend for Frontend |
| API Gateway | 8080 | Punto de entrada |

## Tech Stack

- Java 17 + Spring Boot
- Maven
- React + TypeScript
- MySQL
- Liquibase
- Docker

## Estructura

```
backend/
  pet-service/    # Microservicio de mascotas
  geo-service/    # Microservicio de ubicaciones
  match-service/  # Microservicio de coincidencias
  bff/            # Backend for Frontend
  api-gateway/    # API Gateway

frontend/
  src/components/ # Componentes NPM
```

## Primeros Pasos

### Con Docker

```bash
docker compose up --build
```

### Desarrollo

```bash
# Backend
cd backend/pet-service
mvn spring-boot:run
```

```bash
# Frontend
cd frontend
npm install
npm run dev
```

## Endpoints

- `/api/pets` - Mascotas
- `/api/locations` - Ubicaciones
- `/api/matching` - Coincidencias

## GitHub Actions

- CI: Compila y ejecuta tests en cada push
- CD: Despliega a AWS en push a master