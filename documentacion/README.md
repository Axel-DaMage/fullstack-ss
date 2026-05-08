# Documentación del Proyecto

Este directorio contiene la documentación extensiva del Sistema de Mascotas Perdidas "Sanos y Salvos".

## Archivos de Documentación

### 1. Analisis-Patrones-Arquetipos.md
Documentación detallada de los patrones de diseño y arquetipos implementados en el proyecto.

**Contenido:**
- Patrones de diseño (Factory Method, Repository, Singleton, Observer, Aggregation, Circuit Breaker)
- Justificación técnica de cada patrón
- Arquetipos Maven (microservice, bff)
- Decisiones arquitectónicas

### 2. Plan-Branching.md
Estrategia de branching y flujo de trabajo con Git.

**Contenido:**
- Estructura de ramas (main, develop, feature, hotfix, release)
- Flujo de trabajo (Git Flow)
- Estrategia de merging
- Resolución de conflictos
- Conventional Commits
- Casos de uso prácticos

### 3. Arquitectura.md
Documentación de la arquitectura del sistema.

**Contenido:**
- Visión general de la arquitectura
- Arquitectura de microservicios
- Componentes del sistema (API Gateway, Pet Service, Geo Service, Match Service, BFF)
- Comunicación entre servicios
- Estructura de bases de datos
- Infraestructura Docker
- Consideraciones de seguridad

### 4. API-Documentacion.md
Documentación de todos los endpoints de la API.

**Contenido:**
- Pet Service API (CRUD + endpoints adicionales)
- Geo Service API (CRUD + búsquedas)
- Match Service API (CRUD + reportes)
- BFF API (endpoints agregados)
- API Gateway (rutas y fallbacks)
- Códigos de respuesta HTTP
- Ejemplos de uso con curl

### 5. repositorios.txt
Archivo con los enlaces a los repositorios del proyecto.

**Contenido:**
- Repositorio principal
- Estructura del proyecto
- Puertos de servicios
- Contribuidores

---

## Uso de la Documentación

### Para Desarrolladores
1. Consultar **Arquitectura.md** para entender la estructura del sistema
2. Revisar **API-Documentacion.md** para ver los endpoints disponibles
3. Referirse a **Analisis-Patrones-Arquetipos.md** para decisiones de diseño

### Para Gestión de Proyecto
1. Revisar **Plan-Branching.md** para estrategia de versionado
2. Consultar **repositorios.txt** para enlaces de repositorios

---

## Navegación Rápida

| Tema | Archivo |
|------|---------|
| Patrones de diseño | `Analisis-Patrones-Arquetipos.md` |
| Git y branching | `Plan-Branching.md` |
| Arquitectura técnica | `Arquitectura.md` |
| Endpoints API | `API-Documentacion.md` |
| Enlaces de repos | `repositorios.txt` |

---

## Información del Proyecto

**Sistema:** Sanos y Salvos - Mascotas Perdidas
**Organización:** Axel-DaMage
**Versión:** 1.0.0
**Fecha:** Mayo 2026