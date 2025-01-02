import { Route, Routes, BrowserRouter } from 'react-router-dom';
import './App.css';
import { Login } from './Login/Login';
import { Admin } from './Admin/Admin';
import { Operator } from './Operator/Operator';
import { User } from './User/User';

export function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='' element={<Login />}></Route>
        <Route path='admin' element={<Admin />}></Route>
        <Route path='operator' element={<Operator />}></Route>
        <Route path='user' element={<User />}></Route>
      </Routes>
    </BrowserRouter>
  );
}
