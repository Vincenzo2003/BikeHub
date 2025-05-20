import React from 'react';
import './Header.css'; // Importa gli stili specifici per l'Header

/**
 * Componente Header generico per l'applicazione.
 * @param {object} props - Le proprietà del componente.
 * @param {string} props.title - Il titolo da visualizzare nell'header.
 */
function Header({ title }) {
    return (
        <header className="app-header">
            <h1>{title}</h1>
        </header>
    );
}

export default Header;
