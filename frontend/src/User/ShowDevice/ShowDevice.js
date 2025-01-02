import { useDispatch, useSelector } from 'react-redux'
import './ShowDevice.css'
import { setUserState } from '../../slices/userStateSlice'
import { setDevicesFilter } from '../../slices/devicesFilterSlice'

export function ShowDevice() {
    const devices = useSelector((state) => state.devices);
    const types = useSelector((state) => state.types);
    const brands = useSelector((state) => state.brands);
    const models = useSelector((state) => state.models);
    const deviceIndex = useSelector((state) => state.devicesFilter.devicesFilter)
    const dispatch = useDispatch()

    const handlerSearchDevice = async function (event) {
        event.preventDefault();

        dispatch(setUserState("start"))
        dispatch(setDevicesFilter([]))
    }

    const deviceParams = {}

    const device = devices.devices[deviceIndex]
    deviceParams["id"] = device.id
    deviceParams["type"] = types.types[device.typeId].name
    deviceParams["brand"] = brands.brands[device.brandId].name
    deviceParams["model"] = models.models[device.modelId].name
    deviceParams["serialNumber"] = device.serialNumber
    deviceParams["year"] = device.year
    deviceParams["location"] = device.complectId === 0 ? "На складе" :
            `Комплект № ${device.complectId}`

    return (
        <div className='device'>
            <table className="search-variant">
                <tbody className="search-body">
                    <tr className='search-type'>
                        <td>id</td>
                        <td>
                            <input 
                                className="device-param"
                                type="text"
                                readOnly={true}
                                value={deviceParams.id}
                            >                                
                            </input>
                        </td>
                    </tr>
                    <tr className='search-type'>
                        <td>Тип</td>
                        <td>
                            <input 
                                className="device-param"
                                type="text"
                                readOnly={true}
                                value={deviceParams.type}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-brand'>
                        <td>Марка</td>
                        <td>
                            <input 
                                className="device-param"
                                type="text"
                                readOnly={true}
                                value={deviceParams.brand}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-model'>
                        <td>Модель</td>
                        <td>
                            <input 
                                className="device-param"
                                type="text"
                                readOnly={true}
                                value={deviceParams.model}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-serialnumber'>
                        <td>Заводской номер</td>
                        <td>
                            <input 
                                className="device-param"
                                type="text"
                                readOnly={true}
                                value={deviceParams.serialNumber}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-year'>
                        <td>Год выпуска</td>
                        <td>
                            <input 
                                className="device-param"
                                type="text"
                                readOnly={true}
                                value={deviceParams.year}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-location'>
                        <td>Местонахождение</td>
                        <td>
                            <input 
                                className="device-param"
                                type="text"
                                readOnly={true}
                                value={deviceParams.location}
                            ></input>
                        </td>
                    </tr>
                </tbody>
            </table>

            <button
                className='close-button'
                onClick={handlerSearchDevice}
            >
                Закрыть
            </button>
        </div>
    )
}