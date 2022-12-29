import InputProps from '../types/props/InputProps';

const InputForm = ({ type, placeholder, id, onChange }: InputProps) => {
    return (
        <div className="form">
            <input
                className="form__input"
                type={type}
                placeholder=" "
                id={id}
                onChange={onChange}
            />
            <label htmlFor={id} className="form__label">
                {placeholder}
            </label>
        </div>
    );
};

export default InputForm;
