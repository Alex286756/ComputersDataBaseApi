import { useDispatch, useSelector } from 'react-redux'
import './StartSearchComplect.css'
import { setUserState } from '../../slices/userStateSlice'
import { setComplectFilter } from '../../slices/complectFilterSlice'

export function StartSearchComplect() {
    const devices = useSelector((state) => state.devices);
    const types = useSelector((state) => state.types);
    const complects = useSelector((state) => state.complects);
    const dispatch = useDispatch()

     const typesList = types.index.map((type) => {
        const item = new Map()
        item["id"] = type
        item["name"] = types.types[type].name
        return item
    })

    const handlerSearchComplect = async function (event) {
        event.preventDefault();

        if (complects) {
            const complectFilter = complects.index.filter((index) => {
                let checking = true
                
                const searchNumber = document.querySelector(".search-by-number").value
                if (searchNumber && index.indexOf(searchNumber) === -1) {
                    return false
                }
                const complectDevices = complects.complects[index].devicesId
                const complectDevicesTypes = []
                complectDevices.forEach((deviceIdx) => {
                    if (!complectDevicesTypes.includes(devices.devices[deviceIdx].typeId)) {
                        complectDevicesTypes.push(devices.devices[deviceIdx].typeId[0])
                    }
                })
                const filterTypes = []
                document.querySelectorAll(".search-checkbox").forEach((item) => {
                    if (item.checked) {
                        filterTypes.push(item.id)
                    }
                })
                filterTypes.forEach((deviceIndex) => {
                    if (!complectDevicesTypes.includes(deviceIndex))
                        checking = false
                })
                return checking
            })

            dispatch(setUserState("resultOfSearchComplect"))
            dispatch(setComplectFilter(complectFilter))
        }
    }
    
    return (
        <div className='search-complects'>
            <table className="search-variant">
                <tbody className="search-body">
                    <tr className='search-number'>
                        <td>номеру</td>
                        <td>
                            <input 
                                className="search-by-number"
                                type="text"
                            ></input>
                        </td>
                    </tr>
                    <tr className='search-structure'>
                        <td>составу</td>
                        <td>
                            {typesList.map((item) => (
                                <div key={"type-" + item.id}>
                                    <input 
                                        className="search-checkbox"
                                        type="checkbox"
                                        id={item.id}
                                    >
                                    </input>
                                    <label>{item.name}</label>
                                </div>
                            ))}
                        </td>
                    </tr>
                </tbody>
            </table>

            <button
                className='search-button'
                onClick={handlerSearchComplect}
            >
                Поиск
            </button>
        </div>
    )
}