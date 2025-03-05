import axios from "axios"
import { useState } from "react"
import { Button, Container, Form } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import { store } from "../GlobalData/store"

export const Register:React.FC = () => {
    const navigate = useNavigate()
    const [errorMsg, setErrorMsg] = useState('')
    const[registerCreds, setRegisterCreds] = useState({
            username:"",
            password:"",
            password2:"",
            fName:"",
            lName:""
    })
    const storeValues = (event:React.ChangeEvent<HTMLInputElement>) => {

        const name = event.target.name
        const value = event.target.value 

        setRegisterCreds((registerCreds) => ({...registerCreds, [name]:value}))

    }
    const register = async () => {
        if (!registerCreds.username || !registerCreds.password || !registerCreds.password2 || !registerCreds.fName || !registerCreds.lName) {
            setErrorMsg('Please fill in all input fields')
        } else if (registerCreds.password != registerCreds.password2) {
            setErrorMsg('Passwords do not match')
        } else if (registerCreds.password.length < 8) {
            setErrorMsg('Password needs to be 8 characters or more in length')
        } else {
            try {
                const response = await axios.post("http://localhost:8080/auth/register", {
            username: registerCreds.username,
            password: registerCreds.password,
            password2: registerCreds.password2,
            fName: registerCreds.fName,
            lName: registerCreds.lName
        }, {withCredentials:true})
                store.loggedInUser = response.data
                navigate("/employee")
            } catch {
                setErrorMsg('Registry failed, Username already in use')
            }
            
        }

    }
    return (
        <Container>
            <h1 className="mb-5">Welcome</h1>
                <h3>Please Register a New User:</h3>
                
                <div>
                    <Form.Control
                        type="text"
                        placeholder="username"
                        name="username"
                        onChange={storeValues}
                    />
                </div>

                <div>
                    <Form.Control
                        type="password"
                        placeholder="password"
                        name="password"
                        onChange={storeValues}
                    />
                </div>
                <h6>Password must be 8 characters long</h6>
                <div>
                    <Form.Control
                        type="password"
                        placeholder="repeat the password"
                        name="password2"
                        onChange={storeValues}
                    />
                </div>
                <div>
                    <Form.Control
                        type="text"
                        placeholder="first name"
                        name="fName"
                        onChange={storeValues}
                    />
                </div>
                <div>
                    <Form.Control
                        type="text"
                        placeholder="last name"
                        name="lName"
                        onChange={storeValues}
                    />
                </div>
                <h4 color="red">{errorMsg}</h4>
            <Button variant="outline-success m-1" onClick={register}>Register</Button>
            <Button variant="outline-dark" onClick={()=>navigate("/")}>Return to Login</Button>
        </Container>
    )

}