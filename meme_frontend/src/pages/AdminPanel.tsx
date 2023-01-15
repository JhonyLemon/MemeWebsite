import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../stores/authStore';
import usePhotosStore from '../stores/photosStore';
import useUserStore from '../stores/userStore';
import IAccountDetails from '../types/IAccountDetails';
import IPhoto from '../types/IPhoto';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const AdminPanel = () => {
    const { accessToken } = useAuthStore();
    const { role } = useUserStore();
    const navigate = useNavigate();
    const { photos } = usePhotosStore();
    const [profilePictures, setProfilePictures] = useState<
        IPhoto[] | undefined
    >([]);
    const [allUsers, setAllUsers] = useState<IAccountDetails[]>([]);

    const [refreshComponent, setRefreshComponent] = useState<boolean>(false);

    useEffect(() => {
        role !== 'ADMIN'
            ? navigate('/')
            : axios
                  .post(
                      `http://localhost:8080/api/v1/account/all`,
                      {
                          pagingAndSorting: {
                              page: 0,
                              size: 20,
                          },
                      },
                      {
                          headers: {
                              Authorization: `Bearer ${accessToken}`,
                          },
                      },
                  )
                  .then((res) => {
                      setAllUsers(res.data.accounts);
                  })
                  .catch((err) => console.log(err));

        setProfilePictures(photos);
    }, [refreshComponent]);

    const getUserPhoto = (id: number) => {
        let photo = '';

        profilePictures?.forEach((picture) => {
            if (picture.id == id) {
                photo = picture.content;
            }
        });

        return photo;
    };

    const deleteUser = (id: number) => {
        axios
            .delete(`http://localhost:8080/api/v1/account/${id}`, {
                headers: { Authorization: `Bearer ${accessToken}` },
            })
            .then((res) => {
                console.log(res);
                setRefreshComponent(refreshComponent ? false : true);
            })
            .catch((err) => console.log(err));
    };

    return (
        <div className="admin-panel">
            {allUsers.map((user, id) => {
                return (
                    <div className="admin-panel__user" key={id}>
                        <img
                            className="admin-panel__user__img"
                            src={`data:image/jpeg;base64,${getUserPhoto(
                                user.profilePhotoId,
                            )}`}
                        />
                        <p className="admin-panel__user__name">{user.name}</p>
                        <p className="admin-panel__user__role">
                            {user.role.role}
                        </p>
                        <div
                            className="admin-panel__user--delete"
                            onClick={() => {
                                deleteUser(user.id);
                            }}
                        >
                            <p>Usuń</p>
                            <FontAwesomeIcon icon={faXmark} />
                        </div>
                    </div>
                );
            })}
        </div>
    );
};

export default AdminPanel;
