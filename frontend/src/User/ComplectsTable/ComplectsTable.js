import { useState } from "react";
import "./ComplectsTable.css";
import { useDispatch, useSelector } from "react-redux";
import { setUserState } from "../../slices/userStateSlice";
import { setComplectFilter } from "../../slices/complectFilterSlice";

export function ComplectsTable() {
    const dispatch = useDispatch()
    const complects = useSelector((state) => state.complects)
    const [activeComplectId, setActiveComplect] = useState(0);
    const complectsIds = useSelector((state) => state.complectsFilter.complectsFilter)

    const countDevicesInComplect = complectsIds.map((complectIndex) => {
        const item = new Map()
        item["id"] = complectIndex
        item["count"] = complects.complects[complectIndex].devicesId.length
        return item
    })
    
    const handlerComplectParams = async function (event) {
        event.preventDefault();

        dispatch(setUserState("showComplect"))
        dispatch(setComplectFilter([activeComplectId]))
    }
    
    return (
        <div>
            <table className="complect-table">
                <thead className="complect-header">
                    <tr>
                        <th className="header-text">№ комплекта</th>
                        <th className="header-text">Количество оборудования</th>
                    </tr>
                </thead>
                <tbody className="complect-body">
                    {countDevicesInComplect.map((complect) => (
                        <tr
                            className={activeComplectId === complect.id ? "active-complect" : ""}
                            onClick={() => setActiveComplect(complect.id)}
                            key={complect.id}
                            id={`complect-${complect.id}`}>
                            <td className="complect-id">{complect.id === 0 ? "Склад" : complect.id}</td>
                            <td className="complect-size">{complect.count}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <button
                className='search-button'
                onClick={handlerComplectParams}
            >
                Подробности
            </button>
        </div>
    )
}