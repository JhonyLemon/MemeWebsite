import create from 'zustand';
import useAuthStore from './authStore';

interface UserStore {
    id: number;
    name: string;
    email: string;
    profilePhotoId: number;
    role: string;

    setUser: (
        id: number,
        name: string,
        email: string,
        profilePhotoId: number,
        role: string,
    ) => void;

    resetUser: () => void;

    setName: (name: string) => void;
}

const useUserStore = create<UserStore>((set) => ({
    id: 0,
    name: '',
    email: '',
    profilePhotoId: 0,
    role: '',

    setUser: (
        id: number,
        name: string,
        email: string,
        profilePhotoId: number,
        role: string,
    ) => {
        set((state) => ({
            ...state,
            id: id,
            name: name,
            email: email,
            profilePhotoId: profilePhotoId,
            role: role,
        }));
    },

    resetUser: () => {
        set((state) => ({
            ...state,
            id: 0,
            name: '',
            email: '',
            profilePhotoId: 0,
            role: '',
        }));
    },

    setName: (name: string) => {
        set((state) => ({
            ...state,
            name: name,
        }));
    },
}));

export default useUserStore;
