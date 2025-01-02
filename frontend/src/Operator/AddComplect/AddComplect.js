import { useDispatch, useSelector } from 'react-redux'
import './AddComplect.css'
import { setUserState } from '../../slices/userStateSlice'
import { setDevicesFilter } from '../../slices/devicesFilterSlice'
import { useState } from 'react';
import { addComplect } from '../../slices/complectSlice';
import { setComplectFilter } from '../../slices/complectFilterSlice';
import { editDevice } from '../../slices/deviceSlice';

export function AddComplect() {
    const devices = useSelector((state) => state.devices);
    const types = useSelector((state) => state.types);
    const complects = useSelector((state) => state.complects);
    const [ idValue, setIdValue ] = useState(complects.index.length)
    const dispatch = useDispatch()
    const apiURL = useSelector((state) => state.apiURL.apiURL);
    const apiJwt = useSelector((state) => state.apiURL.apiJwt);
 
    const handlerCancelDevice = async function (event) {
        event.preventDefault();

        dispatch(setUserState("start"))
        dispatch(setDevicesFilter([]))
    }

    const handlerAddDevice = async function (event) {
        event.preventDefault();

        const requestBody = []
        types.index.forEach((typeIndex) => {
            const deviceId = document.querySelector(`.select-${typeIndex}-data`).value
            if (deviceId != "отсутствует") {
                requestBody.push(deviceId)
            }
        })

        const response = await fetch(apiURL + "complects", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + apiJwt,
            },
            body: JSON.stringify({
                name: `${idValue}`,
                devicesId: requestBody
                },
            ),
        });
        const resultComplect = await response.json()

        requestBody.forEach((deviceId) => {
            const resultDevice = structuredClone(devices.devices[deviceId])
            resultDevice.complectId = idValue
            dispatch(editDevice({ index: deviceId, device: resultDevice }))
        })

        dispatch(addComplect({ index: idValue, complect: resultComplect }))
        dispatch(setComplectFilter([...complects.index, idValue]))
        dispatch(setUserState("resultOfSearchComplect"))
    }

    const devicesByTypes = new Map()
    types.index.forEach((typeIndex) => {
        const devicesFilter = devices.index.filter((deviceIndex) =>
            devices.devices[deviceIndex].typeId === typeIndex &&
            devices.devices[deviceIndex].complectId === 0)
        devicesByTypes[typeIndex] = devicesFilter
    })  

    return (
        <div className='device'>
            <table className="search-variant">
                <tbody className="search-body">
                    <tr className='search-id'>
                        <td>Номер</td>
                        <td>
                            <input 
                                className="select-id-data"
                                type="text"
                                value={idValue}
                                onChange={(event) => setIdValue(event.target.value)}
                            >                                
                            </input>
                        </td>
                    </tr>
                    {types.index.map((typeIndex) => (
                        <tr className='search-type' key={typeIndex}>
                            <td>{types.types[typeIndex].name}</td>
                            <td>
                                <select
                                    id={`list-${typeIndex}`}
                                    className={`select-${typeIndex}-data`}
                                >
                                    <option
                                        id="none"
                                        key="type-none"
                                    >
                                        отсутствует
                                    </option>  
                                    {devicesByTypes[typeIndex].map((devicesIndex) => (
                                        <option
                                            id={devicesIndex}
                                            key={`type-${devicesIndex}`}
                                        >
                                            {`${devices.devices[devicesIndex].id}`}
                                        </option>                                    
                                    ))}
                                </select>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div>
                <button
                    className='close-button'
                    onClick={handlerAddDevice}
                >
                    Добавить
                </button>
                <button
                    className='close-button'
                    onClick={handlerCancelDevice}
                >
                    Отменить
                </button>
            </div>
        </div>
    )
}