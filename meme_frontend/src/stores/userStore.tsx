import create from 'zustand';
import useAuthStore from './authStore';

interface UserStore {
    id: number;
    name: string;
    email: string;
    profilePhotoId: number;
    setUser: (
        id: number,
        name: string,
        email: string,
        profilePhotoId: number,
    ) => void;

    resetUser: () => void;
}

const useUserStore = create<UserStore>((set) => ({
    id: 0,
    name: '',
    email: '',
    profilePhotoId: 0,

    setUser: (
        id: number,
        name: string,
        email: string,
        profilePhotoId: number,
    ) => {
        set((state) => ({
            ...state,
            id: id,
            name: name,
            email: email,
            profilePhotoId: profilePhotoId,
        }));
    },

    resetUser: () => {
        set((state) => ({
            ...state,
            id: 0,
            name: '',
            email: '',
            profilePhotoId: 0,
        }));
    },
}));

export default useUserStore;
