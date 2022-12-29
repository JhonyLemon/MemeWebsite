import { useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';
import AccountSettings from './pages/AccountSettings';
import AddPost from './pages/AddPost';
import Error from './pages/Error';
import Home from './pages/Home';
import Login from './pages/Login';
import PostDetails from './pages/PostDetails';
import Signup from './pages/Signup';
import WaitingRoom from './pages/WaitingRoom';
import axios from 'axios';
import useAuthStore from './stores/authStore';
import usePhotosStore from './stores/photosStore';
import useUserStore from './stores/userStore';
import ProtectedRouteProps from './types/props/ProtectedRouteProps';

function App() {
    const { isLogged, setLoggedTrue } = useAuthStore();
    const { photos, setPhotos } = usePhotosStore();
    const { setUser } = useUserStore();

    const defaultProtectedRouteProps: Omit<ProtectedRouteProps, 'outlet'> = {
        isLogged: isLogged,
        redirectPath: '/',
    };

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        if (token !== null) {
            setLoggedTrue(token);
            axios
                .get('http://localhost:8080/api/v1/account', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                })
                .then((res) => {
                    setUser(
                        res.data.id,
                        res.data.name,
                        res.data.email,
                        res.data.profilePhotoId,
                    );
                });
        }

        photos.length === 0 &&
            axios
                .get('http://localhost:8080/api/v1/profilepicture/all')
                .then((res) => {
                    setPhotos(res.data);
                });
    }, []);

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Home />} />
                    <Route path="poczekalnia" element={<WaitingRoom />} />
                    <Route path="zalogujsie" element={<Login />} />
                    <Route path="zarejestrujsie" element={<Signup />} />
                    <Route
                        path="dodajpost"
                        element={
                            <ProtectedRoute
                                {...defaultProtectedRouteProps}
                                outlet={<AddPost />}
                            />
                        }
                    />
                    <Route
                        path="twojekonto"
                        element={
                            <ProtectedRoute
                                {...defaultProtectedRouteProps}
                                outlet={<AccountSettings />}
                            />
                        }
                    />
                    <Route path="post/:id" element={<PostDetails />} />
                    <Route path="*" element={<Error />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
