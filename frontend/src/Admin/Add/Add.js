import { useState } from 'react';
import './Add.css';
import { useSelector } from 'react-redux';

export function Add() {
    const [loginValue, setLoginValue] = useState('');
    const [passwordValue, setPasswordValue] = useState('');
    const [roleValue, setRoleValue] = useState('ROLE_USER');
    const apiURL = useSelector((state) => state.apiURL.apiURL);
    const apiJwt = useSelector((state) => state.apiURL.apiJwt);

    const handlerSubmit = async function (event) {
        event.preventDefault();
        await fetch(apiURL + "users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + apiJwt,
            },
            body: JSON.stringify({
                name: loginValue,
                password: passwordValue,
                role: roleValue
                },
            ),
        });
        setLoginValue('');
        setPasswordValue('');
        setRoleValue('');
        window.location.replace('/admin');
    }

    return (
        <div className="admin-add">
            <form>
                <label>Добавление пользователя</label>
                <div className='login-part'>
                    <label>Логин:</label>
                    <input 
                        className="login-edit"
                        type="text"
                        onChange={(event) => setLoginValue(event.target.value)}
                    ></input>
                </div>
                <div className='role-part'>
                    <label>Роль:</label>
                    <select className="role"
                        onChange={(event) => setRoleValue(event.target.value)}
                    >
                        <option value="ROLE_USER">Пользователь</option>
                        <option value="ROLE_ADMIN">Администратор</option>
                        <option value="ROLE_OPERATOR">Оператор</option>
                    </select>
                </div>
                <div className='password-part'>
                    <label>Пароль:</label>
                    <input 
                        className='password-edit'
                        type="password"
                        onChange={(event) => setPasswordValue(event.target.value)}
                    ></input>
                </div>
                <button 
                    className='add-button'
                    type="submit-button" 
                    onClick={handlerSubmit}
                >
                    Добавить
                </button>
            </form>
        </div>
    )
}