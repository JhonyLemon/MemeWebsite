export default interface ProtectedRouteProps {
    isLogged: boolean;
    redirectPath: string;
    outlet: JSX.Element;
}
