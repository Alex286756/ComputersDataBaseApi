import { useDispatch, useSelector } from 'react-redux';
import "./Control.css";
import { setUserState } from '../../slices/userStateSlice';
import { setDevicesFilter } from '../../slices/devicesFilterSlice';
import { setComplectFilter } from '../../slices/complectFilterSlice';
import { deleteJwt } from "../../slices/urlSlice";

export function Control() {
    const dispatch = useDispatch()
    const devices = useSelector((state) => state.devices);
    const complects = useSelector((state) => state.complects);
    
    const handleExit = async function () {
        dispatch(deleteJwt())
        localStorage.removeItem("token")
        window.location.replace("/")
    }

    const handleAddDevice = async () => {
        dispatch(setUserState("adddevice"))
    }

    const handleSearchDevice = async () => {
        dispatch(setUserState("searchdevice"))
    }

    const handleListDevice = async () => {
        dispatch(setDevicesFilter(devices.index))
        dispatch(setUserState("resultOfSearchDevice"))
    }

    const handleAddComplect = async () => {
        dispatch(setUserState("addcomplect"))
    }

    const handleSearchComplect = async () => {
        dispatch(setUserState("searchcomplect"))
    }

    const handleListComplect = async () => {
        dispatch(setComplectFilter(complects.index))
        dispatch(setUserState("resultOfSearchComplect"))
    }

    return (
        <div className="control_panel">
            <header className="control_header">
                <div>
                    <button 
                        className="option-button"
                        onClick={() => handleAddDevice()}
                    >
                        Добавить оборудование
                    </button>
                </div>
                <div>
                    <button 
                        className="option-button"
                        onClick={() => handleSearchDevice()}
                    >
                        Поиск оборудования
                    </button>
                </div>
                <div>
                    <button 
                        className="option-button"
                        onClick={() => handleListDevice()}
                    >
                        Список оборудования
                    </button>
                </div>
                <div>
                    <button 
                        className="option-button"
                        onClick={() => handleAddComplect()}
                    >
                        Создать комплект
                    </button>
                </div>
                <div>
                    <button 
                        className="option-button"
                        onClick={() => handleSearchComplect()}
                    >
                        Поиск комплекта
                    </button>
                </div>
                <div>
                    <button 
                        className="option-button"
                        onClick={() => handleListComplect()}
                    >
                        Список комплектов
                    </button>
                </div>
            </header>

            <footer className="control_footer">
                <button 
                    className="option-button"
                    onClick={handleExit}
                >
                    Выйти
                </button>
            </footer>
        </div>
    )
}