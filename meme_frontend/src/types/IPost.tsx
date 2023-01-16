import IPostStatistics from './IPostStatistics';

export default interface IPost {
    id: number;
    title: string;
    firstObject: {
        id: number;
        postId: number;
        content: string;
        description: string;
        fileName: string;
        mimeType: string;
    };
    account: {
        id: number;
        name: string;
        email: string;
        creationDate: number[];
        profilePhotoId: string | null;
    };
    postStatistics: IPostStatistics;
}
