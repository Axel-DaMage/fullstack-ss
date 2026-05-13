# Objetivos del Proyecto - Evaluación 2

## Requisitos Principales

### 1. Microservicios
- Al menos **3 microservicios**.
- Cada microservicio debe poseer su **propia base de datos**.

### 2. Base de Datos
- Al menos una de las bases de datos debe tener **2 o más tablas relacionadas** (máximo 4 tablas).

### 3. Endpoints CRUD
Cada microservicio debe tener los siguientes endpoints:
- **GET**: Todos los registros y por ID
- **POST**
- **PUT**
- **DELETE**

### 4. Comunicación entre Microservicios
- Al menos **un microservicio que se comunique con 2 microservicios distintos**.

### 5. Endpoints Adicionales
Para cada microservicio implementar **2 de los siguientes endpoints**:
- Totales
- Búsquedas por atributos distintos a los IDs (ej: buscar ventas por Rut=11111111-1)
- Búsquedas por rangos de fechas
- Otras búsquedas según el modelo de negocio

### 6. Liquibase
- En al menos **2 microservicios** utilizar **Liquibase** para la creación de tablas y al menos **10 registros**.
- **Obligatorio** utilizar Liquibase para las tablas del punto 2 (tablas relacionadas).

### 7. Infraestructura
- Al menos **un servidor de bases de datos**.
- Implementar **API Gateway**.
- Al menos un **BFF**, un **backend** y un **frontend**, cada uno con su correspondiente arquetipo, patrón arquitectónico y patrón de diseño.

### 8. Infraestructura Tecnológica (una de las siguientes opciones)
- **Opción A - Infraestructura en la nube** (al menos 3 de los siguientes):
  - EC2
  - Docker
  - RDS
  - ECR
  - ECS

- **Opción B - Docker en al menos 2 PCs distintos**:
  - Imágenes y contenedores para: base de datos, BFF, backend y frontend

---

### 9. Estructura de Componentes

#### Frontend
- Componentes frontend de tipo **NPM**.
- Código fuente organizado en carpetas (src, public, etc.).
- Archivo `package.json` con dependencias y scripts necesarios.
- Archivo `README.md` con instrucciones para ejecutar y probar los componentes.

#### Backend
- **1 Backend For Frontend (BFF)** basado en arquetipo Maven.
- **Al menos 2 microservicios** basados en arquetipos Maven.
- Código fuente estructurado y organizado.
- Archivos de configuración y dependencias.
- Archivo `README.md` con instrucciones para cada componente.

### 10. Patrones de Diseño
- Implementar al menos **3 patrones de diseño** en componentes frontend y backend.
- Los patrones deben ser eficientes y mantenibles.
- Justificar la selección de cada patrón según el problema que resuelven.

### 11. Arquetipos y Patrones Arquitectónicos
- Utilizar **arquetipos Maven** para la construcción del backend.
- Aplicar patrones arquitectónicos coherentes para BFF y microservicios.
- Demostrar que la solución es escalable y eficiente.
- Cada componente debe tener su propio arquetipo, patrón arquitectónico y patrón de diseño.

### 12. Versionamiento y Estrategia de Branching
- Todos los componentes versionados en **GitHub**.
- Implementar una estrategia de branching clara y organizada.
- Evidencia de **merges**, ramas y resolución de conflictos documentados.
- Gestión eficiente de versiones.

### 13. Pruebas y Calidad de Código
- Implementar **pruebas unitarias** para los componentes.
- Mantener un código limpio y ordenado.
- Demostrar uso adecuado de patrones de diseño.
- Documentar resultados de pruebas y cobertura de código.

---

## Bonos Track

1. Para un microservicio utilizar **Flyway**.
2. Implementar pipeline **CI/CD** desde GitHub.
3. Implementar **JWT** que se extienda a los microservicios.

## Ajustes a Realizar

1. Cambiar esquema SQL
2. Personalizar datos SQL
3. Dividir proyecto en repositorios
4. Realizar CI/CD por repositorios 
5. Implementar Gitflow