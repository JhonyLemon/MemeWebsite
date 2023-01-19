import { useEffect, useState } from 'react';
import axios from 'axios';
import IPhoto from '../types/IPhoto';
import IComments from '../types/IComments';
import usePhotosStore from '../stores/photosStore';
import useAuthStore from '../stores/authStore';
import { useNavigate } from 'react-router-dom';
import classNames from 'classnames';
import {
    faCircleDown,
    faCircleUp,
    faXmark,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import useRefreshStore from '../stores/refreshStore';
import useUserStore from '../stores/userStore';

const Comments = ({
    id,
    account,
    childComments,
    replyToId,
    comment,
    commentStatistics,
}: IComments) => {
    const navigate = useNavigate();
    const { userId, role } = useUserStore();
    const { isLogged, accessToken } = useAuthStore();
    const { photos } = usePhotosStore();
    const { commentsRefresh, refreshComments } = useRefreshStore();

    const [yourVote, setYourVote] = useState(commentStatistics.yourVote);
    const [upVoteCount, setUpVoteCount] = useState(
        commentStatistics.upVoteCount,
    );
    const [downVoteCount, setDownVoteCount] = useState(
        commentStatistics.downVoteCount,
    );
    const [profilePictures, setProfilePictures] = useState<
        IPhoto[] | undefined
    >([]);
    const [didUserVoted, setDidUserVoted] = useState(false);

    useEffect(() => {
        commentStatistics.yourVote != undefined && setDidUserVoted(true);
        setProfilePictures(photos);
    }, []);

    const getUserPhoto = (id: number) => {
        let photo = '';

        profilePictures?.forEach((picture) => {
            if (picture.id == id) {
                photo = picture.content;
            }
        });

        return photo;
    };

    const vote = (userVote: boolean | null | undefined) => {
        if (!isLogged) {
            navigate('/zalogujsie');
            return;
        }

        axios.put(
            `http://localhost:8080/api/v1/comment/${id}/commentstatistic`,
            {
                vote: userVote,
            },
            {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            },
        );
    };

    const changeVoteNumbers = (userVote: boolean | null | undefined) => {
        if (!isLogged) {
            return;
        }

        let hasVoted = true;
        if (yourVote == null && userVote) {
            setUpVoteCount((old) => old + 1);
            setYourVote(true);
        } else if (yourVote == null && !userVote) {
            setDownVoteCount((old) => old + 1);
            setYourVote(false);
        } else if (yourVote && userVote == null) {
            setUpVoteCount((old) => old - 1);
            setYourVote(null);
            hasVoted = false;
        } else if (!yourVote && userVote == null) {
            setDownVoteCount((old) => old - 1);
            setYourVote(null);
            hasVoted = false;
        } else if (yourVote && !userVote) {
            setUpVoteCount((old) => old - 1);
            setDownVoteCount((old) => old + 1);
            setYourVote(false);
        } else if (!yourVote && userVote) {
            setUpVoteCount((old) => old + 1);
            setDownVoteCount((old) => old - 1);
            setYourVote(true);
        }
        setDidUserVoted(hasVoted);
    };

    const deleteComment = (
        event: React.MouseEvent<HTMLDivElement, MouseEvent>,
    ) => {
        event.preventDefault();
        axios
            .delete(`http://localhost:8080/api/v1/comment/${id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            })
            .then((res) => {
                console.log(res);
                refreshComments(commentsRefresh ? false : true);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    return profilePictures != null ? (
        <div className="comments-container">
            <div className="comment">
                <img
                    className="comment__photo"
                    src={`data:image/jpeg;base64,${getUserPhoto(
                        account.profilePhotoId,
                    )}`}
                />
                <div className="comment__wrapper">
                    <div className="comment__content">
                        <p className="comment__content__title">
                            {account.name}
                        </p>
                        <p className="comment__content__text">{comment}</p>
                        <div className="comment__content__statistics">
                            <div
                                className={classNames(
                                    'comment__content__statistics__wrapper--up',
                                    didUserVoted &&
                                        yourVote &&
                                        'green--comment',
                                )}
                            >
                                <FontAwesomeIcon
                                    icon={faCircleUp}
                                    onClick={() => {
                                        let tempYourVote: boolean | null = true;
                                        if (yourVote === true) {
                                            tempYourVote = null;
                                        }
                                        vote(tempYourVote);
                                        changeVoteNumbers(tempYourVote);
                                    }}
                                />
                                <p className="comment__content__statistics__count">
                                    {upVoteCount}
                                </p>
                            </div>
                            <div
                                className={classNames(
                                    'comment__content__statistics__wrapper--down',
                                    didUserVoted && !yourVote && 'red--comment',
                                )}
                            >
                                <FontAwesomeIcon
                                    icon={faCircleDown}
                                    onClick={() => {
                                        let tempYourVote: boolean | null =
                                            false;
                                        if (yourVote === false) {
                                            tempYourVote = null;
                                        }
                                        vote(tempYourVote);
                                        changeVoteNumbers(tempYourVote);
                                    }}
                                />

                                <p className="comment__content__statistics__count">
                                    {downVoteCount}
                                </p>
                            </div>
                        </div>
                    </div>
                    {isLogged && role === 'MODERATOR' ? (
                        <div
                            className="comment__wrapper__delete"
                            onClick={deleteComment}
                        >
                            <p> Usuń komentarz</p>
                            <FontAwesomeIcon icon={faXmark} />
                        </div>
                    ) : (
                        account.id === userId && (
                            <div
                                className="comment__wrapper__delete"
                                onClick={deleteComment}
                            >
                                <p> Usuń komentarz</p>
                                <FontAwesomeIcon icon={faXmark} />
                            </div>
                        )
                    )}
                </div>
            </div>
        </div>
    ) : (
        <></>
    );
};

export default Comments;
