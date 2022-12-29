export default interface IPostStatistics {
    postId: number;
    seenCount: number;
    upVoteCount: number;
    downVoteCount: number;
    favoriteCount: number;
    yourVote?: boolean;
    yourFavorite?: boolean;
    yourSeen?: boolean;
}
