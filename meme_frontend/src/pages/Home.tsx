import Post from '../components/Post';
import { useEffect } from 'react';
import IPost from '../types/IPost';
import useAuthStore from '../stores/authStore';
import { useAxios } from '../customHooks/useAxios';
import { AxiosRequestConfig } from 'axios';
import usePhotosStore from '../stores/photosStore';
import useRefreshStore from '../stores/refreshStore';

export interface IJsonResponse {
    posts: IPost[];
    totalNumberOfPages: number;
    totalNumberOfElements: number;
}

const Home = ({ waitingRoom }: { waitingRoom: boolean }) => {
    const { isLogged, accessToken } = useAuthStore();
    const { setPhotos } = usePhotosStore();
    const { homeRefresh } = useRefreshStore();

    //Different config for axios request according to if user is logged in
    const config: AxiosRequestConfig = {
        method: 'post',
        url: `/v2/post/all`,
        data: {
            pagingAndSorting: {
                page: 0,
                size: 50,
            },
        },
        ...(isLogged && {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        }),
    };
    const [loading, response, error, request] = useAxios<IJsonResponse>(config);

    useEffect(() => {
        request();
    }, [homeRefresh]);

    return (
        <div style={{ backgroundColor: '#36393f' }}>
            <div className="posts-container">
                {response?.posts.map((post: IPost) => {
                    return waitingRoom
                        ? post.postStatistics.downVoteCount >=
                              post.postStatistics.upVoteCount && (
                              <Post
                                  key={post.id}
                                  id={post.id}
                                  title={post.title}
                                  firstObject={post.firstObject}
                                  upCount={post.postStatistics.upVoteCount}
                                  downCount={post.postStatistics.downVoteCount}
                                  seenCount={post.postStatistics.seenCount}
                                  favCount={post.postStatistics.favoriteCount}
                                  userVote={post.postStatistics.yourVote}
                                  userFavorite={
                                      post.postStatistics.yourFavorite
                                  }
                              ></Post>
                          )
                        : post.postStatistics.upVoteCount >
                              post.postStatistics.downVoteCount && (
                              <Post
                                  key={post.id}
                                  id={post.id}
                                  title={post.title}
                                  firstObject={post.firstObject}
                                  upCount={post.postStatistics.upVoteCount}
                                  downCount={post.postStatistics.downVoteCount}
                                  seenCount={post.postStatistics.seenCount}
                                  favCount={post.postStatistics.favoriteCount}
                                  userVote={post.postStatistics.yourVote}
                                  userFavorite={
                                      post.postStatistics.yourFavorite
                                  }
                              ></Post>
                          );
                })}
            </div>
        </div>
    );
};

export default Home;
