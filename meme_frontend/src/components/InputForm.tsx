import InputProps from '../types/props/InputProps';

const InputForm = ({ type, placeholder, id, onChange, value }: InputProps) => {
    return (
        <div className="form">
            <input
                className="form__input"
                type={type}
                placeholder=" "
                id={id}
                onChange={onChange}
                defaultValue={value}
            />
            <label htmlFor={id} className="form__label">
                {placeholder}
            </label>
        </div>
    );
};

export default InputForm;
