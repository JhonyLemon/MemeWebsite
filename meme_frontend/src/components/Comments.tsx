import { useEffect, useState } from 'react';
import axios from 'axios';
import IPhoto from '../types/IPhoto';
import IComments from '../types/IComments';
import usePhotosStore from '../stores/photosStore';
import useAuthStore from '../stores/authStore';
import { useNavigate } from 'react-router-dom';
import classNames from 'classnames';
import { faCircleDown, faCircleUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const Comments = ({
    id,
    account,
    childComments,
    replyToId,
    comment,
    commentStatistics,
}: IComments) => {
    const navigate = useNavigate();
    const { isLogged, accessToken } = useAuthStore();
    const { photos } = usePhotosStore();

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

    return profilePictures != null ? (
        <div className="comments-container">
            <div className="comment">
                <img
                    className="comment__photo"
                    src={`data:image/jpeg;base64,${getUserPhoto(
                        account.profilePhotoId,
                    )}`}
                />
                <div className="comment__content">
                    <p className="comment__content__title">{account.name}</p>
                    <p className="comment__content__text">{comment}</p>
                    <div className="comment__content__statistics">
                        <div
                            className={classNames(
                                'comment__content__statistics__wrapper--up',
                                didUserVoted && yourVote && 'green--comment',
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
                                    let tempYourVote: boolean | null = false;
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
            </div>
        </div>
    ) : (
        <></>
    );
};

export default Comments;
