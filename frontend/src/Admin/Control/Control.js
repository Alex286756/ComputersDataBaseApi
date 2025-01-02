import { useState } from "react";
import "./Control.css";
import { Add } from "../Add/Add";
import { Reset } from "../Reset/Reset";
import { Change } from "../Change/Change";
import { useDispatch, useSelector } from 'react-redux';
import { deleteJwt } from "../../slices/urlSlice";

export function Control() {
    const [functionId, setFunctionId] = useState(0);
    const apiURL = useSelector((state) => state.apiURL.apiURL);
    const apiJwt = useSelector((state) => state.apiURL.apiJwt); 
    const dispatch = useDispatch()
    
    const handleExit = async function () {
        dispatch(deleteJwt())
        localStorage.removeItem("token")
        window.location.replace("/")
    }

    const handleDelete = async function () {
        const activeUser = document.querySelector('.active-user');
        if (activeUser) {
            const userId = activeUser.id.replace('user-','');
            await fetch(apiURL + `users/${userId}`, { 
                    method: "Delete",
                    headers: {
                        "Authorization": "Bearer " + apiJwt,
                    },
                }
            );
            window.location.reload();
        }
    }

    return (
        <div className="control_panel">
            <header className="control_header">
                <div>
                    <button 
                        className="option-button"
                        onClick={() => setFunctionId(1)}
                    >
                        Добавить пользователя
                    </button>
                </div>
                <div>
                    <button 
                        className="option-button"
                        onClick={() => setFunctionId(2)}
                    >
                        Сбросить пароль
                    </button>
                </div>
                <div>
                    <button 
                        className="option-button"
                        onClick={() => setFunctionId(3)}
                    >
                        Сменить роль
                    </button>
                </div>
                <div>
                    <button 
                        className="option-button"
                        onClick={handleDelete}
                    >
                        Удалить пользователя
                    </button>
                </div>
            </header>

            {(functionId === 1) && <Add />}
            {(functionId === 2) && <Reset />}
            {(functionId === 3) && <Change />}
        
            <footer className="control_footer">
                <button 
                    className="option-button"
                    onClick={handleExit}
                >
                    Выйти
                </button>
            </footer>
        </div>
    )
}