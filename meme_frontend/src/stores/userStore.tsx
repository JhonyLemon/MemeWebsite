import create from 'zustand';
import useAuthStore from './authStore';

interface UserStore {
    userId: number;
    name: string;
    email: string;
    profilePhotoId: number;
    role: string;

    setUser: (
        userId: number,
        name: string,
        email: string,
        profilePhotoId: number,
        role: string,
    ) => void;

    resetUser: () => void;

    setName: (name: string) => void;
}

const useUserStore = create<UserStore>((set) => ({
    userId: 0,
    name: '',
    email: '',
    profilePhotoId: 0,
    role: '',

    setUser: (
        userId: number,
        name: string,
        email: string,
        profilePhotoId: number,
        role: string,
    ) => {
        set((state) => ({
            ...state,
            userId: userId,
            name: name,
            email: email,
            profilePhotoId: profilePhotoId,
            role: role,
        }));
    },

    resetUser: () => {
        set((state) => ({
            ...state,
            userId: 0,
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
