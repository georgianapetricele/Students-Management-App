import "../css/modal.css";
import { useState } from "react";

export const ModalEdit = ({ closeModal, onSubmit, defaultValue, userId }) => {
    const [formState, setFormState] = useState(defaultValue || {
        username: "",
        email: "",
    });

    const [errors, setErrors] = useState("");

    const validateForm = (e) => {
        if (formState.username && formState.email) {
            setErrors("");
            return true;
        } else {
            let errorFields = [];
            for (const [key, value] of Object.entries(formState)) {
                if (!value) {
                    errorFields.push(key);
                }
            }
            setErrors(errorFields.join(", "));
            return false;
        }
    };

    const handleChange = (e) => {
        setFormState({
            ...formState,
            [e.target.name]: e.target.value
        });
    };

    
    const handleSubmit = (e) => {
        e.preventDefault();
        if (!validateForm()) return;
        onSubmit(userId, formState);
        closeModal();
    }

    return (
        <div className="modal-container" onClick={(e) => {
            if (e.target.className === "modal-container")
                closeModal();
        }}>
            <div className="modal">
                <form>
                    <div className="form-group">
                        <label htmlFor="username">Username:</label>
                        <input type="text" id="username" name="username" value={formState.username} onChange={handleChange} />
                    </div>
                 
                    <div className="form-group">
                        <label htmlFor="email">Email:</label>
                        <input type="text" id="email" name="email" value={formState.email} onChange={handleChange} />
                    </div>
                    {/* {errors && <div className="error">{`Please include: ${errors}`}</div>} */}
                    <div className="form-group">
                        <button type="submit" className="btn" onClick={handleSubmit}>Submit</button>
                    </div>
                </form>
            </div>
        </div>
    );
};
