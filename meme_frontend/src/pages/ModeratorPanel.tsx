import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../stores/authStore';
import useUserStore from '../stores/userStore';
import IPost from '../types/IPost';
import PostToEdit from '../components/PostToEdit';
import useRefreshStore from '../stores/refreshStore';

const ModeratorPanel = () => {
    const navigate = useNavigate();
    const { role } = useUserStore();
    const { accessToken } = useAuthStore();
    const { accountSettingsRefresh } = useRefreshStore();

    const [changePosts, setChangePosts] = useState<boolean>(true);

    const [postsToEdit, setPostsToEdit] = useState<IPost[]>([]);

    const handleChangePosts = () => {
        axios
            .post(
                `http://localhost:8080/api/v2/post/all`,
                {
                    data: {
                        pagingAndSorting: {
                            page: 0,
                            size: 50,
                        },
                    },
                },
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                },
            )
            .then((res) => {
                setPostsToEdit(res.data.posts);
            })
            .catch((err) => console.log(err));
        setChangePosts(changePosts ? false : true);
    };

    useEffect(() => {
        console.log(role);
        role !== 'MODERATOR'
            ? navigate('/')
            : axios
                  .post(
                      `http://localhost:8080/api/v2/post/all`,
                      {
                          data: {
                              pagingAndSorting: {
                                  page: 0,
                                  size: 50,
                              },
                          },
                      },
                      {
                          headers: {
                              Authorization: `Bearer ${accessToken}`,
                          },
                      },
                  )
                  .then((res) => {
                      setPostsToEdit(res.data.posts);
                  })
                  .catch((err) => console.log(err));
    }, [accountSettingsRefresh]);

    return (
        <div style={{ backgroundColor: '#36393f' }}>
            <div className="moderator-panel">
                {changePosts ? (
                    postsToEdit.length > 0 &&
                    postsToEdit.map((post, id) => {
                        return <PostToEdit key={id} post={post} />;
                    })
                ) : (
                    <></>
                )}
            </div>
        </div>
    );
};

export default ModeratorPanel;
