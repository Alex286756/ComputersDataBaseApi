import { useDispatch} from 'react-redux';
import "./Control.css";
import { setUserState } from '../../slices/userStateSlice';
import { deleteJwt } from "../../slices/urlSlice";

export function Control() {
    const dispatch = useDispatch()
    
    const handleExit = async function () {
        dispatch(deleteJwt())
        localStorage.removeItem("token")
        window.location.replace("/")
    }

    const handleSearchDevice = async () => {
        dispatch(setUserState("searchdevice"))
    }

    const handleSearchComplect = async () => {
        dispatch(setUserState("searchcomplect"))
    }

    return (
        <div className="control_panel">
            <header className="control_header">
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
                        onClick={() => handleSearchComplect()}
                    >
                        Поиск комплекта
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