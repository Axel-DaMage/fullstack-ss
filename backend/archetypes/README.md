# Arquetipos Maven - Sanos y Salvos

## Estructura

```
archetypes/
├── microservice/  # Arquetipo para microservicios
└── bff/           # Arquetipo para BFF
```

## Instalación

```bash
# Desde el directorio backend/archetypes

# Instalar arquetipo de microservicio
cd microservice
mvn install

# Instalar arquetipo de BFF
cd ../bff
mvn install
```

## Uso

### Crear nuevo microservicio

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.sanosysalvos \
  -DarchetypeArtifactId=microservice \
  -DarchetypeVersion=0.0.1-SNAPSHOT \
  -DgroupId=com.sanosysalvos \
  -DartifactId=nuevo-servicio \
  -Dpackage=com.sanosysalvos.nuevoservicio
```

### Crear nuevo BFF

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.sanosysalvos \
  -DarchetypeArtifactId=bff \
  -DarchetypeVersion=0.0.1-SNAPSHOT \
  -DgroupId=com.sanosysalvos \
  -DartifactId=nuevo-bff
```

## Variables Disponibles

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| ${artifactId} | Nombre del proyecto | pet-service |
| ${groupId} | Grupo del proyecto | com.sanosysalvos |
| ${package} | Paquete base | com.sanosysalvos.petservice |