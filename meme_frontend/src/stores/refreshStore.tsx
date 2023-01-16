import create from 'zustand';

interface RefreshStore {
    homeRefresh: boolean;
    commentsRefresh: boolean;

    refreshHome: (homeRefresh: boolean) => void;
    refreshComments: (commentsRefresh: boolean) => void;
}

const useRefreshStore = create<RefreshStore>((set) => ({
    homeRefresh: false,
    commentsRefresh: false,

    refreshHome: (homeRefresh: boolean) => {
        set((state) => ({
            ...state,
            homeRefresh: homeRefresh,
        }));
    },

    refreshComments: (commentsRefresh: boolean) => {
        set((state) => ({
            ...state,
            commentsRefresh: commentsRefresh,
        }));
    },
}));

export default useRefreshStore;
