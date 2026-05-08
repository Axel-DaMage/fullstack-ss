# Plan de Branching y Estrategia de Git

## Índice
1. [Estructura de Ramas](#estructura-de-ramas)
2. [Flujo de Trabajo](#flujo-de-trabajo)
3. [Estrategia de Merging](#estrategia-de-merging)
4. [Resolución de Conflictos](#resolución-de-conflictos)
5. [Reglas de Commits](#reglas-de-commits)
6. [Casos de Uso](#casos-de-uso)

---

## 1. Estructura de Ramas

### 1.1 Rama Principal (main/master)

```
main (producción)
```

La rama `main` contiene el código que está en producción. Solo se fusiona código que ha pasado todas las pruebas y ha sido revisado.

**Reglas:**
- No se commitea directamente a main
- Requiere Pull Request para cualquier cambio
- Debe pasar todos los tests automatizados
- Requiere al menos 1 aprobación

### 1.2 Rama de Desarrollo

```
main
  └── develop (desarrollo)
```

La rama `develop` es la rama de integración para el desarrollo activo. Contiene el código más reciente de las features.

**Reglas:**
- Se fusiona desde feature branches
- Código debe compilar y pasar tests básicos
- Se sincroniza regularmente con main

### 1.3 Ramas de Feature

```
main
  └── develop
       ├── feature/pet-service
       ├── feature/location-service
       ├── feature/matching-service
       ├── feature/bff
       └── feature/frontend
```

Las ramas de feature contienen desarrollo específico para una funcionalidad.

**Naming:** `feature/<nombre-servicio-funcionalidad>`

**Ciclo de vida:**
1. Crear desde `develop`
2. Desarrollo en la rama feature
3. Mergear a `develop` mediante PR
4. Eliminar rama tras merge

### 1.4 Ramas de Hotfix

```
main
  └── hotfix/<descripcion-breve>
```

Para correcciones urgentes en producción.

**Reglas:**
- Se crea desde `main`
- Debe mergearse a `main` Y `develop`
- Requiere aprobación inmediata

### 1.5 Ramas de Release

```
main
  └── release/v1.0.0
```

Para preparar una versión de producción.

**Reglas:**
- Se crea desde `develop`
- Solo correcciones menores (bug fixes)
- Se mergea a `main` y `develop`

---

## 2. Flujo de Trabajo

### 2.1 Flujo Standard (Git Flow)

```
┌─────────────────────────────────────────────────────────┐
│                    main (v1.0.0)                        │
│                         │                               │
│                         ▼                               │
│                   develop (v1.1.0-dev)                  │
│                    │        │                          │
│         ┌──────────┘        └──────────┐               │
│         ▼                                 ▼           │
│   feature/pet-service             feature/frontend    │
│         │                                 │           │
│         └────────────┬────────────────────┘           │
│                      ▼                                │
│                   develop                              │
│                      │                                 │
│                      ▼                                 │
│                    main                                │
└─────────────────────────────────────────────────────────┘
```

### 2.2 Pasos para trabajar con Features

**Paso 1: Actualizar develop**
```bash
git checkout develop
git pull origin develop
```

**Paso 2: Crear rama de feature**
```bash
git checkout -b feature/nueva-funcionalidad
```

**Paso 3: Desarrollar y hacer commits**
```bash
git add .
git commit -m "feat: agregar nueva funcionalidad"
```

**Paso 4: Mantener sincronizado con develop**
```bash
git fetch origin
git merge origin/develop
```

**Paso 5: Subir rama y crear Pull Request**
```bash
git push -u origin feature/nueva-funcionalidad
```

### 2.3 Proceso de Pull Request

1. **Crear PR** en GitHub
2. **Descripción**: Explicar cambios, motivación, testing
3. **Revisión**: Al menos 1 revisor
4. **Correcciones**: Aplicar feedback si es necesario
5. **Aprobación**: Se apruebe el PR
6. **Merge**: Squash merge a develop

---

## 3. Estrategia de Merging

### 3.1 Tipos de Merge

#### Squash Merge (Recomendado)
Combina todos los commits en uno solo.

**Ventajas:**
- Historial limpio
- Facilita revert si es necesario
- Un commit por feature

**Comando:**
```bash
git merge --squash feature/nueva-funcionalidad
```

#### Merge Commit
Mantiene todos los commits del feature.

**Ventajas:**
- Historial completo de desarrollo
- traceability por commit

**Uso:** Solo cuando se quiere mantener historial detallado

### 3.2 Flujo de Merge

```
Feature Branch          develop           main
     │                      │                │
     │--- commit 1 -------->│                │
     │--- commit 2 -------->│                │
     │--- commit 3 -------->│                │
     │                      │--- merge ----->│ (squash)
     │                      │                │
     ▼                      ▼                ▼
  Eliminar             Mantener          Mantener
```

### 3.3 Protección de Ramas

**main (producción):**
- Requiere PR para merge
- Al menos 1 revisión requerida
- Tests deben pasar
- No permite force push

**develop:**
- Requiere PR
- Tests deben pasar
- Code review recomendado

---

## 4. Resolución de Conflictos

### 4.1 Cuándo ocurren conflictos

Los conflictos ocurren cuando:
- Mismo archivo modificado en dos ramas
- Mismo código eliminado en ambas ramas
- Cambios incompatibles en lógica relacionada

### 4.2 Pasos para resolver conflictos

**Paso 1: Identificar conflictos**
```bash
git merge origin/develop
# Muestra archivos con conflictos
```

**Paso 2: Editar archivos en conflicto**
```bash
# Abrir archivos marcados con <<<<<<< === >>>>>>>
# Elegir o combinar cambios
```

**Paso 3: Marcar como resuelto**
```bash
git add <archivos-resueltos>
git commit -m "merge: resolver conflictos con develop"
```

### 4.3 Estrategias de Resolución

#### Estrategia 1: Mantener nuestros cambios
```bash
git checkout --ours <archivo>
```
**Uso:** Cuando tenemos certeza de que nuestros cambios son correctos

#### Estrategia 2: Mantener cambios entrantes
```bash
git checkout --theirs <archivo>
```
**Uso:** Cuando los cambios de la otra rama son más recientes

#### Estrategia 3: Combinar manualmente
```bash
# Editar manualmente el archivo
# Eliminar marcadores de conflicto
# Mantener lo necesario de ambas partes
```

### 4.4 Prevención de Conflictos

1. **Sincronizar frecuentemente**: `git pull origin develop` frecuentemente
2. **Commits pequeños**: Cambios pequeños = menos conflictos
3. **Comunicación**: Coordinar con el equipo sobre archivos compartidos
4. **Code reviews**: Revisar antes de hacer merge

---

## 5. Reglas de Commits

### 5.1 Conventional Commits

Formato: `<tipo>(<alcance>): <descripción>`

```
feat(pet-service): agregar endpoint para buscar por raza
fix(api-gateway): corregir fallback para location service
docs(readme): actualizar sección de instalación
refactor(pet-service): simplificar lógica de validación
test(pet-service): agregar pruebas unitarias para PetFactory
```

### 5.2 Tipos de Commits

| Tipo | Descripción |
|------|-------------|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de bug |
| `docs` | Documentación |
| `style` | Formato (sin cambio de lógica) |
| `refactor` | Refactorización |
| `test` | Agregar/modificar pruebas |
| `chore` | Tareas de mantenimiento |

### 5.3 Alcances (Scopes)

- `pet-service`
- `location-service`
- `matching-service`
- `bff`
- `api-gateway`
- `frontend`
- `frontend-components`
- `archetypes`

### 5.4 Buenas Prácticas

1. **Ser específico**: "agregar validación de email" no "agregar cosas"
2. **Primera línea límite**: 50 caracteres máximo
3. **Cuerpo descriptivo**: Si es necesario, explicar el "por qué"
4. **Verbos en imperativo**: "agregar" no "agregado" o "agregando"

---

## 6. Casos de Uso

### 6.1 Nueva funcionalidad en Pet Service

```
1. git checkout develop
2. git pull origin develop
3. git checkout -b feature/busqueda-avanzada
4. # Desarrollo...
5. git add . && git commit -m "feat(pet-service): agregar búsqueda avanzada"
6. git push -u origin feature/busqueda-avanzada
7. # Crear PR en GitHub -> revisar -> merge a develop
```

### 6.2 Corrección de bug crítico

```
1. git checkout main
2. git pull origin main
3. git checkout -b hotfix/correccion-login
4. # Corrección...
5. git commit -m "fix(api-gateway): corregir autenticación JWT"
6. # PR a main Y develop
7. git checkout main && git merge hotfix/correccion-login
8. git checkout develop && git merge hotfix/correccion-login
```

### 6.3 Preparar Release

```
1. git checkout develop
2. git pull origin develop
3. git checkout -b release/v1.0.0
4. # Actualizar version en pom.xml y package.json
5. # Prueba de regression
6. git commit -m "chore: preparar release v1.0.0"
7. # Merge a main y develop
8. # Tag en GitHub: v1.0.0
```

---

## 7. Reglas del Proyecto

### 7.1 Commits obligatorios

- ✅ Mensajes claros y descriptivos
- ✅ Commits atómicos (un cambio por commit)
- ✅ Rama correcta (develop para features)

### 7.2 Commits prohibidos

- ❌ Commits con mensaje "WIP" o "trabajando en..."
- ❌ Commits sin mensaje
- ❌ Commits grandes sin explicación
- ❌ Commiteo directo a main

### 7.3 Reviews obligatorios

- ✅ Pull Request requiere al menos 1 reviewer
- ✅ Tests deben pasar antes de merge
- ✅ Código debe compilar

---

## 8. Resumen Visual

```
main ─────────────────────────────────────────────────►
  │   ▲                                         ▲
  │   │ merge (release/hotfix)              merge (release)
  │   │                                         │
develop ──────────────────────────────────────────────►
  │   ▲                                      ▲
  │   │ merge (feature)                  merge (feature)
  │   │                                       │
feature/a ─►  feature/b ─►  feature/c ─► ─────┘
```

---

## 9. Referencias

- **Git Flow**: https://nvie.com/posts/a-successful-git-branching-model/
- **Conventional Commits**: https://www.conventionalcommits.org/
- **GitHub Flow**: https://githubflow.github.io/