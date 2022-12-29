import create from 'zustand';

interface AuthStore {
    accessToken: string;
    refreshToken: string;
    isLogged: boolean;
    setLoggedTrue: (accessToken: string) => void;
    loginUser: (accessToken: string, refreshToken: string) => void;
    logoutUser: () => void;
}

const useAuthStore = create<AuthStore>((set) => ({
    accessToken: '',
    refreshToken: '',
    isLogged: false,

    setLoggedTrue: (accessToken: string) => {
        set((state) => ({
            ...state,
            accessToken: accessToken,
            isLogged: true,
        }));
    },

    loginUser: (accessToken: string, refreshToken: string) => {
        set((state) => ({
            ...state,
            accessToken: accessToken,
            refreshToken: refreshToken,
            isLogged: true,
        }));
    },

    logoutUser: () => {
        set((state) => ({
            ...state,
            accessToken: '',
            refreshToken: '',
            isLogged: false,
        }));
    },
}));

export default useAuthStore;
