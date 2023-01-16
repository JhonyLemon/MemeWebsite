import IAccountDetails from '../IAccountDetails';
import IComments from '../IComments';
import ITag from '../ITag';

export default interface PostProps {
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
    upCount: number;
    downCount: number;
    seenCount: number;
    favCount: number;
    userVote: boolean | undefined | null;
    userFavorite: boolean | undefined | null;
    details?: boolean;
    creationDate?: string;
    tags?: [ITag];
    account?: IAccountDetails;
    comments?: [IComments];
}
