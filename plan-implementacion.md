# Plan de Implementación - Sistema de Mascotas Perdidas

## Descripción del Proyecto

Plataforma para gestionar el registro, búsqueda y reencontración de mascotas perdidas y encontradas, conectando a dueñas/os con quienes encontramien animales varados.

### Microservicios

| Servicio | Descripción | Base de Datos |
|----------|-------------|---------------|
| **Pet Service** | Gestión de mascotas (CRUD, características físicas, fotografías) | Propia |
| **Location Service** | Geolocalización y zonas de incidencia | Propia |
| **Matching Service** | Motor de coincidencias (se comunica con Pet Service y Location Service) | Propia |

### Arquitectura

```
                    [Frontend - NPM]
                           │
                    [API Gateway]
                           │
            ┌──────────────┼──────────────┐
            │              │              │
      [Pet Service]   [Location S.]   [Matching S.]
            │              │              │
        [BD Pet]      [BD Location]   [BD Matching]
```

---

## Objetivos por Etapa

### Etapa 1: Fundamentos y Estructura (Semana 1-2)

#### Objetivos Técnicos
- [x] Configurar repositorio principal en GitHub
- [x] Crear estructura de directorios para frontend y backend
- [ ] Implementar arquetipos Maven para microservicios
- [x] Configurar API Gateway con Spring Cloud Gateway

#### Objetivos de Componentes
- [ ] Component NPM para frontend
- [ ] BFF (Backend For Frontend)
- [x] 3 microservicios base

#### Patrones de Diseño a Implementar
- [x] Factory Method (para creación de entidades)
- [ ] Singleton (para servicios de configuración)
- [x] Repository Pattern (capa de acceso a datos)

#### Arquetipos y Arquitectura
- [ ] Arquetipo Maven para BFF
- [ ] Arquetipo Maven para microservicios
- [x] Patrón arquitectónico: Microservices

---

### Etapa 2: Pet Service (Semana 3)

#### Objetivos CRUD
- [x] **GET** - Obtener todas las mascotas
- [x] **GET** - Obtener mascota por ID
- [x] **POST** - Registrar nueva mascota
- [x] **PUT** - Actualizar datos de mascota
- [x] **DELETE** - Eliminar mascota

#### Objetivos Adicionales (2 endpoints)
- [x] Búsqueda por raza
- [x] Búsqueda por estado (perdida/encontrada)
- [x] Totales de mascotas por estado

#### Base de Datos
- [x] Tabla: `pets` (id, name, race, color, size, status, description, photo_url, contact_id, created_at, updated_at)
- [x] Tabla: `contacts` (id, name, phone, email, address) - Relacionada con pets
- [x] Liquibase para creación de tablas y 10 registros

#### Estructura Spring
- [x] PetController
- [x] PetService
- [x] PetRepository
- [x] Pet (Entity)

---

### Etapa 3: Location Service (Semana 4)

#### Objetivos CRUD
- [x] **GET** - Obtener todas las ubicaciones
- [x] **GET** - Obtener ubicación por ID
- [x] **POST** - Registrar nueva ubicación
- [x] **PUT** - Actualizar ubicación
- [x] **DELETE** - Eliminar ubicación

#### Objetivos Adicionales (2 endpoints)
- [x] Búsqueda por zona/barrio
- [x] Búsqueda por rango de fechas
- [x] Totales de reportes por zona

#### Base de Datos
- [x] Tabla: `locations` (id, pet_id, latitude, longitude, zone, address, reported_at)
- [x] Tabla: `zones` (id, name, city, incidence_count) - Relacionada con locations
- [x] Liquibase para creación de tablas y 10 registros

#### Estructura Spring
- [x] LocationController
- [x] LocationService
- [x] LocationRepository
- [x] Location (Entity)

---

### Etapa 4: Matching Service (Semana 5)

#### Objetivos CRUD
- [x] **GET** - Obtener todos los matches
- [x] **GET** - Obtener match por ID
- [x] **POST** - Crear match manualmente
- [x] **PUT** - Actualizar estado del match
- [x] **DELETE** - Eliminar match

#### Objetivos Adicionales (2 endpoints)
- [x] Búsqueda por porcentaje de coincidencia
- [x] Búsqueda por estado (pendiente/confirmado)
- [x] Totales de matches por estado

#### Comunicación entre Microservicios
- [x] Consume Pet Service para obtener datos de mascotas
- [ ] Consume Location Service para obtener ubicaciones
- [x] Implementar cliente REST para comunicación

#### Base de Datos
- [x] Tabla: `matches` (id, pet_lost_id, pet_found_id, match_percentage, status, created_at)
- [x] Tabla: `match_criteria` (id, match_id, criteria_name, score) - Relacionada con matches

---

### Etapa 5: API Gateway e Integración (Semana 6)

#### Objetivos
- [x] Configurar rutas en API Gateway para cada microservicio
- [ ] Implementar balanceador de carga
- [ ] Configurar fallbacks (circuit breaker)
- [ ] Documentar endpoints

#### Rutas
- `/api/pets/**` -> Pet Service
- `/api/locations/**` -> Location Service
- `/api/matching/**` -> Matching Service

---

### Etapa 6: Frontend y BFF (Semana 7)

#### BFF (Backend For Frontend)
- [ ] Crear proyecto con arquetipo Maven
- [ ] Implementar aggregation pattern
- [ ] Exponer endpoints unificados para frontend
- [ ] Gestionar comunicación con microservicios

#### Frontend (NPM)
- [ ] Componente para listar mascotas
- [ ] Componente para registrar mascota
- [ ] Componente para ver mapa de zonas
- [ ] Componente para ver matches
- [ ] Integración con BFF

#### Patrones de Diseño Frontend
- [ ] Observer (para estado de la aplicación)
- [ ] Factory (para creación de componentes)
- [ ] Singleton (para servicios API)

---

### Etapa 7: Pruebas y Calidad (Semana 8)

#### Objetivos
- [ ] Implementar pruebas unitarias en cada microservicio
- [ ] Implementar pruebas unitarias en BFF
- [ ] Implementar pruebas unitarias en componentes frontend
- [ ] Documentar cobertura de código
- [ ] Validar que todas las pruebas pasen

#### Herramientas
- [ ] JUnit para Java
- [ ] Jest para JavaScript/TypeScript

---

### Etapa 8: Infraestructura y Despliegue (Semana 9-10)

#### Objetivos de Infraestructura (una de las opciones)

**Opción A - Nube (al menos 3):**
- [ ] EC2 (instancia para despliegue)
- [ ] Docker (contenedores)
- [ ] RDS (base de datos)
- [ ] ECR (repositorio de imágenes)
- [ ] ECS (orquestación)

**Opción B - Docker Local:**
- [ ] Contenedor Base de Datos
- [ ] Contenedor BFF
- [ ] Contenedor Microservicios
- [ ] Contenedor Frontend
- [ ] Ejecutar en al menos 2 PCs distintos

---

### Etapa 9: Documentación de Presentación (Semana 11)

#### Objetivos
- [ ] Estrategia de branching documentada
- [ ] Commits con mensajes claros
- [ ] Merges documentados
- [ ] Resolución de conflictos registrada

#### Entregables de Documentación
- [ ] **PDF: Análisis de Patrones y Arquetipos** - Justificación de patrones de diseño y arquetipos seleccionados
- [ ] **PDF: Plan de Branching** - Estrategia detallada de ramas, merges y resolución de conflictos
- [ ] **repositorios.txt** - Enlaces a todos los repositorios GitHub

#### Estrategia de Branching
```
main
  ├── develop
  │   ├── feature/pet-service
  │   ├── feature/location-service
  │   ├── feature/matching-service
  │   ├── feature/bff
  │   └── feature/frontend
  └── hotfix/
```

---

### Etapa 10: Entrega Final (Semana 12)

#### Archivo Comprimido (ZIP/RAR)
- [ ] Documentación (PDF Análisis de Patrones, PDF Plan de Branching)
- [ ] Componentes frontend NPM
- [ ] BFF con archivos de configuración
- [ ] 3 Microservicios con archivos de configuración
- [ ] Arquetipos Maven (código fuente)
- [ ] Archivo repositorios.txt
- [ ] README.md en cada componente

---

## Objetivos Técnicos por Servicio

### Pet Service
| Requisito | Estado |
|-----------|--------|
| Propia base de datos | ✅ |
| CRUD completo | ✅ |
| 2 endpoints adicionales | ✅ |
| Liquibase con 10+ registros | ✅ |
| Tablas relacionadas | ✅ |

### Location Service
| Requisito | Estado |
|-----------|--------|
| Propia base de datos | ✅ |
| CRUD completo | ✅ |
| 2 endpoints adicionales | ✅ |
| Liquibase con 10+ registros | ✅ |
| Tablas relacionadas | ✅ |

### Matching Service
| Requisito | Estado |
|-----------|--------|
| Propia base de datos | ✅ |
| CRUD completo | ✅ |
| 2 endpoints adicionales | ✅ |
| Comunica con Pet Service | ✅ |

---

## Bonos Track

| Bono | Servicio | Estado |
|------|----------|--------|
| Flyway | Por definir | ⬜ |
| CI/CD desde GitHub | Todos | ⬜ |
| JWT en microservicios | Todos | ⬜ |

---

## Entregables por Semana

| Semana | Entregable |
|--------|------------|
| 1-2 | Repositorio GitHub, arquetipos Maven, estructura base, API Gateway |
| 3 | Pet Service completo con BD (Liquibase, tablas relacionadas) |
| 4 | Location Service completo con BD (Liquibase, tablas relacionadas) |
| 5 | Matching Service con comunicación (consume Pet y Location) |
| 6 | API Gateway configurado con rutas y fallbacks |
| 7 | BFF y Frontend NPM |
| 8 | Pruebas unitarias y cobertura de código |
| 9-10 | Infraestructura y despliegue (Docker) |
| 11 | Documentación (PDF Patrones, PDF Branching, repositorios.txt) |
| 12 | Archivo ZIP/RAR final y entrega a BlackBoard |

---

## Cronograma General

```
Semana 1-2:  ██████████
Semana 3:    ██████
Semana 4:    ██████
Semana 5:    ██████
Semana 6:    ██████
Semana 7:    ██████████
Semana 8:    ██████
Semana 9-10: ██████████
Semana 11:   ██████
Semana 12:   ██████
```

---

## Recursos y Tecnologías

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Cloud Gateway
- Maven
- Liquibase
- PostgreSQL

### Frontend
- TypeScript
- React
- NPM

### Infraestructura
- Docker
- GitHub Actions (CI/CD)
- JWT para autenticación