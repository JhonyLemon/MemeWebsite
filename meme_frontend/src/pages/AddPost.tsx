import axios from 'axios';
import { useState } from 'react';
import CustomButton from '../components/CustomButton';
import useAuthStore from '../stores/authStore';
import { useNavigate } from 'react-router-dom';

const AddPost = () => {
    const [postImg, setPostImg] = useState<File>();
    const [postTitle, setPostTitle] = useState<string>('');
    const [postDescription, setPostDescription] = useState<string>('');
    const { accessToken } = useAuthStore();
    const navigate = useNavigate();

    const config = {
        headers: {
            Authorization: `Bearer ${accessToken}`,
            'content-type': 'multipart/form-data',
        },
    };

    const submitHandler = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const bodyFormData: any = new FormData();
        bodyFormData.append('files', postImg);
        bodyFormData.append('title', postTitle);
        bodyFormData.append('description', postDescription);
        bodyFormData.append('visible', true);
        axios
            .post('http://localhost:8080/api/v2/post', bodyFormData, config)
            .then((res) => {
                navigate('/');
            });
    };

    return (
        <form className="add-post" onSubmit={submitHandler}>
            <p>Dodaj posta</p>
            <input
                className="add-post__file"
                type="file"
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                    const files = (e.target as HTMLInputElement).files;

                    if (files && files.length > 0) {
                        setPostImg(files[0]);
                    }
                }}
                accept="image/png, image/jpeg"
            />
            <input
                className="add-post__input"
                type="text"
                placeholder="Tytuł posta"
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                    setPostTitle(e.currentTarget.value);
                }}
            />
            <textarea
                className="add-post__input--textarea"
                placeholder="Opis"
                rows={5}
                onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => {
                    setPostDescription(e.currentTarget.value);
                }}
            />
            <CustomButton
                styles={'login-button'}
                onClick={() => {}}
                text="Dodaj"
            />
        </form>
    );
};

export default AddPost;
