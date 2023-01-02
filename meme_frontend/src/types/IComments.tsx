import IAccount from './IAccount';

export default interface IComments {
    id: number;
    account: IAccount;
    childComments: [string];
    replyToId: number;
    comment: string;
    commentStatistics: {
        commentId: number;
        upVoteCount: number;
        downVoteCount: number;
        yourVote: boolean | undefined | null;
    };
}
