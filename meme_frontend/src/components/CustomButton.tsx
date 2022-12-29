import CustomButtonProps from '../types/props/CustomButtonProps';

const CustomButton = ({ styles, text, onClick }: CustomButtonProps) => {
    return (
        <button className={styles} type="submit" onClick={onClick}>
            {text}
        </button>
    );
};

export default CustomButton;
