import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import CustomButton from '../components/CustomButton';
import InputForm from '../components/InputForm';
import useAuthStore from '../stores/authStore';
import useUserStore from '../stores/userStore';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const { setUser } = useUserStore();
    const { loginUser, accessToken } = useAuthStore();
    const navigate = useNavigate();

    const submitHandler = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        axios
            .post('http://localhost:8080/api/login', {
                email: email,
                password: password,
            })
            .then((res) => {
                loginUser(res.data.accessToken, res.data.refreshToken);
                localStorage.setItem('accessToken', res.data.accessToken);

                axios
                    .get('http://localhost:8080/api/v1/account', {
                        headers: {
                            Authorization: `Bearer ${res.data.accessToken}`,
                        },
                    })
                    .then((res) => {
                        setUser(
                            res.data.id,
                            res.data.name,
                            res.data.email,
                            res.data.profilePhotoId,
                        );
                        localStorage.setItem('userId', res.data.id);
                        localStorage.setItem('userName', res.data.name);
                        localStorage.setItem('userEmail', res.data.email);
                        localStorage.setItem(
                            'userProfilePhotoId',
                            res.data.profilePhotoId,
                        );
                    });
                navigate('/');
            });
    };

    return (
        <form className="login-container" onSubmit={submitHandler}>
            <p className="login-container__title">Logowanie</p>
            <InputForm
                id="login"
                placeholder="Email"
                type="text"
                onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    setEmail(e.currentTarget.value)
                }
            />
            <InputForm
                id="password"
                placeholder="Hasło"
                type="password"
                onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    setPassword(e.currentTarget.value)
                }
            />
            <CustomButton
                styles={'login-button'}
                onClick={() => {}}
                text="Zaloguj się"
            />
            <div className="login-container__link">
                <p>Nie masz jeszcze konta?</p>
                <Link to="/zarejestrujsie">
                    <label>Zarejestruj się</label>
                </Link>
            </div>
        </form>
    );
};

export default Login;
