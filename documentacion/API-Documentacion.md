# Documentación de API

## Índice
1. [Pet Service API](#pet-service-api)
2. [Geo Service API](#geo-service-api)
3. [Match Service API](#match-service-api)
4. [BFF API](#bff-api)
5. [API Gateway](#api-gateway)
6. [Códigos de Respuesta](#códigos-de-respuesta)
7. [Ejemplos](#ejemplos)

---

## 1. Pet Service API

**Base URL:** `http://localhost:8081/api/pets`

### Endpoints

#### 1.1 GET /pets
Obtener todas las mascotas.

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Max",
      "race": "Golden Retriever",
      "color": "Dorado",
      "size": "LARGE",
      "status": "LOST",
      "description": "Collar azul",
      "photoUrl": "https://example.com/photo.jpg",
      "contact": {
        "id": 1,
        "name": "Juan Pérez",
        "phone": "+1234567890",
        "email": "juan@email.com",
        "address": "Calle 123"
      },
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": "2024-01-15T10:30:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1
}
```

#### 1.2 GET /pets/{id}
Obtener mascota por ID.

**Response:** 200 OK
```json
{
  "id": 1,
  "name": "Max",
  "race": "Golden Retriever",
  "color": "Dorado",
  "size": "LARGE",
  "status": "LOST",
  "description": "Collar azul",
  "photoUrl": "https://example.com/photo.jpg",
  "contact": { ... },
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### 1.3 POST /pets
Crear nueva mascota.

**Request:**
```json
{
  "name": "Luna",
  "race": "Labrador",
  "color": "Negro",
  "size": "LARGE",
  "status": "FOUND",
  "description": "Sin collar, muy nerviosa",
  "photoUrl": "https://example.com/luna.jpg",
  "contact": {
    "name": "Maria García",
    "phone": "+0987654321",
    "email": "maria@email.com",
    "address": "Avenida Principal 456"
  }
}
```

**Response:** 201 Created

#### 1.4 PUT /pets/{id}
Actualizar mascota.

**Request:**
```json
{
  "name": "Max Actualizado",
  "status": "FOUND"
}
```

**Response:** 200 OK

#### 1.5 DELETE /pets/{id}
Eliminar mascota.

**Response:** 204 No Content

#### 1.6 GET /pets/race/{race}
Buscar por raza.

**Parámetros:**
- `race`: Nombre de la raza

**Response:** 200 OK
```json
{
  "content": [ ... ],
  "totalElements": 5
}
```

#### 1.7 GET /pets/status/{status}
Buscar por estado.

**Parámetros:**
- `status`: LOST | FOUND | ADOPTED

**Response:** 200 OK

#### 1.8 GET /pets/reports/count
Obtener conteo de mascotas por estado.

**Response:** 200 OK
```json
{
  "LOST": 15,
  "FOUND": 8,
  "ADOPTED": 3
}
```

---

## 2. Geo Service API

**Base URL:** `http://localhost:8082/api/locations`

### Endpoints

#### 2.1 GET /locations
Obtener todas las ubicaciones.

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "petId": 1,
      "latitude": -34.9011,
      "longitude": -56.1648,
      "zone": "Pocitos",
      "address": "Calle 18 de Julio 1234",
      "reportedAt": "2024-01-15T10:30:00"
    }
  ],
  "totalElements": 1
}
```

#### 2.2 GET /locations/{id}
Obtener ubicación por ID.

**Response:** 200 OK

#### 2.3 POST /locations
Crear nueva ubicación.

**Request:**
```json
{
  "petId": 1,
  "latitude": -34.9011,
  "longitude": -56.1648,
  "zone": "Pocitos",
  "address": "Calle 18 de Julio 1234"
}
```

**Response:** 201 Created

#### 2.4 PUT /locations/{id}
Actualizar ubicación.

**Request:**
```json
{
  "zone": "Nueva zona",
  "address": "Nueva dirección"
}
```

#### 2.5 DELETE /locations/{id}
Eliminar ubicación.

#### 2.6 GET /locations/zone/{zone}
Buscar por zona.

#### 2.7 GET /locations/dates?start={start}&end={end}
Buscar por rango de fechas.

**Parámetros Query:**
- `start`: Fecha inicio (ISO 8601)
- `end`: Fecha fin (ISO 8601)

#### 2.8 GET /locations/reports/zones
Reporte de incidentes por zona.

**Response:**
```json
{
  "Pocitos": 5,
  "Centro": 3,
  "Parque Batlle": 2
}
```

---

## 3. Match Service API

**Base URL:** `http://localhost:8083/api/matching`

### Endpoints

#### 3.1 GET /matching
Obtener todos los matches.

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "petLostId": 5,
      "petFoundId": 10,
      "matchPercentage": 85,
      "status": "PENDING",
      "criteria": [
        {
          "criteriaName": "race",
          "score": 100
        },
        {
          "criteriaName": "color",
          "score": 80
        }
      ],
      "createdAt": "2024-01-15T10:30:00"
    }
  ],
  "totalElements": 1
}
```

#### 3.2 GET /matching/{id}
Obtener match por ID.

#### 3.3 POST /matching
Crear match manualmente.

**Request:**
```json
{
  "petLostId": 5,
  "petFoundId": 10,
  "matchPercentage": 85,
  "status": "PENDING"
}
```

#### 3.4 PUT /matching/{id}
Actualizar estado del match.

**Request:**
```json
{
  "status": "CONFIRMED"
}
```

**Estados válidos:** PENDING | CONFIRMED | REJECTED

#### 3.5 DELETE /matching/{id}
Eliminar match.

#### 3.6 GET /matching/percentage/{min}
Buscar por porcentaje mínimo.

**Response:** 200 OK

#### 3.7 GET /matching/status/{status}
Buscar por estado.

#### 3.8 GET /matching/reports/status
Reporte de matches por estado.

**Response:**
```json
{
  "PENDING": 10,
  "CONFIRMED": 5,
  "REJECTED": 2
}
```

---

## 4. BFF API

**Base URL:** `http://localhost:8084/api/bff`

### Endpoints

#### 4.1 GET /bff/dashboard
Obtener datos del dashboard agregado.

**Response:**
```json
{
  "totalPets": 26,
  "totalLocations": 15,
  "totalMatches": 17,
  "petsByStatus": {
    "LOST": 15,
    "FOUND": 8,
    "ADOPTED": 3
  },
  "matchesByStatus": {
    "PENDING": 10,
    "CONFIRMED": 5,
    "REJECTED": 2
  },
  "recentPets": [ ... ],
  "recentMatches": [ ... ]
}
```

#### 4.2 GET /bff/pets
Listado de mascotas (agregado).

**Response:** Lista de mascotas de Pet Service

#### 4.3 GET /bff/matches
Listado de matches (agregado).

**Response:** Lista de matches con datos de mascotas

---

## 5. API Gateway

**Base URL:** `http://localhost:8080`

### Rutas

| Ruta | Servicio | Puerto |
|------|----------|--------|
| `/api/pets/**` | pet-service | 8081 |
| `/api/locations/**` | geo-service | 8082 |
| `/api/matching/**` | match-service | 8083 |
| `/api/bff/**` | bff | 8084 |

### Fallbacks

Cuando un servicio no está disponible:

```json
{
  "status": "SERVICE_UNAVAILABLE",
  "message": "Pet service is temporarily unavailable",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

## 6. Códigos de Respuesta

| Código | Descripción |
|--------|-------------|
| 200 | OK - Solicitud exitosa |
| 201 | Created - Recurso creado |
| 204 | No Content - Eliminación exitosa |
| 400 | Bad Request - Datos inválidos |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |
| 503 | Service Unavailable - Servicio no disponible |

---

## 7. Ejemplos

### 7.1 Crear una mascota perdida

```bash
curl -X POST http://localhost:8080/api/pets \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Buddy",
    "race": "Border Collie",
    "color": "Negro y blanco",
    "size": "MEDIUM",
    "status": "LOST",
    "description": "Tiene collar rojo, muy rápido",
    "contact": {
      "name": "Carlos Rodríguez",
      "phone": "+59899123456",
      "email": "carlos@example.com",
      "address": "Bulevar Artigas 2500"
    }
  }'
```

### 7.2 Buscar mascotas por raza

```bash
curl http://localhost:8080/api/pets/race/Labrador
```

### 7.3 Crear una ubicación

```bash
curl -X POST http://localhost:8080/api/locations \
  -H "Content-Type: application/json" \
  -d '{
    "petId": 1,
    "latitude": -34.9011,
    "longitude": -56.1648,
    "zone": "Pocitos",
    "address": "Bulevar España 500"
  }'
```

### 7.4 Ver dashboard

```bash
curl http://localhost:8080/api/bff/dashboard
```

---

## 8. Formatos de Datos

### 8.1 Fechas
Formato: ISO 8601
```
2024-01-15T10:30:00
```

### 8.2 Coordenadas
```
latitude: -34.9011 (Sudamérica)
longitude: -56.1648 (Uruguay)
```

### 8.3 Estados de Mascotas
- `LOST` - Mascota perdida
- `FOUND` - Mascota encontrada
- `ADOPTED` - Mascota adoptada

### 8.4 Estados de Match
- `PENDING` - Pendiente de confirmación
- `CONFIRMED` - Confirmado
- `REJECTED` - Rechazado

### 8.5 Tamaños
- `SMALL` - Pequeño
- `MEDIUM` - Mediano
- `LARGE` - Grande