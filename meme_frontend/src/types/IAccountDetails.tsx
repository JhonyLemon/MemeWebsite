export default interface IAccountDetails {
    id: number;
    name: string;
    email: string;
    creationDate: string;
    profilePhotoId: number;
    role: {
        id: number;
        role: string;
        permissions: [
            {
                id: number;
                permission: string;
            },
        ];
    };
    enabled: boolean;
    banned: boolean;
}
