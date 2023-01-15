import IPhoto from '../types/IPhoto';
import axios, { AxiosRequestConfig } from 'axios';
import { useEffect, useState } from 'react';
import useAuthStore from '../stores/authStore';
import { useAxios } from '../customHooks/useAxios';
import CustomButton from '../components/CustomButton';
import useUserStore from '../stores/userStore';

const options = [
    { value: 'chocolate', label: 'Zdjęcie profilowe' },
    { value: 'strawberry', label: 'Hasło' },
    { value: 'vanilla', label: 'Nazwę' },
];

const AccountSettings = () => {
    const { isLogged, accessToken, logoutUser } = useAuthStore();
    const { id, name, setName, setUser } = useUserStore();

    const [changePassword, setChangePassword] = useState<boolean>(false);
    const [changeName, setChangeName] = useState<boolean>(false);

    const [newName, setNewName] = useState('');
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [repeatNewPassword, setRepeatNewPassword] = useState('');

    useEffect(() => {
        axios
            .get('http://localhost:8080/api/v1/account', {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            })
            .then((res) => {
                setUser(
                    res.data.id,
                    res.data.name,
                    res.data.email,
                    res.data.profilePhotoId,
                    res.data.role.role,
                );
                localStorage.setItem('userId', res.data.id);
                localStorage.setItem('userName', res.data.name);
                localStorage.setItem('userEmail', res.data.email);
                localStorage.setItem(
                    'userProfilePhotoId',
                    res.data.profilePhotoId,
                );
            });
    }, [name]);

    const handleChangePassword = () => {
        changeName && setChangeName(false);
        setChangePassword(changePassword ? false : true);
    };
    const handleChangeName = () => {
        changePassword && setChangePassword(false);
        setChangeName(changeName ? false : true);
    };

    const changeUserName = () => {
        console.log(newName);
        axios
            .put(
                `http://localhost:8080/api/v1/account/name/${newName}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                },
            )
            .then((res) => {
                window.location.reload();
                console.log(res);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    const changeUserPassword = () => {
        newPassword.length === repeatNewPassword.length &&
            axios
                .put(
                    `http://localhost:8080/api/v1/account/password`,
                    {
                        newPassword: newPassword,
                        oldPassword: oldPassword,
                    },
                    {
                        headers: {
                            Authorization: `Bearer ${accessToken}`,
                        },
                    },
                )
                .then((res) => {
                    logoutUser();
                    console.log(res);
                })
                .catch((err) => {
                    console.log(err);
                });
    };

    return (
        <div className="account-settings">
            <p>Witaj {name}!</p>
            <p>Co chcesz zmienić w swoim profilu?</p>
            <div className="account-settings__wrapper">
                <CustomButton
                    text="Zmień hasło"
                    styles="login-button"
                    onClick={() => handleChangePassword()}
                />
                <CustomButton
                    text="Zmień nazwę użytkownika"
                    styles="login-button"
                    onClick={() => handleChangeName()}
                />
            </div>
            {changePassword && (
                <div className="change-details">
                    <input
                        type="password"
                        className="change-details__input"
                        placeholder="Stare hasło"
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                            setOldPassword(e.currentTarget.value)
                        }
                    ></input>
                    <input
                        type="password"
                        className="change-details__input"
                        placeholder="Nowe hasło (minimum 7 znaków)"
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                            setNewPassword(e.currentTarget.value)
                        }
                    ></input>
                    <input
                        type="password"
                        className="change-details__input"
                        placeholder="Potwierdź nowe hasło"
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                            setRepeatNewPassword(e.currentTarget.value)
                        }
                    ></input>
                    <CustomButton
                        text="Zatwiedź zmiany"
                        styles="login-button"
                        onClick={() => {
                            changeUserPassword();
                        }}
                    />
                </div>
            )}
            {changeName && (
                <div className="change-details">
                    <input
                        type="text"
                        className="change-details__input"
                        placeholder="Nowa nazwa użytkownika"
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                            setNewName(e.currentTarget.value)
                        }
                    ></input>
                    <CustomButton
                        text="Zatwiedź zmiany"
                        styles="login-button"
                        onClick={() => {
                            changeUserName();
                        }}
                    />
                </div>
            )}
        </div>
    );
};

export default AccountSettings;
