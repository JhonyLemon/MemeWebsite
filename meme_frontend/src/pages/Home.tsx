import Post from '../components/Post';
import { useEffect } from 'react';
import IPost from '../types/IPost';
import useAuthStore from '../stores/authStore';
import { useAxios } from '../customHooks/useAxios';
import axios, { AxiosRequestConfig } from 'axios';
import usePhotosStore from '../stores/photosStore';

export interface IJsonResponse {
    posts: IPost[];
    totalNumberOfPages: number;
    totalNumberOfElements: number;
}

const Home = () => {
    const { isLogged, accessToken } = useAuthStore();
    const { setPhotos } = usePhotosStore();

    //Different config for axios request according to if user is logged in
    const config: AxiosRequestConfig = {
        method: 'post',
        url: `/v2/post/all`,
        data: {
            pagingAndSorting: {
                page: 0,
                size: 10,
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
    }, []);

    return (
        <div style={{ backgroundColor: '#36393f' }}>
            <div className="posts-container">
                {response?.posts.map((post: IPost) => {
                    return (
                        <Post
                            key={post.id}
                            id={post.id}
                            title={post.title}
                            img={`data:image/jpeg;base64,${post.firstObjectContent}`}
                            upCount={post.postStatistics.upVoteCount}
                            downCount={post.postStatistics.downVoteCount}
                            seenCount={post.postStatistics.seenCount}
                            favCount={post.postStatistics.favoriteCount}
                            userVote={post.postStatistics.yourVote}
                            userFavorite={post.postStatistics.yourFavorite}
                        ></Post>
                    );
                })}
            </div>
        </div>
    );
};

export default Home;
