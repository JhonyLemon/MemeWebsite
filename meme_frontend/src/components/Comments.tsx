import { useEffect, useState } from 'react';
import IPhoto from '../types/IPhoto';
import IComments from '../types/IComments';
import usePhotosStore from '../stores/photosStore';

const Comments = ({
    id,
    account,
    childComments,
    replyToId,
    comment,
    commentStatistics,
}: IComments) => {
    const [profilePictures, setProfilePictures] = useState<
        IPhoto[] | undefined
    >([]);

    const { photos } = usePhotosStore();

    useEffect(() => {
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
                </div>
            </div>
        </div>
    ) : (
        <></>
    );
};

export default Comments;
