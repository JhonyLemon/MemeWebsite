import IPost from '../types/IPost';
import CustomButton from '../components/CustomButton';
import React, { useEffect, useState } from 'react';
import InputForm from './InputForm';
import axios from 'axios';
import useAuthStore from '../stores/authStore';
import ITag from '../types/ITag';
import Select from 'react-dropdown-select';
import useRefreshStore from '../stores/refreshStore';

const PostToEdit = ({ post }: { post: IPost }) => {
    const { accessToken } = useAuthStore();
    const { accountSettingsRefresh, refreshAccountSettings } =
        useRefreshStore();

    const [editPost, setEditPost] = useState<boolean>(false);
    const [activePost, setPost] = useState<IPost>();

    const [tagsList, setTagsList] = useState<ITag[]>([]);

    const [newTags, setNewTags] = useState<ITag[]>([]);
    const [newPostImg, setNewPostImg] = useState<File>();
    const [postTitle, setPostTitle] = useState<string>('');
    const [postDescription, setPostDescription] = useState<string>('');

    const [displayNewImg, setDisplayNewImg] = useState('');

    useEffect(() => {
        setPost(post);
        axios
            .post(
                'http://localhost:8080/api/v1/tag/all',
                {
                    pagingAndSorting: {
                        page: 0,
                        size: 400,
                        sortBy: 'id',
                        sortDirection: 'asc',
                    },
                },
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                },
            )
            .then((res) => {
                setTagsList(res.data.tags);
            })
            .catch((err) => console.log(err));

        axios
            .get(`http://localhost:8080/api/v2/post/${post.id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            })
            .then((res) => {
                setNewTags(res.data.tags);
            })
            .catch((err) => console.log(err));
    }, []);

    useEffect(() => {
        newPostImg !== undefined && getBase64(newPostImg);
    }, [newPostImg]);

    const getBase64 = (file: File) => {
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function () {
            setDisplayNewImg(reader.result as string);
        };
    };

    const handleEditPost = () => {
        setEditPost(editPost ? false : true);
    };

    const getTagsId = (tags: ITag[]) => {
        let tagsArray: number[] = [];

        tags.map((tag) => {
            tagsArray.push(tag.id);
        });

        return tagsArray;
    };

    const submitHandler = (e: React.FormEvent<HTMLButtonElement>) => {
        e.preventDefault();

        const bodyFormData: any = new FormData();
        newPostImg !== undefined && bodyFormData.append('files', newPostImg);
        bodyFormData.append('title', postTitle);
        bodyFormData.append('description', postDescription);
        bodyFormData.append('visible', true);
        bodyFormData.append('tags', getTagsId(newTags));

        axios
            .put(`http://localhost:8080/api/v2/post/${post.id}`, bodyFormData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                    'content-type': 'multipart/form-data',
                },
            })
            .then((res) => {
                console.log(res);
                refreshAccountSettings(accountSettingsRefresh ? false : true);
            });

        console.log(bodyFormData);
    };

    return (
        <div className="post-to-edit__form">
            <div className="post-to-edit">
                <div className="post-to-edit__wrapper">
                    <p className="post-to-edit__text">{post.title}</p>
                </div>
                <CustomButton
                    text="Edytuj posta"
                    styles="login-button"
                    onClick={() => {
                        handleEditPost();
                    }}
                />
            </div>
            {editPost && (
                <div className="post-to-edit__inputs">
                    <input
                        className="add-post__input"
                        type="text"
                        placeholder="Tytuł posta"
                        value={postTitle === '' ? activePost?.title : postTitle}
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                            setPostTitle(e.currentTarget.value);
                        }}
                    />
                    <Select
                        options={tagsList}
                        labelField="tag"
                        valueField="id"
                        onChange={(values) => setNewTags(values)}
                        values={newTags}
                        multi={true}
                        placeholder="Dodaj tagi"
                    />
                    {newPostImg !== undefined &&
                        newPostImg.type.substring(0, 5) === 'image' && (
                            <img
                                className="post-to-edit__img"
                                src={displayNewImg}
                            />
                        )}
                    {newPostImg !== undefined &&
                        newPostImg.type.substring(0, 5) === 'video' && (
                            <video
                                className="post-to-edit__img"
                                src={displayNewImg}
                                controls={true}
                            />
                        )}
                    {newPostImg === undefined &&
                        activePost?.firstObject.mimeType.substring(0, 5) ===
                            'video' && (
                            <>
                                <video
                                    className="post-to-edit__img"
                                    src={`data:${activePost?.firstObject.mimeType};base64,${activePost?.firstObject.content}`}
                                    controls={true}
                                />
                            </>
                        )}
                    {newPostImg === undefined &&
                        activePost?.firstObject.mimeType.substring(0, 5) ===
                            'image' && (
                            <>
                                <img
                                    className="post-to-edit__img"
                                    src={`data:${activePost?.firstObject.mimeType};base64,${activePost?.firstObject.content}`}
                                    alt="post"
                                />
                            </>
                        )}
                    <input
                        className="add-post__file"
                        type="file"
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                            e.preventDefault();
                            const files = (e.target as HTMLInputElement).files;

                            if (files && files.length > 0) {
                                setNewPostImg(files[0]);
                            }
                        }}
                        accept="image/png, image/jpeg, image/gif, video/mp4"
                    />
                    <textarea
                        className="add-post__input--textarea"
                        placeholder="Opis"
                        rows={5}
                        value={
                            postDescription === ''
                                ? activePost?.firstObject.description
                                : postDescription
                        }
                        onChange={(
                            e: React.ChangeEvent<HTMLTextAreaElement>,
                        ) => {
                            setPostDescription(e.currentTarget.value);
                        }}
                    />
                    <CustomButton
                        text="Zatwiedź zmiany"
                        styles="login-button"
                        onClick={submitHandler}
                    />
                </div>
            )}
        </div>
    );
};

export default PostToEdit;
