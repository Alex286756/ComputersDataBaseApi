import { useState, useEffect } from 'react';
import './Reset.css';
import { useSelector } from "react-redux";

export function Reset() {
    const [idValue, setIdValue] = useState();
    const [loginValue, setLoginValue] = useState('');
    const [passwordValue, setPasswordValue] = useState('');
    const apiURL = useSelector((state) => state.apiURL.apiURL);
    const apiJwt = useSelector((state) => state.apiURL.apiJwt);

    const handlerPatch = async function (event) {
        event.preventDefault();
        if (idValue) {
            await fetch(apiURL + `users/${idValue}`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + apiJwt,
                },
                body: JSON.stringify({
                    password: passwordValue
                    },
                ),
            });
            setPasswordValue('');
            setIdValue();
            setLoginValue('');
            window.location.replace('/admin');
        }
    }

    useEffect(() => {
        const activeUser = document.querySelector('.active-user');
        if (activeUser) {
            const userId = activeUser.id.replace('user-','');  
            setIdValue(userId);
            setLoginValue(activeUser.childNodes[0].textContent)
        }
    }, [])

    return (
        <form className="admin-reset">
            <div className='password-part'>
                <label>Новый пароль для {loginValue}:</label>
                <input 
                    className='password-edit'
                    type="password"
                    onChange={(event) => setPasswordValue(event.target.value)}
                ></input>
            </div>
            <button 
                className='reset-button'
                type="submit-button"
                onClick={handlerPatch}    
            >
                Сменить
            </button>
        </form>
    )
}