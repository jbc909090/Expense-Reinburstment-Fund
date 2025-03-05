import './App.css'
import 'bootstrap/dist/css/bootstrap.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Login } from './Components/LoginComponents/Login'
import { Register } from './Components/LoginComponents/Register'
import { Employee } from './Components/Roles/Employee'

function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path = "" element = {<Login/>}/>
          <Route path = "register" element = {<Register/>}/>
          <Route path = "employee" element = {<Employee/>}/>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
