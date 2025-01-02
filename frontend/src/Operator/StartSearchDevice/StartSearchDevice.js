import { useDispatch, useSelector } from 'react-redux';
import './StartSearchDevice.css'
import { useState } from 'react';
import { setUserState } from '../../slices/userStateSlice';
import { setDevicesFilter } from '../../slices/devicesFilterSlice';

export function StartSearchDevice() {
    const devices = useSelector((state) => state.devices);
    const types = useSelector((state) => state.types);
    const brands = useSelector((state) => state.brands);
    const models = useSelector((state) => state.models);
    const [typeValue, setTypeValue] = useState('');
    const [brandValue, setBrandValue] = useState('');
    const [modelValue, setModelValue] = useState('');
    const [serialnumberValue, setSerialnumberValue] = useState('');
    const [locationValue, setLocationValue] = useState('');
    const dispatch = useDispatch()

    const handlerSearchDevice = async function (event) {
        event.preventDefault();

        const deviceFilter = devices.index.filter((index) => {
            let checking = true
            if (typeValue != '') {
                const typeIndex = devices.devices[index].typeId
                if (types.types[typeIndex].name.indexOf(typeValue) === -1) {
                    checking = false
                }
            }
            if (brandValue != '') {
                const brandIndex = devices.devices[index].brandId
                if (brands.brands[brandIndex].name.indexOf(brandValue) === -1) {
                    checking = false
                }
            }
            if (modelValue != '') {
                const modelIndex = devices.devices[index].modelId
                if (models.models[modelIndex].name.indexOf(modelValue) === -1) {
                    checking = false
                }
            }
            if (serialnumberValue != '') {
                if (devices.devices[index].serialNumber.indexOf(serialnumberValue) === -1) {
                    checking = false
                }
            }
            if (locationValue != '') {
                if ("склад".indexOf(locationValue) != -1) {
                    if (devices.devices[index].complectId != "0") {
                        checking = false
                    }
                } else {
                    if (locationValue.indexOf(devices.devices[index].complectId) === -1) {
                        checking = false
                    }
                }
            }
            return checking
        })

        dispatch(setUserState("resultOfSearchDevice"))
        dispatch(setDevicesFilter(deviceFilter))
    }
    
    return (
        <div className='search-devices'>
            <table className="search-variant">
                <tbody className="search-body">
                    <tr className='search-type'>
                        <td>типу</td>
                        <td>
                            <input 
                                className="search-by-type"
                                type="text"
                                onChange={(event) => setTypeValue(event.target.value)}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-brand'>
                        <td>производителю</td>
                        <td>
                            <input 
                                className="search-by-brand"
                                type="text"
                                onChange={(event) => setBrandValue(event.target.value)}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-model'>
                        <td>модели</td>
                        <td>
                            <input 
                                className="search-by-model"
                                type="text"
                                onChange={(event) => setModelValue(event.target.value)}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-serialnumber'>
                        <td>заводскому номеру</td>
                        <td>
                            <input 
                                className="search-by-serialnumber"
                                type="text"
                                onChange={(event) => setSerialnumberValue(event.target.value)}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-location'>
                        <td>местонахождению</td>
                        <td>
                            <input 
                                className="search-by-location"
                                type="text"
                                onChange={(event) => setLocationValue(event.target.value)}
                            ></input>
                        </td>
                    </tr>
                </tbody>
            </table>

            <button
                className='search-button'
                onClick={handlerSearchDevice}
            >
                Поиск
            </button>
        </div>
    )
}