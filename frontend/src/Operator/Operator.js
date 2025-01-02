import { useDispatch, useSelector } from 'react-redux';
import { Control } from './Control/Control';
import { TableHeader } from './TableHeader/TableHeader';
import './Operator.css';
import { StartSearchDevice } from './StartSearchDevice/StartSearchDevice';
import { DevicesTable } from './DevicesTable/DevicesTable';
import { ShowDevice } from './ShowDevice/ShowDevice';
import { StartSearchComplect } from './StartSearchComplect/StartSearchComplect';
import { ComplectsTable } from './ComplectsTable/ComplectsTable';
import { ShowComplect } from './ShowComplect/ShowComplect';
import { useEffect } from 'react';
import { loadDataFromDB } from '../tools/loadDataFromDB';
import { setDevices } from '../slices/deviceSlice';
import { setComplects } from '../slices/complectSlice';
import { setBrands } from '../slices/brandSlice';
import { setModels } from '../slices/modelSlica';
import { setTypes } from '../slices/typeSlice';
import { AddDevice } from './AddDevice/AddDevice';
import { AddComplect } from './AddComplect/AddComplect';
import { setJwt } from '../slices/urlSlice';

const texts = {
    "start": "Добро пожаловать на склад",
    "adddevice": "Данные нового оборудования",
    "searchdevice": "Поиск оборудования по...",
    "resultOfSearchDevice": "Найдено следующее оборудование:",
    "showDevice": "Данные оборудования:",
    "addcomplect": "Собрать комплект",
    "searchcomplect": "Поиск комплекта по...",
    "resultOfSearchComplect": "Найдены следующие комплекты:",
    "showComplect": "Данные комплекта:",
}

export function Operator() {
    const { userState } = useSelector((state) => state.userState)
    const { apiURL, apiJwt } = useSelector((state) => state.apiURL)
    const dispatch = useDispatch()

    useEffect(() => {
        const jwt = localStorage.getItem("token")
        dispatch(setJwt(jwt))
        loadDataFromDB(dispatch, apiURL + "brands", apiJwt, setBrands)
        loadDataFromDB(dispatch, apiURL + "complects", apiJwt, setComplects)
        loadDataFromDB(dispatch, apiURL + "devices", apiJwt, setDevices)
        loadDataFromDB(dispatch, apiURL + "models", apiJwt, setModels)
        loadDataFromDB(dispatch, apiURL + "types", apiJwt, setTypes)
    }, [dispatch, apiURL, apiJwt])

    return (
        <div className="user-main">
            <Control />
            <div className="user-right">
                <div>
                    {<TableHeader title={texts[userState]} />}
                </div>
                <div>
                    {userState === "start" && <img src='./assets/store.jpg' alt='' />}

                    {userState === "adddevice" && <AddDevice />}
                    {userState === "searchdevice" && <StartSearchDevice />}
                    {userState === "resultOfSearchDevice" && <DevicesTable />}
                    {userState === "showDevice" && <ShowDevice />}

                    {userState === "addcomplect" && <AddComplect />}
                    {userState === "searchcomplect" && <StartSearchComplect />}
                    {userState === "resultOfSearchComplect" && <ComplectsTable />}
                    {userState === "showComplect" && <ShowComplect />}
                </div>
            </div>
        </div>
    )
}