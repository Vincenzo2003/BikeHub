import React, {useState} from 'react';
import './SignUpPage.css'; // Useremo lo stesso file CSS

function SignUpPage({onSignupSuccess, onSwitchToLogin}) {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        setError('');

        if (password !== confirmPassword) {
            setError('Le password non corrispondono.');
            return;
        }

        // --- SIMULAZIONE DI REGISTRAZIONE ---
        // In un'applicazione reale, qui faresti una chiamata API al tuo backend Spring Boot
        // per registrare il nuovo utente.
        console.log('Tentativo di registrazione:', {username, email, password});
        // Supponiamo che il backend risponda con successo dopo la validazione
        onSignupSuccess(); // Chiamiamo la funzione del genitore per indicare il successo
    };

    return (
        <div className="auth-container">
            <h2>Registrazione</h2>
            <form onSubmit={handleSubmit} className="auth-form">
                <div className="form-group">
                    <label htmlFor="signup-username">Username:</label>
                    <input
                        type="text"
                        id="signup-username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="signup-email">Email:</label>
                    <input
                        type="email"
                        id="signup-email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="signup-password">Password:</label>
                    <input
                        type="password"
                        id="signup-password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="confirm-password">Conferma Password:</label>
                    <input
                        type="password"
                        id="confirm-password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p className="error-message">{error}</p>}
                <button type="submit" className="auth-button">Registrati</button>
            </form>
            <p className="switch-link">
                Hai già un account?{' '}
                <span onClick={onSwitchToLogin}>Accedi qui</span>.
            </p>
        </div>
    );
}

export default SignUpPage;
