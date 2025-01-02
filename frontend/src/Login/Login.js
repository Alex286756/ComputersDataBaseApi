import { useEffect } from "react";
import { useDispatch, useSelector } from 'react-redux'
import "./Login.css";

export function Login() {
    const apiURL = useSelector((state) => state.apiURL);
    const dispatch = useDispatch()

    useEffect(() => {
        const loginEl = document.querySelector(".LoginInput");
        const passwordEl = document.querySelector(".PasswordInput");
        const buttonEl = document.querySelector(".LoginButton");
        
        buttonEl.onclick = async function (event) {
            event.preventDefault();
            
            try {
                const responseAPI = await fetch(apiURL.apiURL + "login/sign-in", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        "username": loginEl.value,
                        "password": passwordEl.value
                    }),
                });
                const response = await responseAPI.json();
                const token = response.accessToken
                localStorage.setItem("token", token)
                const roleName = response.role.replace("ROLE_", "")
                if (roleName === "UNKNOWN") {
                    console.log(`${loginEl.value} и ${passwordEl.value} не подходят!`);
                } else {
                    window.location.replace("../" + roleName.toLowerCase());
                }
            } catch(err) {
                console.log(err);
            }
        };
  }, [apiURL, dispatch])

  return (
    <form className="LoginPage">
        <div className="UserName">
            <label className="Label">Логин:</label>
            <input type="text" className="LoginInput"></input>
        </div>
        <div className="UserPassword">
            <label className="Label">Пароль:</label>
            <input type="password" className="PasswordInput"></input>
        </div>
        <div className="Button">
            <button className="LoginButton">Войти</button>
        </div>
    </form>
  );

} 