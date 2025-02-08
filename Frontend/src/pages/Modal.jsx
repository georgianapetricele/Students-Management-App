import { getMaximumSize } from "chart.js/helpers";
import "../css/modal.css";
import { useState } from "react";

export const Modal = ({ closeModal, onSubmit }) => {
    const [formState, setFormState] = useState({
        username: "",
        email: "",
    });

    const [errors, setErrors] = useState("");
    const usersFromLocalStorage = JSON.parse(localStorage.getItem("users")); // Retrieve students from local storage
    if(usersFromLocalStorage === null)
        var maxId = 0;
    else
        var maxId = usersFromLocalStorage.length;

    const validateForm = () => {
        const { username,email } = formState;
        if (username && email) {
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
        const newId = maxId + 1; // Increment ID for the new user
        const newUser = { ...formState, userId:newId.toString() };
        onSubmit(newUser);
        closeModal();
    };

    return (
        <div
            className="modal-container"
            onClick={(e) => {
                if (e.target.className === "modal-container") closeModal();
            }}
        >
            <div className="modal">
                <form>
                    <div className="form-group">
                        <input type="hidden" id="userId" />
                        <label htmlFor="username">Username: </label>
                        <input type="text" id="username" name="username" value={formState.username} onChange={handleChange} />
                    </div>
                  
                    <div className="form-group">
                        <label htmlFor="email">Email: </label>
                        <input type="text" id="email" name="email" value={formState.email} onChange={handleChange} />
                    </div>
                    {errors && <div className="error">{`Please include: ${errors}`}</div>}
                    <div className="form-group">
                        <button type="submit" className="btn" onClick={handleSubmit}>
                            Submit
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};
