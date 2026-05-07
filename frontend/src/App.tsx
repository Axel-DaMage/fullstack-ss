import { useState } from 'react';
import { Dashboard, PetsList, MatchesList } from './components';
import './components/styles.css';

type Section = 'dashboard' | 'pets' | 'matches';

function App() {
  const [currentSection, setCurrentSection] = useState<Section>('dashboard');

  const renderSection = () => {
    switch (currentSection) {
      case 'dashboard':
        return <Dashboard apiUrl="http://localhost:8081/api" onNavigate={setCurrentSection} />;
      case 'pets':
        return <PetsList apiUrl="http://localhost:8081/api" />;
      case 'matches':
        return <MatchesList apiUrl="http://localhost:8081/api" />;
      default:
        return <Dashboard apiUrl="http://localhost:8081/api" onNavigate={setCurrentSection} />;
    }
  };

  return (
    <div className="app">
      <nav className="navbar">
        <h1 className="app-title">Sanos y Salvos</h1>
        <div className="nav-links">
          <button 
            className={currentSection === 'dashboard' ? 'active' : ''} 
            onClick={() => setCurrentSection('dashboard')}
          >
            Dashboard
          </button>
          <button 
            className={currentSection === 'pets' ? 'active' : ''} 
            onClick={() => setCurrentSection('pets')}
          >
            Mascotas
          </button>
          <button 
            className={currentSection === 'matches' ? 'active' : ''} 
            onClick={() => setCurrentSection('matches')}
          >
            Coincidencias
          </button>
        </div>
      </nav>
      <main className="app-content">
        {renderSection()}
      </main>
    </div>
  );
}

export default App;