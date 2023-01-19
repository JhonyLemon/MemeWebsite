import { useState, useEffect } from 'react';
import PostProps from '../types/props/PostProps';
import ArrowUp from '../images/up-arrow.png';
import ArrowDown from '../images/down-arrow.png';
import ViewIcon from '../images/view.png';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { Link, useNavigate } from 'react-router-dom';
import classNames from 'classnames';
import axios from 'axios';
import useAuthStore from '../stores/authStore';
import usePhotosStore from '../stores/photosStore';
import Tag from '../components/Tag';
import Comments from './Comments';
import CustomButton from './CustomButton';
import useUserStore from '../stores/userStore';
import useRefreshStore from '../stores/refreshStore';

export interface IJsonResponse {
    postId: number;
    seenCount: number;
    upVoteCount: number;
    downVoteCount: number;
    favoriteCount: number;
    yourVote: boolean;
    yourFavorite: boolean;
    yourSeen: boolean;
}

const Post = ({
    id,
    title,
    firstObject,
    upCount,
    downCount,
    seenCount,
    favCount,
    userVote,
    userFavorite,
    details,
    tags,
    account,
    creationDate,
    comments,
}: PostProps) => {
    const navigate = useNavigate();

    const { isLogged, accessToken } = useAuthStore();
    const { homeRefresh, refreshHome, commentsRefresh, refreshComments } =
        useRefreshStore();

    const [postId, setPostId] = useState<number>();
    const [yourVote, setYourVote] = useState(userVote);
    const [upVoteCount, setUpVoteCount] = useState(upCount);
    const [downVoteCount, setDownVoteCount] = useState(downCount);

    const [isFavourite, setIsFavourite] = useState(userFavorite);
    const [favoriteCount, setFavouriteCount] = useState(favCount);

    const [didUserVoted, setDidUserVoted] = useState(false);
    const [didUserSetFavorite, setDidUserSetFavorite] = useState(false);

    //States that are used in post details
    const { profilePhotoId } = useUserStore();
    const { photos } = usePhotosStore();
    const [creatorPhoto, setCreatorPhoto] = useState('');
    const [loggedUserPhoto, setLoggedUserPhoto] = useState('');
    const [userComment, setUserComment] = useState('');

    useEffect(() => {
        yourVote != undefined && setDidUserVoted(true);
        userFavorite != undefined && setDidUserSetFavorite(true);
        setPostId(id);

        //Get photo of user that created post, only in post details
        details &&
            account != null &&
            axios
                .get(
                    `http://localhost:8080/api/v1/profilepicture/${account?.profilePhotoId}`,
                )
                .then((res) => {
                    setCreatorPhoto(res.data.content);
                });

        //Get photo of user that is logged, only in post details
        details &&
            isLogged &&
            axios
                .get('http://localhost:8080/api/v1/account', {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                })
                .then((res) => {
                    let photo = '';
                    photos.forEach((picture) => {
                        if (picture.id == profilePhotoId) {
                            photo = picture.content;
                        }
                    });
                    setLoggedUserPhoto(photo);
                });
    }, []);

    //Function used to set value of user vote
    const vote = (userVote: boolean | null | undefined) => {
        if (!isLogged) {
            navigate('/zalogujsie');
            return;
        }

        axios
            .put(
                `http://localhost:8080/api/v1/post/${postId}/poststatistic/vote`,
                {
                    vote: userVote,
                },
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                },
            )
            .then((res) => {
                refreshHome(homeRefresh ? false : true);
                changeVoteNumbers(userVote);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    //Function used change color and number of votes
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

    //Function used to set post as user favourite
    const favourite = (userVote: boolean) => {
        if (!isLogged) {
            navigate('/zalogujsie');
            return;
        }

        axios
            .put(
                `http://localhost:8080/api/v1/post/${postId}/poststatistic/favorite`,
                {
                    vote: userVote,
                },
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                },
            )
            .then((res) => {
                refreshHome(homeRefresh ? false : true);
                changeHeartColor(userVote);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    //Function used to change color of heart
    const changeHeartColor = (userVote: boolean) => {
        if (!isLogged) {
            return;
        }

        let countFavourite = favoriteCount;
        if (isFavourite && !userVote) {
            setFavouriteCount(countFavourite - 1);
            setIsFavourite(false);
        } else if (!isFavourite && userVote) {
            setFavouriteCount(countFavourite + 1);
            setIsFavourite(true);
        }
    };

    //Function used to add new comment
    const addComment = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (!isLogged || userComment === '') {
            return;
        }

        axios
            .post(
                `http://localhost:8080/api/v1/post/${postId}/comment`,
                {
                    comment: userComment,
                },
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                },
            )
            .then((res) => {
                refreshComments(commentsRefresh ? false : true);
                setUserComment('');
            });
    };

    return (
        <div className="post">
            <div className="post__header">
                {details ? (
                    <p className="post__header__title">{title}</p>
                ) : (
                    <Link to={`/post/${id}`}>
                        <p className="post__header__title">{title}</p>
                    </Link>
                )}

                <div className="post__statistics__wrapper">
                    <div className="post__statistics__icon">
                        <img src={ViewIcon} />
                    </div>
                    <p className="post__statistics__count">{seenCount}</p>
                </div>
            </div>
            {details ? (
                <>
                    {firstObject.mimeType.substring(0, 5) === 'video' && (
                        <>
                            <video
                                className="post__img"
                                src={`data:${firstObject.mimeType};base64,${firstObject.content}`}
                                controls={true}
                            />
                            <p className="post__description">
                                {firstObject.description}
                            </p>
                        </>
                    )}
                    {firstObject.mimeType.substring(0, 5) === 'image' && (
                        <>
                            <Link to={`/post/${id}`}>
                                <img
                                    className="post__img"
                                    src={`data:${firstObject.mimeType};base64,${firstObject.content}`}
                                    alt="post"
                                />
                            </Link>

                            <p className="post__description">
                                {firstObject.description}
                            </p>
                        </>
                    )}
                </>
            ) : (
                <>
                    {firstObject.mimeType.substring(0, 5) === 'video' && (
                        <video
                            className="post__img"
                            src={`data:${firstObject.mimeType};base64,${firstObject.content}`}
                            controls={true}
                        />
                    )}
                    {firstObject.mimeType.substring(0, 5) === 'image' && (
                        <Link to={`/post/${id}`}>
                            <img
                                className="post__img"
                                src={`data:${firstObject.mimeType};base64,${firstObject.content}`}
                                alt="post"
                            />
                        </Link>
                    )}
                </>
            )}
            <div className="post__statistics">
                <div className="post__statistics__wrapper">
                    <div
                        className={classNames(
                            'post__statistics__icon',
                            didUserVoted && yourVote && 'green',
                        )}
                    >
                        <img
                            src={ArrowUp}
                            onClick={() => {
                                let tempYourVote: boolean | null = true;
                                if (yourVote === true) {
                                    tempYourVote = null;
                                }
                                vote(tempYourVote);
                            }}
                        />
                    </div>
                    <p className="post__statistics__count">{upVoteCount}</p>
                </div>
                <div className="post__statistics__wrapper">
                    <div
                        className={classNames(
                            'post__statistics__icon--downvote',
                            didUserVoted && !yourVote && 'red',
                        )}
                    >
                        <img
                            src={ArrowDown}
                            onClick={() => {
                                let tempYourVote: boolean | null = false;
                                if (yourVote === false) {
                                    tempYourVote = null;
                                }
                                vote(tempYourVote);
                            }}
                        />
                    </div>
                    <p className="post__statistics__count">{downVoteCount}</p>
                </div>
                <div
                    className={classNames(
                        'post__statistics__wrapper--heart',
                        didUserSetFavorite && isFavourite
                            ? 'favourite'
                            : 'no-favourite',
                    )}
                >
                    <FontAwesomeIcon
                        icon={faHeart}
                        onClick={() => {
                            let tempUserFavourite = true;
                            if (isFavourite) tempUserFavourite = false;
                            favourite(tempUserFavourite);
                        }}
                    />
                    <p className="post__statistics__count">{favoriteCount}</p>
                </div>
            </div>
            {details && (
                <>
                    <div className="post-details__info">
                        <div>
                            <p className="post-details__p">Tagi:</p>
                            <div className="post-details__tags">
                                {tags?.map((tag) => {
                                    return <Tag key={tag.id} name={tag.tag} />;
                                })}
                            </div>
                        </div>
                        <div className="post-details__creator">
                            <img
                                className="post-details__creator--photo"
                                src={`data:image/jpeg;base64,${creatorPhoto}`}
                            />
                            <div className="post-details__creator--name">
                                {account?.name}
                                <p className="post-details__creator--date">
                                    {creationDate}
                                </p>
                            </div>
                        </div>
                    </div>
                    <div className="post-details__comments">
                        <p>Komentarze</p>
                    </div>
                    {isLogged && (
                        <>
                            <form
                                className="post-details__add-comment"
                                onSubmit={addComment}
                            >
                                <div className="post-details__add-comment__container">
                                    <img
                                        className="post-details__add-comment__photo"
                                        src={`data:image/jpeg;base64,${loggedUserPhoto}`}
                                    />
                                    <textarea
                                        className="post-details__add-comment__textarea"
                                        placeholder="Napisz komentarz"
                                        rows={5}
                                        value={userComment}
                                        onChange={(
                                            e: React.ChangeEvent<HTMLTextAreaElement>,
                                        ) => {
                                            setUserComment(
                                                e.currentTarget.value,
                                            );
                                        }}
                                    />
                                </div>
                                <CustomButton
                                    styles="add-comment"
                                    text="Dodaj komentarz"
                                />
                            </form>
                        </>
                    )}
                    {comments !== undefined && comments.length > 0 ? (
                        comments.map((comment) => {
                            return <Comments key={comment.id} {...comment} />;
                        })
                    ) : (
                        <p className="post__no-comments">Brak komentarzy</p>
                    )}
                </>
            )}
        </div>
    );
};

export default Post;
