# Arquitectura del Sistema de Mascotas Perdidas

## Índice
1. [Visión General](#visión-general)
2. [Arquitectura de Microservicios](#arquitectura-de-microservicios)
3. [Componentes del Sistema](#componentes-del-sistema)
4. [Comunicación entre Servicios](#comunicación-entre-servicios)
5. [Base de Datos](#base-de-datos)
6. [Infraestructura](#infraestructura)

---

## 1. Visión General

### 1.1 Propósito del Sistema

Sistema para gestionar el registro, búsqueda y reencontración de mascotas perdidas y encontradas, conectando a dueñas/os con quienes encontramien animales varados.

### 1.2 Arquitectura General

```
                              [Frontend - React]
                                     │
                                     ▼
                             [API Gateway]
                              (Puerto 8080)
                                     │
         ┌────────────────────────────┼────────────────────────────┐
         │                            │                            │
         ▼                            ▼                            ▼
  [Pet Service]              [Geo Service]              [Match Service]
   (Puerto 8081)              (Puerto 8082)              (Puerto 8083)
         │                            │                            │
         ▼                            ▼                            ▼
    [DB Pet]                  [DB Geo]                  [DB Match]
   (MySQL 3306)              (MySQL 3307)              (MySQL 3308)
         │                            │                            │
         └────────────────────────────┴────────────────────────────┘
                                     │
                                     ▼
                               [BFF Service]
                               (Puerto 8084)
                                     │
                                     ▼
                              [Frontend NPM]
```

---

## 2. Arquitectura de Microservicios

### 2.1 Principios Aplicados

| Principio | Implementación |
|-----------|----------------|
| **Single Responsibility** | Cada servicio maneja un dominio específico |
| **Loose Coupling** | Comunicación via API REST |
| **High Cohesion** | Lógica de negocio encapsulada por servicio |
| **Autonomous** | Cada servicio tiene su propia base de datos |
| **Resilience** | Circuit Breaker en API Gateway |

### 2.2 Tecnologías del Backend

| Componente | Tecnología | Versión |
|------------|------------|---------|
| Framework | Spring Boot | 3.1.2 |
| Lenguaje | Java | 17+ |
| Gateway | Spring Cloud Gateway | 2022.0.4 |
| Persistencia | Spring Data JPA | - |
| Migraciones | Liquibase | - |
| Base de Datos | MySQL | 8.0 |
| Build | Maven | 3.9+ |

### 2.3 Tecnologías del Frontend

| Componente | Tecnología |
|------------|------------|
| Framework | React |
| Lenguaje | TypeScript/JavaScript |
| Build | Vite |
| Testing | Vitest/Jest |
| Componentes | NPM Packages |

---

## 3. Componentes del Sistema

### 3.1 API Gateway

**Ubicación:** `backend/api-gateway/`

**Responsabilidades:**
- Punto único de entrada para el frontend
- Ruteo de requests a microservicios
- Balanceo de carga
- Circuit Breaker (fallbacks)
- Logging centralizado

**Puertos y Rutas:**
| Ruta | Servicio Destino |
|------|-----------------|
| `/api/pets/**` | http://pet-service:8081 |
| `/api/locations/**` | http://geo-service:8082 |
| `/api/matching/**` | http://match-service:8083 |

**Fallback Configuration:**
```java
@RestController
public class FallbackController {
    @GetMapping("/fallback/pets")
    public ResponseEntity<Map<String, String>> petServiceFallback()
    
    @GetMapping("/fallback/locations")
    public ResponseEntity<Map<String, String>> locationServiceFallback()
    
    @GetMapping("/fallback/matching")
    public ResponseEntity<Map<String, String>> matchServiceFallback()
}
```

### 3.2 Pet Service

**Ubicación:** `backend/pet-service/`

**Responsabilidades:**
- CRUD de mascotas
- Búsqueda por raza y estado
- Gestión de contactos
- Reportes y estadísticas

**Puerto:** 8081

**Endpoints:**
```
GET    /api/pets              - Listar todas las mascotas
GET    /api/pets/{id}        - Obtener mascota por ID
POST   /api/pets              - Crear nueva mascota
PUT    /api/pets/{id}        - Actualizar mascota
DELETE /api/pets/{id}        - Eliminar mascota
GET    /api/pets/race/{race} - Buscar por raza
GET    /api/pets/status/{status} - Buscar por estado
GET    /api/pets/reports/count - Conteo por estado
```

**Modelo de Datos - Pet:**
```java
@Entity
@Table(name = "pets")
public class Pet {
    private Long id;
    private String name;          // Nombre de la mascota
    private String race;           // Raza
    private String color;          // Color
    private String size;           // Tamaño (SMALL, MEDIUM, LARGE)
    private String status;         // LOST, FOUND, ADOPTED
    private String description;    // Descripción
    private String photoUrl;       // URL de foto
    private Contact contact;       // Contacto del dueño
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

**Modelo de Datos - Contact:**
```java
@Entity
@Table(name = "contacts")
public class Contact {
    private Long id;
    private String name;      // Nombre del dueño
    private String phone;     // Teléfono
    private String email;     // Email
    private String address;   // Dirección
}
```

### 3.3 Geo Service (Location Service)

**Ubicación:** `backend/geo-service/`

**Responsabilidades:**
- CRUD de ubicaciones
- Gestión de zonas
- Búsqueda geográfica

**Puerto:** 8082

**Endpoints:**
```
GET    /api/locations              - Listar todas las ubicaciones
GET    /api/locations/{id}        - Obtener ubicación por ID
POST   /api/locations              - Crear nueva ubicación
PUT    /api/locations/{id}        - Actualizar ubicación
DELETE /api/locations/{id}        - Eliminar ubicación
GET    /api/locations/zone/{zone} - Buscar por zona
GET    /api/locations/dates       - Buscar por rango de fechas
GET    /api/locations/reports/zones - Reporte por zona
```

**Modelo de Datos - Location:**
```java
@Entity
@Table(name = "locations")
public class Location {
    private Long id;
    private Long petId;           // ID de la mascota
    private Double latitude;       // Latitud
    private Double longitude;      // Longitud
    private String zone;           // Zona/Barrio
    private String address;        // Dirección
    private LocalDateTime reportedAt; // Fecha de reporte
}
```

**Modelo de Datos - Zone:**
```java
@Entity
@Table(name = "zones")
public class Zone {
    private Long id;
    private String name;           // Nombre de la zona
    private String city;           // Ciudad
    private Integer incidenceCount; // Conteo de incidencia
}
```

### 3.4 Match Service

**Ubicación:** `backend/match-service/`

**Responsabilidades:**
- CRUD de matches
- Cálculo de coincidencias
- Comunicación con Pet Service y Geo Service
- Gestión de criterios de matching

**Puerto:** 8083

**Endpoints:**
```
GET    /api/matching              - Listar todos los matches
GET    /api/matching/{id}        - Obtener match por ID
POST   /api/matching              - Crear match manualmente
PUT    /api/matching/{id}        - Actualizar estado
DELETE /api/matching/{id}        - Eliminar match
GET    /api/matching/percentage/{min} - Buscar por %
GET    /api/matching/status/{status} - Buscar por estado
GET    /api/matching/reports/status - Reporte por estado
```

**Modelo de Datos - Match:**
```java
@Entity
@Table(name = "matches")
public class Match {
    private Long id;
    private Long petLostId;       // ID de mascota perdida
    private Long petFoundId;       // ID de mascota encontrada
    private Integer matchPercentage; // Porcentaje de coincidencia
    private String status;         // PENDING, CONFIRMED, REJECTED
    private LocalDateTime createdAt;
}
```

**Modelo de Datos - MatchCriteria:**
```java
@Entity
@Table(name = "match_criteria")
public class MatchCriteria {
    private Long id;
    private Long matchId;
    private String criteriaName;   // nombre del criterio
    private Integer score;          // puntaje (0-100)
}
```

### 3.5 BFF (Backend For Frontend)

**Ubicación:** `backend/bff/`

**Responsabilidades:**
- Agregación de datos de múltiples servicios
- Transformación de respuestas
- Exposición de endpoints unificados para frontend

**Puerto:** 8084

**Endpoints:**
```
GET    /api/bff/dashboard    - Datos completos del dashboard
GET    /api/bff/pets         - Listado de mascotas
GET    /api/bff/matches      - Listado de matches
```

**Arquitectura del BFF:**
```
BffController
       │
       ▼
AggregationService
       │
   ┌───┴───┐
   ▼       ▼
PetServiceClient  LocationServiceClient  MatchServiceClient
```

### 3.6 Frontend

**Ubicación:** `frontend/`

**Componentes principales:**
- Dashboard
- PetsList
- PetCard
- MatchesList

**Frontend Components NPM:**
- `frontend-components/`
- Componentes reutilizables publicados como paquete NPM

---

## 4. Comunicación entre Servicios

### 4.1 Comunicación Sincrónica (REST)

**Pet Service consume Pet Service:**
```java
@RestController
public class PetServiceClient {
    @GetMapping("http://pet-service:8081/api/pets/{id}")
    public PetDto getPetById(Long id)
}
```

**Match Service consume Location Service:**
```java
@RestController
public class LocationServiceClient {
    @GetMapping("http://geo-service:8082/api/locations/pet/{petId}")
    public List<LocationDto> getLocationsByPetId(Long petId)
}
```

### 4.2 Patrones de Comunicación

| Patrón | Uso | Implementación |
|--------|-----|----------------|
| **REST** | Comunicación estándar | HTTP/JSON |
| **Circuit Breaker** | Tolerancia a fallos | FallbackController |
| **Client Side** | Consumo de servicios | RestTemplate |

### 4.3 Formato de Datos

**JSON Request/Response:**
```json
// Pet
{
  "id": 1,
  "name": "Max",
  "race": "Golden Retriever",
  "color": "Dorado",
  "size": "LARGE",
  "status": "LOST",
  "description": "Collar azul, muy amigable",
  "photoUrl": "https://...",
  "contact": {
    "name": "Juan Pérez",
    "phone": "+1234567890",
    "email": "juan@email.com"
  },
  "createdAt": "2024-01-15T10:30:00"
}
```

---

## 5. Base de Datos

### 5.1 Estructura de Bases de Datos

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│    DB Pet       │     │    DB Geo       │     │    DB Match     │
│   (MySQL 3306)  │     │  (MySQL 3307)   │     │  (MySQL 3308)   │
├─────────────────┤     ├─────────────────┤     ├─────────────────┤
│ pets            │     │ locations       │     │ matches         │
│ contacts        │     │ zones           │     │ match_criteria  │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

### 5.2 Tablas de Pet Service

**contacts:**
| Columna | Tipo | Descripción |
|---------|------|-------------|
| id | BIGINT | PK |
| name | VARCHAR(100) | Nombre del contacto |
| phone | VARCHAR(20) | Teléfono |
| email | VARCHAR(100) | Email |
| address | VARCHAR(255) | Dirección |

**pets:**
| Columna | Tipo | Descripción |
|---------|------|-------------|
| id | BIGINT | PK |
| name | VARCHAR(100) | Nombre |
| race | VARCHAR(50) | Raza |
| color | VARCHAR(50) | Color |
| size | VARCHAR(20) | Tamaño |
| status | VARCHAR(20) | LOST/FOUND/ADOPTED |
| description | VARCHAR(500) | Descripción |
| photo_url | VARCHAR(255) | URL foto |
| contact_id | BIGINT | FK a contacts |
| created_at | DATETIME | Fecha creación |
| updated_at | DATETIME | Fecha actualización |

### 5.3 Tablas de Geo Service

**zones:**
| Columna | Tipo | Descripción |
|---------|------|-------------|
| id | BIGINT | PK |
| name | VARCHAR(100) | Nombre zona |
| city | VARCHAR(100) | Ciudad |
| incidence_count | INT | Conteo de incidencia |

**locations:**
| Columna | Tipo | Descripción |
|---------|------|-------------|
| id | BIGINT | PK |
| pet_id | BIGINT | ID de mascota |
| latitude | DOUBLE | Latitud |
| longitude | DOUBLE | Longitud |
| zone | VARCHAR(100) | Zona |
| address | VARCHAR(255) | Dirección |
| reported_at | DATETIME | Fecha reporte |

### 5.4 Tablas de Match Service

**matches:**
| Columna | Tipo | Descripción |
|---------|------|-------------|
| id | BIGINT | PK |
| pet_lost_id | BIGINT | ID mascota perdida |
| pet_found_id | BIGINT | ID mascota encontrada |
| match_percentage | INT | % de coincidencia |
| status | VARCHAR(20) | PENDING/CONFIRMED/REJECTED |
| created_at | DATETIME | Fecha creación |

**match_criteria:**
| Columna | Tipo | Descripción |
|---------|------|-------------|
| id | BIGINT | PK |
| match_id | BIGINT | FK a matches |
| criteria_name | VARCHAR(50) | Nombre criterio |
| score | INT | Puntaje (0-100) |

### 5.5 Migraciones con Liquibase

**Estructura de cambios:**
```
src/main/resources/db/changelog/
├── db-changelog-master.xml
├── 001-create-contacts.xml
├── 002-create-pets.xml
├── 003-insert-data.xml
├── 001-create-zones.xml
├── 002-create-locations.xml
├── 003-insert-data.xml
├── 001-create-matches.xml
└── 002-create-match-criteria.xml
```

---

## 6. Infraestructura

### 6.1 Servicios Docker

| Servicio | Imagen | Puerto |
|----------|--------|--------|
| api-gateway | spring-boot | 8080 |
| pet-service | spring-boot | 8081 |
| geo-service | spring-boot | 8082 |
| match-service | spring-boot | 8083 |
| bff | spring-boot | 8084 |
| frontend | nginx/react | 3000 |
| db-pet | mysql:8.0 | 3306 |
| db-geo | mysql:8.0 | 3307 |
| db-match | mysql:8.0 | 3308 |

### 6.2 Variables de Entorno

**Pet Service:**
```
SPRING_DATASOURCE_URL=jdbc:mysql://db-pet:3306/pet_service
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=password
SERVER_PORT=8081
```

**API Gateway:**
```
SPRING_CLOUD_GATEWAY_ROUTES_URI=http://pet-service:8081
```

### 6.3 Health Checks

```
GET /actuator/health
```

Todos los servicios exponen endpoints de health para orquestación y monitoreo.

---

## 7. Seguridad

### 7.1 Consideraciones Futuras (Bonos Track)

| Feature | Descripción | Estado |
|---------|-------------|--------|
| JWT | Autenticación con tokens JWT | Pendiente |
| API Key | Keys para acceso a servicios | Pendiente |
| Rate Limiting | Control de requests | Pendiente |

---

## 8. Conclusión

Esta arquitectura proporciona:
- **Escalabilidad**: Microservicios independientes
- **Mantenibilidad**: Código modular y organizado
- **Resiliencia**: Circuit Breaker y fallbacks
- **Flexibilidad**: Posibilidad de escalar servicios específicos
- **Testabilidad**: Arquitectura que facilita pruebas unitarias y de integración