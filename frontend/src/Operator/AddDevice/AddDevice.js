import { useDispatch, useSelector } from 'react-redux'
import './AddDevice.css'
import { setUserState } from '../../slices/userStateSlice'
import { setDevicesFilter } from '../../slices/devicesFilterSlice'
import { useState } from 'react';
import { addType } from '../../slices/typeSlice';
import { addDevice } from '../../slices/deviceSlice';
import { addModel } from '../../slices/modelSlica';
import { addBrand } from '../../slices/brandSlice';
import { postDataInDb } from '../../tools/postDataInDB';

export function AddDevice() {
    const devices = useSelector((state) => state.devices);
    const types = useSelector((state) => state.types);
    const brands = useSelector((state) => state.brands);
    const models = useSelector((state) => state.models);
    const apiURL = useSelector((state) => state.apiURL.apiURL);
    const apiJwt = useSelector((state) => state.apiURL.apiJwt);
    const [ idValue, setIdValue ] = useState(devices.index.length + 1)
    const [ yearValue, setYearValue ] = useState(new Date().getFullYear())
    const [ serialnumberValue, setSerialnumberValue ] = useState()
    const dispatch = useDispatch()
 
    const handlerCancelDevice = async function (event) {
        event.preventDefault();

        dispatch(setUserState("start"))
        dispatch(setDevicesFilter([]))
    }

    const handlerAddDevice = async function (event) {
        event.preventDefault();

        if (devices.index.includes(`${idValue}`)) {
            alert("Такой id уже существует в базе!")
            return
        }
        
        const typeValue = document.querySelector(".select-type-data").value
        let newDeviceTypeId = types.index.find(typeId => types.types[typeId].name === typeValue)
        if (!newDeviceTypeId) {
            const response = await postDataInDb(apiURL + "types", apiJwt, typeValue)
            newDeviceTypeId = response.id
            dispatch(addType({index: newDeviceTypeId, type: response}))
        }

        const brandValue = document.querySelector(".select-brand-data").value
        let newDeviceBrandId = brands.index.find(brandId => brands.brands[brandId].name === brandValue)
        if (!newDeviceBrandId) {
            const response = await postDataInDb(apiURL + "brands", apiJwt, brandValue)
            newDeviceBrandId = response.id
            dispatch(addBrand({index: newDeviceBrandId, brand: response}))
        }

        const modelValue = document.querySelector(".select-model-data").value
        let newDeviceModelId = models.index.find(modelId => models.models[modelId].name === modelValue)
        if (!newDeviceModelId) {
            const response = await postDataInDb(apiURL + "models", apiJwt, modelValue)
            newDeviceModelId = response.id
            dispatch(addModel({index: newDeviceModelId, model: response}))
        }

        const response = await fetch(apiURL + "devices", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + apiJwt,
            },
            body: JSON.stringify({
                id: `${idValue}`,
                typeId: newDeviceTypeId,
                brandId: newDeviceBrandId,
                modelId: newDeviceModelId,
                serialNumber: serialnumberValue,
                year: yearValue,
                complectId: 0
                },
            ),
        });
        const resultDevice = await response.json()

        dispatch(addDevice({ index: idValue, device: resultDevice }))
        dispatch(setDevicesFilter([...devices.index, idValue]))
        dispatch(setUserState("resultOfSearchDevice"))
    }

    return (
        <div className='device'>
            <table className="search-variant">
                <tbody className="search-body">
                    <tr className='search-id'>
                        <td>id</td>
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
                    <tr className='search-type'>
                        <td>Тип</td>
                        <td>
                            <input type="text" list="types" className='select-type-data'/>
                            <datalist id="types" className="types"
                            >
                                {types.index.map((typeId) =>
                                    <option
                                        value={types.types[typeId].name}
                                        id={typeId}
                                        key={`type-${typeId}`}
                                    >
                                    </option>
                                )}
                            </datalist>
                        </td>
                    </tr>
                    <tr className='search-brand'>
                        <td>Марка</td>
                        <td>
                            <input type="text" list="brands" className='select-brand-data'/>
                            <datalist id="brands" className="brands"
                            >
                                {brands.index.map((brandId) =>
                                    <option
                                        value={brands.brands[brandId].name}
                                        id={brandId}
                                        key={`brand-${brandId}`}
                                    >
                                    </option>
                                )}
                            </datalist>
                        </td>
                    </tr>
                    <tr className='search-model'>
                        <td>Модель</td>
                        <td>
                            <input type="text" list="models" className='select-model-data'/>
                            <datalist id="models" className="models"
                            >
                                {models.index.map((modelId) =>
                                    <option
                                        value={models.models[modelId].name}
                                        id={modelId}
                                        key={`model-${modelId}`}
                                    >
                                    </option>
                                )}
                            </datalist>
                        </td>
                    </tr>
                    <tr className='search-serialnumber'>
                        <td>Заводской номер</td>
                        <td>
                            <input 
                                className="select-serialnumber-data"
                                type="text"
                                value={serialnumberValue}
                                onChange={(event) => setSerialnumberValue(event.target.value)}
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-year'>
                        <td>Год выпуска</td>
                        <td>
                            <input 
                                className="device-param"
                                type="text"
                                value={yearValue}
                                onChange={(event) => setYearValue(event.target.value)}
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
                                value="На складе"
                            ></input>
                        </td>
                    </tr>
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