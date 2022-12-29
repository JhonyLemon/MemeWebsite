import IPhoto from '../types/IPhoto';
import { AxiosRequestConfig } from 'axios';
import { useEffect } from 'react';
import useAuthStore from '../stores/authStore';
import { useAxios } from '../customHooks/useAxios';

const options = [
    { value: 'chocolate', label: 'Zdjęcie profilowe' },
    { value: 'strawberry', label: 'Hasło' },
    { value: 'vanilla', label: 'Nazwę' },
];

const AccountSettings = () => {
    const { isLogged, accessToken } = useAuthStore();

    const config: AxiosRequestConfig = {
        method: 'get',
        url: `/v2/profilepicture/all`,
    };
    const [loading, response, error, request] = useAxios<IPhoto[]>(config);

    useEffect(() => {
        request();
    }, []);

    return (
        <div className="account-settings">
            <p>Co chcesz zmienić w swoim profilu?</p>
            {response?.map((photo) => {
                return (
                    <img
                        key={photo.id}
                        src={`data:image/jpeg;base64,${photo.content}`}
                    />
                );
            })}
        </div>
    );
};

export default AccountSettings;
