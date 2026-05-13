# Plan de Branching - Sanos y Salvos

## Estrategia General

Este documento define la estrategia de branching utilizada en todos los repositorios del proyecto Sanos y Salvos, siguiendo las reglas de Git Flow.

## Estructura de Ramas

```
main (producción)
└── develop (desarrollo)
    ├── feature/xxx   → nuevas funcionalidades
    ├── fix/xxx        → correcciones de bugs
    ├── chore/xxx      → tareas menores y documentación
    └── hotfix/xxx     → correcciones urgentes en producción
```

## Distribución por Repositorio

| Integrante | Repositorios |
|------------|--------------|
| Axel-DaMage | fullstack-ss-api-gateway, fullstack-ss-bff |
| xMvxyz | fullstack-ss-pet-service, fullstack-ss-geo-service |
| Dogameplays | fullstack-ss-frontend |

## Reglas de Trabajo

1. **Cada tarea → Rama propia desde develop**
   - Nunca trabajar directamente en develop
   - Crear rama desde develop al inicio de cada tarea

2. **Commits pequeños → mensajes claros**
   - `feat:` nuevas funcionalidades
   - `fix:` correcciones de bugs
   - `chore:` tareas menores y documentación
   - `refactor:` refactorización de código

3. **PR para merge → requiere code review**
   - El assignee del repositorio debe revisar
   - No hacer merge sin approval

4. **No commit directo a develop**
   - Todo pasa por PR
   - Mantener historial limpio

5. **Sincronizar → git pull origin develop al empezar tarea**
   - Siempre actualizar develop antes de crear rama

## Flujo de Trabajo

### Iniciar nueva tarea
```bash
git checkout develop
git pull origin develop
git checkout -b [type]/[description]
```

### Trabajar y commitear
```bash
git add .
git commit -m "type: description clara"
```

### Crear PR
```bash
git push -u origin [branch-name]
# Crear PR en GitHub con description y asignar reviewer
```

### после merge
```bash
git checkout develop
git pull origin develop
```

## Ramas Creadas (Semana 5)

| Repositorio | Rama | Tipo | Descripción |
|-------------|------|------|-------------|
| fullstack-ss-frontend | feature/observer-pattern | feature | Observer pattern implementation |
| fullstack-ss-bff | chore/update-readme-bff | chore | BFF README documentation |
| fullstack-ss | chore/update-architecture-analysis | chore | Análisis de patrones |

## Ramas Creadas (Semana 6 - PRs Pendientes)

| Repositorio | Rama | Estado | URL PR |
|-------------|------|--------|--------|
| fullstack-ss-frontend | feature/observer-pattern | Push hecho | Pendiente crear |
| fullstack-ss-bff | chore/update-readme-bff | Push hecho | Pendiente crear |
| fullstack-ss | chore/update-architecture-analysis | Push hecho | Pendiente crear |

## Conventions de Nombres

- **features**: `feature/nombre-descriptivo` (ej: `feature/observer-pattern`)
- **fixes**: `fix/bug-description` (ej: `fix/login-error`)
- **chores**: `chore/task-description` (ej: `chore/update-readme`)
- **hotfixes**: `hotfix/urgent-fix` (ej: `hotfix/security-patch`)

## Políticas de Merge

- develop → main: Solo con PR aprobado
- feature → develop: Solo con PR aprobado
- hotfix → main + develop: Merge inmediato, luego sincronizar

## Resolución de Conflictos

1. Hacer pull de develop a la rama local
2. Resolver conflictos manualmente
3. Commitear cambios
4. Push y actualizar PR