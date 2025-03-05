import axios from "axios";
import { useEffect, useRef, useState } from "react";
import { Button, Container, Form } from "react-bootstrap"
import { useNavigate } from "react-router-dom";
import { store } from "../GlobalData/store";

export const Login:React.FC = () => {
    //set up navigation
    const navigate = useNavigate()
    const [errorMsg, setErrorMsg] = useState('')
    //focus the user on the username field upon load
    const usernameRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        if (usernameRef.current) {
            usernameRef.current.focus();
        }
    }, []);
    //store the field data
    const[loginCreds, setLoginCreds] = useState({
        username:"",
        password:""
    })
    const storeValues = (event:React.ChangeEvent<HTMLInputElement>) => {

        //As I understand this code
        const name = event.target.name //gets the field name password or username
        const value = event.target.value //gets the content of the field

        //updates either password or username based on 'name' to equal 'value'
        //in other words if you type in the password box update the stored password to match
        setLoginCreds((loginCreds) => ({...loginCreds, [name]:value}))

    }
    const login = async () => {
        if (!loginCreds.password && !loginCreds.username) {
            //run code to say values are empty
        } else {
            try {
                const response = await axios.post("http://localhost:8080/auth/login", loginCreds, {withCredentials:true})
                store.loggedInUser = response.data
                //login successful display handled here
                navigate("/employee")
            } catch {
                setErrorMsg("Login Failed")
            }
        }
    }

    return (
        <Container>
            <h1 className="mb-5">Welcome</h1>
                <h3>Please Log In:</h3>
                
                <div>
                    <Form.Control
                        type="text"
                        placeholder="username"
                        name="username"
                        ref={usernameRef} 
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
                
                <h4 color="red">{errorMsg}</h4>
            <Button variant="outline-success m-1" onClick={login}>Login</Button>
            <Button variant="outline-dark" onClick={()=>navigate("/register")}>Register</Button>
        </Container>
    )

}