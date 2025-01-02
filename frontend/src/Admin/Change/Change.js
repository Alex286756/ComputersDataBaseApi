import { useState, useEffect } from 'react';
import './Change.css';
import { useSelector } from 'react-redux';

export function Change() {
    const [idValue, setIdValue] = useState();
    const [loginValue, setLoginValue] = useState('');
    const [roleValue, setRoleValue] = useState('ROLE_USER');
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
                    role: roleValue
                    },
                ),
            });
            setRoleValue('');
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
        <form className="admin-change">
            <label>Поменять роль {loginValue}</label>
            <div className='role-part'>
                <select 
                    className="role-edit"
                    onChange={(event) => setRoleValue(event.target.value)}
                >
                    <option value="ROLE_USER">Пользователь</option>
                    <option value="ROLE_ADMIN">Администратор</option>
                    <option value="ROLE_OPERATOR">Оператор</option>
                </select>
            </div>
            <button 
                className='role-button'
                type="submit-button"
                onClick={handlerPatch}
            >
                Сохранить
            </button>
        </form>
    )
}