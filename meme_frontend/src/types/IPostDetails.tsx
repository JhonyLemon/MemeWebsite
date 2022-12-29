import IAccountDetails from './IAccountDetails';
import IComments from './IComments';
import ITag from './ITag';
import IPostStatistics from './IPostStatistics';

export default interface IPostDetails {
    id: number;
    title: string;
    creationDate: string;
    visible: boolean;
    postObjects: [
        {
            id: number;
            postId: number;
            fileName: string;
            content: string;
            mimeType: string;
            description: string;
        },
    ];
    tags: [ITag];
    comments: [IComments];
    account: IAccountDetails;
    postStatistics: IPostStatistics;
}
