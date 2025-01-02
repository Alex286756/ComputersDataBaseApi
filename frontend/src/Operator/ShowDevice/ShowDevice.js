import { useDispatch, useSelector } from 'react-redux'
import './ShowDevice.css'
import { setUserState } from '../../slices/userStateSlice'
import { setDevicesFilter } from '../../slices/devicesFilterSlice'
import { useState } from 'react';
import { addType } from '../../slices/typeSlice';
import { addBrand } from '../../slices/brandSlice';
import { addModel } from '../../slices/modelSlica';
import { deleteDevice, editDevice } from '../../slices/deviceSlice';
import { postDataInDb } from '../../tools/postDataInDB';
import { deleteDeviceFromComplect } from '../../slices/complectSlice';

export function ShowDevice() {
    const devices = useSelector((state) => state.devices);
    const types = useSelector((state) => state.types);
    const brands = useSelector((state) => state.brands);
    const models = useSelector((state) => state.models);
    const deviceIndex = useSelector((state) => state.devicesFilter.devicesFilter)
    const apiURL = useSelector((state) => state.apiURL.apiURL);
    const apiJwt = useSelector((state) => state.apiURL.apiJwt);

    const [typeValue, setTypeValue] = useState(types.types[devices.devices[deviceIndex].typeId].name)
    const [brandValue, setBrandValue] = useState(brands.brands[devices.devices[deviceIndex].brandId].name)
    const [modelValue, setModelValue] = useState(models.models[devices.devices[deviceIndex].modelId].name)
    const [yearValue, setYearValue] = useState(devices.devices[deviceIndex].year)
    const [serialnumberValue, setSerialnumberValue] = useState(devices.devices[deviceIndex].serialNumber)
    const dispatch = useDispatch()

    const deviceStartParams = {}

    const device = devices.devices[deviceIndex]
    deviceStartParams["type"] = types.types[device.typeId].name
    deviceStartParams["typeId"] = device.typeId
    deviceStartParams["brand"] = brands.brands[device.brandId].name
    deviceStartParams["brandId"] = device.brandId
    deviceStartParams["model"] = models.models[device.modelId].name
    deviceStartParams["modelId"] = device.modelId
    deviceStartParams["serialnumber"] = device.serialNumber
    deviceStartParams["year"] = device.year
    deviceStartParams["location"] = device.complectId === 0 ? "На складе" :
            `Комплект № ${device.complectId}`
 
    const handlerSaveDevice = async function (event) {
        event.preventDefault();
        const requestBody = {}

        let newDeviceTypeId = types.index.find(typeId => types.types[typeId].name === typeValue)
        if (!newDeviceTypeId) {
            const response = await postDataInDb(apiURL + "types", apiJwt, typeValue)
            newDeviceTypeId = response.id
            dispatch(addType({index: newDeviceTypeId, type: response}))
        }
        if (newDeviceTypeId != deviceStartParams.typeId) {
            requestBody["typeId"] = newDeviceTypeId
        }

        let newDeviceBrandId = brands.index.find(brandId => brands.brands[brandId].name === brandValue)
        if (!newDeviceBrandId) {
            const response = await postDataInDb(apiURL + "brands", apiJwt, brandValue)
            newDeviceBrandId = response.id
            dispatch(addBrand({index: newDeviceBrandId, brand: response}))
        }
        if (newDeviceBrandId != deviceStartParams.brandId) {
            requestBody["brandId"] = newDeviceBrandId
        }

        let newDeviceModelId = models.index.find(modelId => models.models[modelId].name === modelValue)
        if (!newDeviceModelId) {
            const response = await postDataInDb(apiURL + "models", apiJwt, modelValue)
            newDeviceModelId = response.id
            dispatch(addModel({index: newDeviceModelId, model: response}))
        }
        if (newDeviceModelId != deviceStartParams.modelId) {
            requestBody["modelId"] = newDeviceModelId
        }
        
        if (serialnumberValue != deviceStartParams.serialnumber) {
            requestBody["serialNumber"] = serialnumberValue
        }
        if (yearValue != deviceStartParams.year) {
            requestBody["year"] = yearValue
        }

        const response = await fetch(`${apiURL}devices/${deviceIndex}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + apiJwt,
            },
            body: JSON.stringify(requestBody),
        });
        const resultDevice = await response.json()

        dispatch(editDevice({ index: deviceIndex, device: resultDevice }))
        dispatch(setDevicesFilter(devices.index))
        dispatch(setUserState("resultOfSearchDevice"))
    }

    const handlerDeleteDevice = async function (event) {
        event.preventDefault();

        await fetch(apiURL + `devices/${deviceIndex}`, 
            { 
                method: "Delete",
                headers: {
                    "Authorization": "Bearer " + apiJwt,
                },
            }
        )
        dispatch(deleteDeviceFromComplect({ index: device.complectId, deviceId: deviceIndex[0] }))
        dispatch(deleteDevice({ index: deviceIndex[0] }))
        const newFilter = devices.index.filter((item) => item != deviceIndex[0])
        dispatch(setDevicesFilter(newFilter))
        dispatch(setUserState("resultOfSearchDevice"))
    }

    const handlerClose = async function (event) {
        event.preventDefault();

        dispatch(setUserState("start"))
        dispatch(setDevicesFilter([]))
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
                                value={deviceIndex}
                                readOnly={true}
                            >                                
                            </input>
                        </td>
                    </tr>
                    <tr className='search-type'>
                        <td>Тип</td>
                        <td>
                            <input
                                type="text"
                                list="types"
                                className='select-type-data'
                                value={typeValue}
                                onChange={(event) => setTypeValue(event.target.value)}
                            />
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
                            <input
                                type="text"
                                list="brands"
                                className='select-brand-data'
                                value={brandValue}
                                onChange={(event) => setBrandValue(event.target.value)}
                            />
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
                            <input
                                type="text"
                                list="models"
                                className='select-model-data'
                                value={modelValue}
                                onChange={(event) => setModelValue(event.target.value)}
                            />
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
                                value={deviceStartParams.location}
                            ></input>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div>
                <button
                    className='close-button'
                    onClick={handlerSaveDevice}
                >
                    Сохранить
                </button>
                <button
                    className='close-button'
                    onClick={handlerDeleteDevice}
                >
                    Списать
                </button>
                <button
                    className='close-button'
                    onClick={handlerClose}
                >
                    Закрыть
                </button>
            </div>
        </div>
    )
}