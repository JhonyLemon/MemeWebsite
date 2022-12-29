import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import CustomButton from '../components/CustomButton';
import InputForm from '../components/InputForm';

const Login = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');
    const navigate = useNavigate();

    const submitHandler = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        axios
            .post('http://localhost:8080/api/v1/account/register', {
                name: name,
                email: email,
                password: password,
            })
            .then((res) => {
                navigate('/zalogujsie');
            });
    };

    return (
        <form className="login-container" onSubmit={submitHandler}>
            <p className="login-container__title">Rejestracja</p>
            <InputForm
                id="name"
                placeholder="Nazwa użytkownika"
                type="text"
                onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    setName(e.target.value)
                }
            />
            <InputForm
                id="email"
                placeholder="Email"
                type="text"
                onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    setEmail(e.target.value)
                }
            />
            <InputForm
                id="password"
                placeholder="Hasło"
                type="password"
                onChange={(e) => setPassword(e.target.value)}
            />
            <InputForm
                id="repeatPassword"
                placeholder="Powtórz hasło"
                type="password"
                onChange={(e) => setRepeatPassword(e.target.value)}
            />
            <CustomButton
                styles={'login-button'}
                onClick={() => {}}
                text="Zarejestruj sie"
            />
        </form>
    );
};

export default Login;
