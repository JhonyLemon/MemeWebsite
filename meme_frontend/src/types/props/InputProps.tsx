export default interface InputProps {
    type: string;
    placeholder: string;
    id: string;
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}
