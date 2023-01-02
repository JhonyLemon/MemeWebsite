import IAccountDetails from '../IAccountDetails';
import IComments from '../IComments';
import ITag from '../ITag';

export default interface PostProps {
    id: number;
    title: string;
    img: any;
    upCount: number;
    downCount: number;
    seenCount: number;
    favCount: number;
    userVote: boolean | undefined | null;
    userFavorite: boolean | undefined | null;
    details?: boolean;
    description?: string;
    creationDate?: string;
    tags?: [ITag];
    account?: IAccountDetails;
    comments?: [IComments];
}
