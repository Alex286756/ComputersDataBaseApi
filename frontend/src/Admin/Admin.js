import { Control } from './Control/Control';
import { TableHeader } from './TableHeader/TableHeader';
import './Admin.css';
import { Table } from './Table/Table';
import { useEffect } from 'react';
import { loadDataFromDB } from '../tools/loadDataFromDB'
import { useDispatch, useSelector } from 'react-redux';
import { setUsers } from '../slices/userSlice';
import { setRoles } from '../slices/roleSlice';
import { setJwt } from '../slices/urlSlice';

export function Admin() {
    const dispatch = useDispatch()
    const apiURL = useSelector((state) => state.apiURL.apiURL);
    
    useEffect(() => {
        const jwt = localStorage.getItem("token")
        dispatch(setJwt(jwt))
        loadDataFromDB(dispatch, apiURL + "users", jwt, setUsers)
        loadDataFromDB(dispatch, apiURL + "roles", jwt, setRoles)
    }, [dispatch, apiURL])

    return (
        <div className="admin-main">
            <div>
                <Control />
            </div>
            <div className="admin-right">
                <div>
                    <TableHeader />
                </div>
                <div>
                    <Table />
                </div>
            </div>
        </div>
    )
}