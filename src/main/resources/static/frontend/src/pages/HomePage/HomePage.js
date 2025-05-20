import React from 'react';
import './HomePage.css'; // File CSS per la pagina Home

function HomePage({ onLogout }) {
    return (
        <div className="home-container">
            <h2>Benvenuto nella tua applicazione!</h2>
            <p>Hai effettuato l'accesso con successo. Questa è la tua dashboard.</p>
            <button onClick={onLogout} className="logout-button">Logout</button>
        </div>
    );
}

export default HomePage;