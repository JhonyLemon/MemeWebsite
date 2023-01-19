import create from 'zustand';

interface RefreshStore {
    homeRefresh: boolean;
    commentsRefresh: boolean;
    accountSettingsRefresh: boolean;

    refreshHome: (homeRefresh: boolean) => void;
    refreshComments: (commentsRefresh: boolean) => void;
    refreshAccountSettings: (accountSettingsRefresh: boolean) => void;
}

const useRefreshStore = create<RefreshStore>((set) => ({
    homeRefresh: false,
    commentsRefresh: false,
    accountSettingsRefresh: false,

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

    refreshAccountSettings: (accountSettingsRefresh: boolean) => {
        set((state) => ({
            ...state,
            accountSettingsRefresh: accountSettingsRefresh,
        }));
    },
}));

export default useRefreshStore;
