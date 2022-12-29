import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useAxios } from '../customHooks/useAxios';
import { AxiosRequestConfig } from 'axios';
import useAuthStore from '../stores/authStore';
import IPostDetails from '../types/IPostDetails';
import Post from '../components/Post';

const PostDetails = () => {
    const { id } = useParams();
    const { isLogged, accessToken } = useAuthStore();

    const config: AxiosRequestConfig = {
        method: 'get',
        url: `/v2/post/${id}`,
        ...(isLogged && {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        }),
    };
    const [loading, response, error, request] = useAxios<IPostDetails>(config);

    useEffect(() => {
        request();
    }, []);

    return response ? (
        <div className="post-details__container">
            <div className="post-details">
                <Post
                    key={response.id}
                    id={response.id}
                    title={response.title}
                    img={`data:image/jpeg;base64,${response.postObjects[0].content}`}
                    upCount={response.postStatistics.upVoteCount}
                    downCount={response.postStatistics.downVoteCount}
                    seenCount={response.postStatistics.seenCount}
                    favCount={response.postStatistics.favoriteCount}
                    userVote={response.postStatistics.yourVote}
                    userFavorite={response.postStatistics.yourFavorite}
                    details={true}
                    creationDate={response.creationDate}
                    tags={response.tags}
                    account={response.account}
                    comments={response.comments}
                ></Post>
            </div>
        </div>
    ) : (
        <></>
    );
};

export default PostDetails;
