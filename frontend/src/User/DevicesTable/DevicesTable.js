import { useState } from "react";
import "./DevicesTable.css";
import { useDispatch, useSelector } from "react-redux";
import { setUserState } from "../../slices/userStateSlice";
import { setDevicesFilter } from "../../slices/devicesFilterSlice";

export function DevicesTable() {
    const dispatch = useDispatch()
    const devices = useSelector((state) => state.devices);
    const types = useSelector((state) => state.types);
    const brands = useSelector((state) => state.brands);
    const models = useSelector((state) => state.models);
    const [activeDeviceId, setActiveDevice] = useState();
    const devicesIds = useSelector((state) => state.devicesFilter.devicesFilter)

    const devicesList = devicesIds.map((deviceId) => {
        const device = devices.devices[deviceId]
        const item = new Map()
        item["id"] = device.id
        item["type"] = types.types[device.typeId].name
        item["name"] = brands.brands[device.brandId].name + " " +
            models.models[device.modelId].name
        item["location"] = device.complectId === 0 ? "На складе" :
            `Комплект № ${device.complectId}`
        return item
    })

    const handlerDeviceParams = async function (event) {
        event.preventDefault();

        if (activeDeviceId) {
            dispatch(setUserState("showDevice"))
            dispatch(setDevicesFilter([activeDeviceId]))
        }    
    }
    
    return (
        <div>
            <table className="devices-table">
                <thead className="devices-header">
                    <tr>
                        <th className="header-text">id</th>
                        <th className="header-text">Тип</th>
                        <th className="header-text">Марка, модель</th>
                        <th className="header-text">Местонахождение</th>
                    </tr>
                </thead>
                <tbody className="devices-body">
                    {devicesList.map((device) => (
                        <tr className={activeDeviceId === device.id ? "active-device" : ""}
                            onClick={() => setActiveDevice(device.id)}
                            key={device.id}
                            id={`device-${device.id}`}>
                            <td className="device-id">{device.id}</td>
                            <td className="device-type">{device.type}</td>
                            <td className="device-name">{device.name}</td>
                            <td className="device-location">{device.location}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <button
                className='search-button'
                onClick={handlerDeviceParams}
            >
                Подробности
            </button>
        </div>
    )
}