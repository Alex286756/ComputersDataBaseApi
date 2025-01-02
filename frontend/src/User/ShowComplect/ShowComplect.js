import { useDispatch, useSelector } from 'react-redux'
import './ShowComplect.css'
import { setUserState } from '../../slices/userStateSlice'
import { setComplectFilter } from '../../slices/complectFilterSlice'
import { setDevicesFilter } from '../../slices/devicesFilterSlice'

export function ShowComplect() {
    const devices = useSelector((state) => state.devices);
    const types = useSelector((state) => state.types);
    const brands = useSelector((state) => state.brands);
    const models = useSelector((state) => state.models);
    const complects = useSelector((state) => state.complects);
    const complectIndex = useSelector((state) => state.complectsFilter.complectsFilter)
    const dispatch = useDispatch()
   
    const handlerClose = async function (event) {
        event.preventDefault();

        dispatch(setUserState("start"))
        dispatch(setComplectFilter([]))
    }

    const devicesListInComplect = complects.complects[complectIndex].devicesId
        .map((deviceId) => {
            const item = new Map()
            const device = devices.devices[deviceId.id]
            item["id"] = deviceId.id
            item["type"] = types.types[device.typeId].name
            item["name"] = brands.brands[device.brandId].name + " " +
                models.models[device.modelId].name
            item["serialNumber"] = device.serialNumber
            return item
        })
    

    return (
        <div className='complect'>
            <div className='complect-number'>
                <label>
                    № комплекта:
                </label>
                <input 
                    className="complect-number-data"
                    type="text"
                    readOnly={true}
                    value={complectIndex}
                >                                
                </input>
            </div>
            <label>
                Состав:
            </label>
            <table className="complect-table">
                <thead className="complect-header">
                    <tr>
                        <th className="header-text">id</th>
                        <th className="header-text">Оборудование</th>
                        <th className="header-text">Марка, модель</th>
                        <th className="header-text">Заводской №</th>
                    </tr>
                </thead>
                <tbody className="complect-body">
                    {devicesListInComplect.map((device) => (
                        <tr
                            className='complect-data'
                            key={device.id}
                            onClick={() => {
                                dispatch(setUserState("showDevice"))
                                dispatch(setDevicesFilter([device.id]))
                            }}
                        >
                            <td>{device.id}</td>
                            <td>{device.type}</td>
                            <td>{device.name}</td>
                            <td>{device.serialnumber}</td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <button
                className='close-button'
                onClick={handlerClose}
            >
                Закрыть
            </button>
        </div>
    )
}