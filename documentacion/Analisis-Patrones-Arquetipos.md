# Análisis de Patrones de Diseño y Arquetipos

## Índice
1. [Patrones de Diseño Implementados](#patrones-de-diseño-implementados)
2. [Justificación de Patrones](#justificación-de-patrones)
3. [Arquetipos Maven](#arquetipos-maven)
4. [Decisiones Arquitectónicas](#decisiones-arquitectónicas)

---

## 1. Patrones de Diseño Implementados

### 1.1 Factory Method (Patrón Creacional)

**Ubicación:** `backend/pet-service/src/main/java/com/sanosysalvos/petservice/service/PetFactory.java`

**Descripción:**
El patrón Factory Method se implementa en la clase `PetFactory` para centralizar la creación de objetos `Pet`. Este patrón permite crear instancias de mascotas con valores por defecto y métodos especializados.

**Implementación:**
```java
@Component
public class PetFactory {
    public Pet createPet(String name, String race, String color, String size, String status)
    public Pet createLostPet(String name, String race, String color, String size, String description)
    public Pet createFoundPet(String name, String race, String color, String size, String description)
}
```

**Justificación:**
- **Encapsulamiento**: La lógica de creación de mascotas está centralizada
- **Flexibilidad**: Permite crear tipos específicos de mascotas (perdidas/encontradas)
- **Mantenimiento**: Cambios en la lógica de creación solo afectan a una clase
- **Testabilidad**: Fácil de mockear en pruebas unitarias

---

### 1.2 Repository Pattern (Patrón de Acceso a Datos)

**Ubicación:** 
- `backend/pet-service/src/main/java/com/sanosysalvos/petservice/repository/`
- `backend/geo-service/src/main/java/com/sanosysalvos/geoservice/repository/`
- `backend/match-service/src/main/java/com/sanosysalvos/matchservice/repository/`

**Descripción:**
El patrón Repository proporciona una abstracción sobre la capa de datos, permitiendo acceso a las entidades mediante métodos de Spring Data JPA.

**Implementación - PetRepository:**
```java
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByRace(String race);
    List<Pet> findByStatus(String status);
    Long countByStatus(String status);
}
```

**Implementación - LocationRepository:**
```java
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByZone(String zone);
    List<Location> findByReportedAtBetween(LocalDateTime start, LocalDateTime end);
    Long countByZone(String zone);
}
```

**Justificación:**
- **Abstracción**: Separa la lógica de negocio del acceso a datos
- **Flexibilidad**: Permite cambiar la implementación de persistencia
- **Testabilidad**: Facilita el uso de repositorios mock en pruebas
- **Consistencia**: Estandariza el acceso a datos en todos los microservicios

---

### 1.3 Singleton (Patrón Creacional)

**Ubicación:**
- `backend/pet-service/src/main/java/com/sanosysalvos/petservice/config/AppConfig.java`
- `backend/geo-service/src/main/java/com/sanosysalvos/geoservice/config/AppConfig.java`
- `backend/match-service/src/main/java/com/sanosysalvos/matchservice/config/AppConfig.java`

**Descripción:**
En Spring Boot, las clases anotadas con `@Component` o `@Configuration` son singletons por defecto. Se utiliza `AppConfig` para gestionar configuraciones centralizadas.

**Implementación:**
```java
@Configuration
public class AppConfig {
    @Value("${external.services.pet-service.url}")
    private String petServiceUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

**Justificación:**
- **Gestión centralizada**: Configuraciones centralizadas en un solo lugar
- **Inyección de dependencias**: Spring gestiona el ciclo de vida
- **Reutilización**: Una única instancia compartida en toda la aplicación

---

### 1.4 Observer (Patrón de Comportamiento)

**Ubicación:** 
- `frontend/src/lib/EventEmitter.ts`
- `frontend/src/lib/events.ts`

**Descripción:**
El patrón Observer permite que los componentes React se suscriban a eventos y reactiven a cambios de estado.

**Implementación:**
```typescript
export class EventEmitter {
  private events: Map<string, Function[]> = new Map();

  on(event: string, callback: Function): void
  emit(event: string, data?: any): void
  off(event: string, callback: Function): void
}
```

**Casos de uso:**
- Notificaciones de matches encontrados
- Actualización de estado de mascotas
- Logging de eventos de la aplicación

**Justificación:**
- **Desacoplamiento**: Componentes no dependen directamente unos de otros
- **Reactividad**: Actualización automática de la UI ante cambios
- **Flexibilidad**: Múltiples suscriptores pueden escuchar el mismo evento

---

### 1.5 Aggregation Pattern (Patrón Estructural)

**Ubicación:** 
- `backend/bff/src/main/java/com/sanosysalvos/bff/service/AggregationService.java`

**Descripción:**
El BFF (Backend For Frontend) utiliza el patrón de agregación para combinar datos de múltiples microservicios en una sola respuesta.

**Implementación:**
```java
@Service
public class AggregationService {
    @Autowired private PetServiceClient petServiceClient;
    @Autowired private LocationServiceClient locationServiceClient;
    @Autowired private MatchServiceClient matchServiceClient;

    public DashboardData getDashboardData() {
        // Agrega datos de múltiples servicios
    }
}
```

**Justificación:**
- **Optimización de llamadas**: Una única llamada desde el frontend
- **Transformación**: Adapta respuestas de múltiples servicios
- **Simplicidad**: El frontend consume un único endpoint

---

### 1.6 Circuit Breaker (Patrón de Resiliencia)

**Ubicación:** 
- `backend/api-gateway/src/main/java/com/sanosysalvos/apigateway/controller/FallbackController.java`

**Descripción:**
El patrón Circuit Breaker proporciona resiliencia cuando un servicio falla, evitando cascades de errores.

**Implementación:**
```java
@RestController
public class FallbackController {
    @GetMapping("/fallback/pets")
    public ResponseEntity<Map<String, String>> petServiceFallback() {
        return ResponseEntity.ok(Map.of(
            "status", "SERVICE_UNAVAILABLE",
            "message", "Pet service is temporarily unavailable"
        ));
    }
}
```

**Justificación:**
- **Tolerancia a fallos**: Manejo elegante de fallos de servicios
- **Prevención de cascade**: Evita que errores se propaguen
- **Recuperación**: Permite que el servicio se recupere

---

## 2. Arquetipos Maven

### 2.1 Arquetipo de Microservicio

**Ubicación:** `backend/archetypes/microservice/`

**Propósito:**
Plantilla reusable para crear nuevos microservicios con estructura predefinida.

**Estructura:**
```
microservice/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/sanosysalvos/${artifactId}/
    │   │   └── Application.java
    │   └── resources/
    │       ├── application.properties
    │       └── db/changelog/
    │           └── db-changelog-master.xml
    └── test/
        └── java/com/sanosysalvos/${artifactId}/
            └── ApplicationTests.java
```

**Características:**
- Spring Boot 3.1.2
- Spring Data JPA
- Liquibase integrado
- MySQL Connector
- Pruebas unitarias base

**Dependencias incluidas:**
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
</dependencies>
```

### 2.2 Arquetipo BFF

**Ubicación:** `backend/archetypes/bff/`

**Propósito:**
Plantilla para crear BFFs (Backend For Frontend) con patrón de agregación.

**Estructura:**
```
bff/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/sanosysalvos/bff/
    │   │   ├── BffApplication.java
    │   │   └── service/
    │   │       └── AggregationService.java
    │   └── resources/
    │       └── application.yml
    └── test/
        └── java/com/sanosysalvos/bff/
            └── AggregationServiceTest.java
```

**Características:**
- Integración con múltiples clientes REST
- Patrón Aggregation Service
- Configuration Properties
- Pruebas de integración

---

## 3. Decisiones Arquitectónicas

### 3.1 Arquitectura de Microservicios

**Justificación:**
- **Escalabilidad independiente**: Cada servicio puede escalar según demanda
- **Tecnologías diversas**: Posibilidad de usar diferentes stacks
- **Despliegue autónomo**: Despliegue sin afectar otros servicios
- **Mantenimiento**: Código más pequeño y manejable

### 3.2 Spring Cloud Gateway

**Justificación:**
- **Unified entry point**: Punto único de entrada para el frontend
- **Routing**: Ruteo dinámico basado en paths
- **Cross-cutting concerns**: Logging, seguridad centralizada
- **Resilience**: Fallbacks configurables

### 3.3 BFF (Backend For Frontend)

**Justificación:**
- **Optimización**: Reduce el número de llamadas desde el cliente
- **Abstracción**: Oculta la complejidad de múltiples servicios
- **Transformación**: Adapta datos al formato requerido por el frontend
- **Autonomía**: Frontend independiente de cambios en servicios

### 3.4 Liquibase para Migraciones

**Justificación:**
- **Versionamiento**: Control de versiones del esquema de DB
- **Reversibilidad**: Posibilidad de rollback
- **Colaboración**: Cambios en equipo mediante código
- **Auditoría**: Historial de cambios en la base de datos

---

## 4. Resumen de Implementación

| Patrón | Servicio | Estado | Beneficio Principal |
|--------|----------|--------|---------------------|
| Factory Method | Pet Service | ✅ Implementado | Centralización de creación de entidades |
| Repository | Todos | ✅ Implementado | Abstracción de acceso a datos |
| Singleton | Todos | ✅ Implementado | Gestión centralizada de configuraciones |
| Observer | Frontend | ✅ Implementado | Reactividad en UI |
| Aggregation | BFF | ✅ Implementado | Combinación de datos de servicios |
| Circuit Breaker | API Gateway | ✅ Implementado | Resiliencia ante fallos |

| Arquetipo | Propósito | Estado |
|-----------|-----------|--------|
| microservice | Plantilla para nuevos microservicios | ✅ Implementado |
| bff | Plantilla para BFF | ✅ Implementado |

---

## 5. Conclusiones

Los patrones de diseño seleccionados proporcionan:

1. **Mantenibilidad**: Código organizado y predecible
2. **Testabilidad**: Fácil creación de pruebas unitarias
3. **Escalabilidad**: Capacidad de crecer sin refactorizaciones mayores
4. **Reutilización**: Componentes reutilizables mediante arquetipos
5. **Resiliencia**: Tolerancia a fallos mediante Circuit Breaker

Los arquetipos Maven permiten la creación rápida de nuevos servicios con las mejores prácticas ya incorporadas, reduciendo el tiempo de desarrollo y asegurando consistencia en el código.