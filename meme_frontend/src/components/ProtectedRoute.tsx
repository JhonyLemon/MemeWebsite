import ProtectedRouteProps from '../types/props/ProtectedRouteProps';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({
    isLogged,
    redirectPath,
    outlet,
}: ProtectedRouteProps) => {
    if (isLogged) {
        return outlet;
    } else {
        return <Navigate to={{ pathname: redirectPath }} />;
    }
};

export default ProtectedRoute;
