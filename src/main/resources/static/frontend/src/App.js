import React, { useState } from 'react';
import LoginPage from './pages/LoginPage/LoginPage';
import SignupPage from './pages/SignUpPage/SignUpPage';
import HomePage from './pages/HomePage/HomePage';
import Header from './components/layout/Header'; // Importa il componente Header
import './App.css'; // Stili globali e per il layout principale dell'App

function App() {
  // Stato per controllare quale pagina mostrare: 'login', 'signup', o 'home'
  const [currentPage, setCurrentPage] = useState('login');
  // Stato per simulare se l'utente è loggato
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  /**
   * Gestisce il successo del login.
   * Imposta lo stato di login su true e cambia la pagina alla Home.
   */
  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
    setCurrentPage('home'); // Passa alla pagina Home
  };

  /**
   * Gestisce il successo della registrazione.
   * Cambia la pagina al Login e mostra un alert.
   */
  const handleSignupSuccess = () => {
    setCurrentPage('login'); // Dopo la registrazione, torna alla pagina di login
    // Utilizziamo alert per semplicità, in una vera app useresti un modal o una notifica UI
    alert('Registrazione avvenuta con successo! Ora puoi effettuare il login.');
  };

  /**
   * Gestisce l'azione di logout.
   * Imposta lo stato di login su false e cambia la pagina al Login.
   */
  const handleLogout = () => {
    setIsLoggedIn(false);
    setCurrentPage('login'); // Torna alla pagina di login dopo il logout
  };

  /**
   * Funzione helper per renderizzare il componente della pagina corrente.
   * @returns {JSX.Element} Il componente della pagina da renderizzare.
   */
  const renderPage = () => {
    if (isLoggedIn) {
      return <HomePage onLogout={handleLogout} />;
    }

    switch (currentPage) {
      case 'login':
        return <LoginPage onLoginSuccess={handleLoginSuccess} onSwitchToSignup={() => setCurrentPage('signup')} />;
      case 'signup':
        return <SignupPage onSignupSuccess={handleSignupSuccess} onSwitchToLogin={() => setCurrentPage('login')} />;
      default:
        // Fallback predefinito alla pagina di login
        return <LoginPage onLoginSuccess={handleLoginSuccess} onSwitchToSignup={() => setCurrentPage('signup')} />;
    }
  };

  return (
      <div className="App">
        {/* Utilizza il componente Header, passandogli il titolo come prop */}
        <Header title="Applicazione React di Autenticazione" />
        <main className="App-main">
          {renderPage()}
        </main>
      </div>
  );
}

export default App;
