import { useState } from "react";
import "./Table.css";
import { useSelector } from "react-redux";

export function Table() {
    const [activeUserId, setActiveUser] = useState();
    const users = useSelector((state) => state.users)
    
    const usersList = users.index.map((userId) => {
        const item = new Map()
        const user = users.users[userId]
        item["id"] = user.id
        item["login"] = user.name
        item["role"] = user.role.replace("ROLE_", "").toLowerCase()
        return item
    })

    return (
        <table className="users-table">
            <thead className="users-header">
                <tr>
                    <th className="header-text">Логин</th>
                    <th className="header-text">Текущая роль</th>
                </tr>
            </thead>
            <tbody className="users-body">
                {usersList.map((user) => (
                    <tr className={activeUserId === user.id ? "active-user" : ""}
                        onClick={() => setActiveUser(user.id)}
                        key={user.id}
                        id={`user-${user.id}`}
                    >
                        <td
                            className="user-login"
                            key={"login-"+user.id}
                        >{user.login}</td>
                        <td
                            className="user-role"
                            key={"role-"+user.id}
                        >{user.role}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    )
}