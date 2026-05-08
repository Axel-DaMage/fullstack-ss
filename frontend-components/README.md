# @sanosysalvos/ui-components

Componentes React para el proyecto Sanos y Salvos.

## Instalación

```bash
npm install @sanosysalvos/ui-components
```

## Componentes

### Dashboard
```tsx
import { Dashboard } from '@sanosysalvos/ui-components';

<Dashboard 
  apiUrl="http://localhost:8081/api"
  onNavigate={(section) => console.log(section)}
/>
```

### PetCard
```tsx
import { PetCard, Pet } from '@sanosysalvos/ui-components';

const pet: Pet = {
  id: 1,
  name: 'Firulais',
  race: 'Labrador',
  color: 'Dorado',
  size: 'Grande',
  status: 'LOST',
  description: 'Amigable'
};

<PetCard 
  pet={pet}
  onEdit={(pet) => {}}
  onDelete={(id) => {}}
  onViewLocation={(id) => {}}
/>
```

### PetsList
```tsx
import { PetsList } from '@sanosysalvos/ui-components';

<PetsList 
  apiUrl="http://localhost:8081/api"
  onPetSelect={(pet) => {}}
/>
```

### MatchesList
```tsx
import { MatchesList } from '@sanosysalvos/ui-components';

<MatchesList apiUrl="http://localhost:8081/api" />
```

## Observer Pattern

```tsx
import { eventEmitter, Events, useEvent } from '@sanosysalvos/ui-components';

// Suscribirse a eventos
useEvent(Events.PET_CREATED, (pet) => {
  console.log('Nueva mascota:', pet);
});

// Emitir eventos
eventEmitter.emit(Events.PET_CREATED, { id: '1', name: 'Firulais' });
```

## Props

| Componente | Props |
|------------|-------|
| Dashboard | apiUrl?, onNavigate? |
| PetCard | pet, onEdit?, onDelete?, onViewLocation? |
| PetsList | apiUrl?, onPetSelect? |
| MatchesList | apiUrl? |

## Desarrollo

```bash
npm install
npm run build
npm test
```

## Publicar

```bash
npm publish --access public
```