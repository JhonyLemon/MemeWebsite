import IPostStatistics from './IPostStatistics';

export default interface IPost {
    id: number;
    title: string;
    firstObjectContent: string;
    account: {
        id: number;
        name: string;
        email: string;
        creationDate: number[];
        profilePhotoId: string | null;
    };
    postStatistics: IPostStatistics;
}
