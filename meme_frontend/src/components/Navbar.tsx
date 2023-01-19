import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../stores/authStore';
import useUserStore from '../stores/userStore';

const Navbar = () => {
    const { isLogged, logoutUser } = useAuthStore();
    const { resetUser, role } = useUserStore();
    const navigate = useNavigate();

    return (
        <div className="navbar-container">
            <nav className="navbar">
                <Link to="/" className="navbar__item">
                    Główna
                </Link>
                <Link to="poczekalnia" className="navbar__item">
                    Poczekalnia
                </Link>
                {isLogged ? (
                    <>
                        <Link to="dodajpost" className="navbar__item--add-post">
                            Dodaj posta
                        </Link>
                        <Link to="twojekonto" className="navbar__item">
                            Twoje konto
                        </Link>
                        {role === 'ADMIN' && (
                            <Link
                                className="navbar__item--add-post"
                                to="paneladmina"
                            >
                                Panel admina
                            </Link>
                        )}
                        {role === 'MODERATOR' && (
                            <Link
                                className="navbar__item--add-post"
                                to="panelmoderatora"
                            >
                                Panel moderatora
                            </Link>
                        )}
                        <button
                            className="navbar__item--logout"
                            onClick={() => {
                                logoutUser();
                                resetUser();
                                localStorage.removeItem('accessToken');
                                localStorage.removeItem('userId');
                                localStorage.removeItem('userName');
                                localStorage.removeItem('userEmail');
                                localStorage.removeItem('userProfilePhotoId');
                                localStorage.removeItem('userRole');
                                navigate('/');
                                window.location.reload();
                            }}
                        >
                            Wyloguj się
                        </button>
                    </>
                ) : (
                    <Link to="zalogujsie" className="navbar__item">
                        Zaloguj sie
                    </Link>
                )}
            </nav>
        </div>
    );
};

export default Navbar;
