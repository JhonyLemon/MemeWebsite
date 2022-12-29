import create from 'zustand';
import IPhoto from '../types/IPhoto';

interface PhotosStore {
    photos: IPhoto[];
    setPhotos: (photos: IPhoto[]) => void;
}

const usePhotosStore = create<PhotosStore>((set) => ({
    photos: [],

    setPhotos: (photos: IPhoto[]) => {
        set((state) => ({
            ...state,
            photos: photos,
        }));
    },
}));

export default usePhotosStore;
