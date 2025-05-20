import React, {useState} from 'react';
import './LoginPage.css'; // Useremo questo file per lo stile comune ai form

function LoginPage({onLoginSuccess, onSwitchToSignup}) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault(); // Previene il ricaricamento della pagina
        setError(''); // Resetta eventuali errori precedenti

        // --- SIMULAZIONE DI AUTENTICAZIONE ---
        // In un'applicazione reale, qui faresti una chiamata API al tuo backend Spring Boot
        // per verificare le credenziali.
        if (username === 'testuser' && password === 'password') {
            onLoginSuccess(); // Chiamiamo la funzione del genitore per indicare il successo
        } else {
            setError('Nome utente o password non validi.');
        }
    };

    return (
        <div className="auth-container">
            <h2>Login</h2>
            <form onSubmit={handleSubmit} className="auth-form">
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p className="error-message">{error}</p>}
                <button type="submit" className="auth-button">Accedi</button>
            </form>
            <p className="switch-link">
                Non hai un account?{' '}
                <span onClick={onSwitchToSignup}>Registrati qui</span>.
            </p>
        </div>
    );
}

export default LoginPage;
