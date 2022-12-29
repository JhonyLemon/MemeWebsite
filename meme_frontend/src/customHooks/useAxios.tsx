import { useState } from 'react';
import axios, { AxiosRequestConfig } from 'axios';

axios.defaults.baseURL = 'http://localhost:8080/api';

export const useAxios = <T,>(
    config: AxiosRequestConfig<any>,
): [boolean, T | undefined, string, () => void] => {
    const [response, setResponse] = useState<T>();
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);

    const request = () => {
        sendRequest();
    };

    const sendRequest = () => {
        setLoading(true);

        axios(config)
            .then((response) => {
                setResponse(response.data);
            })
            .catch((error) => {
                setError(error);
            })
            .finally(() => setLoading(false));
    };

    return [loading, response, error, request];
};
