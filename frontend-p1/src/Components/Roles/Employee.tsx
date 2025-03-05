import { Button, Container, Form, Table } from "react-bootstrap"
import { store } from "../GlobalData/store"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import { useState } from "react"
import { User } from "../Interfaces/User"
import { Invoice } from "../Interfaces/Invoice"

export const Employee:React.FC = () => {
    const navigate = useNavigate()
    //does everything an employee and manager can do
    //manager actions have a check to run and to display?
    //done via hidden buttons
    const [users, setUsers] = useState<User[]>([])
    const [invoices, setInvoices] = useState<Invoice[]>([])
    const [errorMsg, setErrorMsg] = useState('')
    const [displayMsg, setDisplayMsg] = useState('')
    const [fields, setFields] = useState({
        id1:0,
        desc:"",
        id2:0,
        status:"Pending",
        desc2:"",
        amount:0,
        user_id:0
    })
    //we need to
    //logout function
    const logout = async () => {
        try {
            const response = await axios.post("http://localhost:8080/auth/logout", "", {withCredentials:true})
            store.loggedInUser = response.data
            navigate("/")
        } catch {
            //maybe something here
        }
    }
    const storeValues = (event:React.ChangeEvent<HTMLInputElement>) => {

        const name = event.target.name
        const value = event.target.value
        setFields((fields) => ({...fields, [name]:value}))

    }
    const storeValue = (event:React.ChangeEvent<HTMLSelectElement>) => {

        const name = event.target.name
        const value = event.target.value
        setFields((fields) => ({...fields, [name]:value}))

    }
    //retrieve all your reinburstments
    const allUserInvoice = async () => {
        try {
            const response = await axios.get("http://localhost:8080/user/employee/all", {withCredentials:true})
            setInvoices(response.data)
        } catch {
            setErrorMsg("something went wrong all version")
        }
    }
    //make new reinburstment
    const newInvoice = async () => {
        try {
            const response = await axios.post("http://localhost:8080/user/employee/add", {
            amount: fields.amount,
            status: "Pending",
            description: fields.desc2,
            userId: store.loggedInUser.userId
        }, {withCredentials:true})
            setDisplayMsg("New Pending Invoice added with id " + response.data.invoiceId)
        } catch {
            setErrorMsg("only admins can see all invoices")
        }
    }
    //see only your pending reinburstments
    const allUserPending = async () => {
        try {
            const response = await axios.get("http://localhost:8080/user/employee/pending", {withCredentials:true})
            setInvoices(response.data)
        } catch {
            setErrorMsg("something went wrong pending version")
        }
    }
    //update your own reinburstment
    const updateDescription = async () => {
        try {
            const response = await axios.patch("http://localhost:8080/user/employee/desc", {
            description:fields.desc,
            id:fields.id1
        }, {withCredentials:true})
            setDisplayMsg("Invoice " + response.data.invoiceId + " has been updated")
        } catch {
            setErrorMsg("That is not your invoice, you cannot edit it")
        }
    }
    //MANAGER ACIONS
    //see all reinburstments
    const allInvoice = async () => {
        try {
            const response = await axios.get("http://localhost:8080/user/admin/all", {withCredentials:true})
            setInvoices(response.data)
        } catch {
            setErrorMsg("only admins can see all invoices")
        }
    }
    //see all pending reinburstments
    const allPending = async () => {
        try {
            const response = await axios.get("http://localhost:8080/user/admin/pending", {withCredentials:true})
            setInvoices(response.data)
        } catch {
            setErrorMsg("Only admins can see all pending invoices")
        }
    }
    //see all users
    const allUsers = async () => {
        try {
            const response = await axios.get("http://localhost:8080/user/admin/users", {withCredentials:true})
            setUsers(response.data)
        } catch {
            setErrorMsg("You are not an Admin, you cannot view employee data")
        }
    }
    //delete a user
    const delUser = async () => {
        try {
            const response = await axios.delete("http://localhost:8080/user/admin/user/" + fields.user_id, {withCredentials:true})
            setDisplayMsg("User id " + response.data.userId + " is gone")
        } catch {
            setErrorMsg("You are not an Admin, you cannot delete employee data")
        }
    }
    //edit a reinburstment's status
    const updateStatus = async () => {
        try {
            const response = await axios.patch("http://localhost:8080/user/admin/status", {
            description:fields.status,
            id:fields.id2
        }, {withCredentials:true})
            setDisplayMsg("Invoice " + response.data.invoiceId + " has been updated")
        } catch {
            setErrorMsg("You are not an Admin, you cannot edit status")
        }
    }
    return (
        <Container>
            <h1 className="mb-5">You are logged in as:</h1>
                <h3>user id: {store.loggedInUser.userId} and your role is an {store.loggedInUser.role}</h3>
                <h4 color="red">{errorMsg}</h4>
                <h4>{displayMsg}</h4>
                <div></div>
                <Button variant="outline-success m-1" onClick={allUserInvoice}>See All Your Reinburstments</Button>
                <Button variant="outline-success m-1" onClick={allUserPending}>See All Your Pending Reinburstments</Button>
                <Button variant="outline-danger" onClick={allInvoice}>Admin: See All Reinburstments</Button>
                <Button variant="outline-danger" onClick={allPending}>Admin: See All Pending Reinburstments</Button>
                <Button variant="outline-danger" onClick={allUsers}>Admin: See All Users</Button>
                <Form.Control
                        type="text"
                        placeholder="updated description"
                        name="desc"
                        onChange={storeValues}
                    />
                <Form.Control
                        type="number"
                        placeholder="0"
                        name="id1"
                        onChange={storeValues}
                    />
                <Button variant="outline-success" onClick={updateDescription}>Change Description by ID</Button>
                <div></div>
                 <Form.Control
                        type="text"
                        placeholder="new invoice description"
                        name="desc2"
                        onChange={storeValues}
                    />
                <Form.Control
                        type="number"
                        placeholder="0"
                        name="amount"
                        onChange={storeValues}
                    />
                <Button variant="outline-success" onClick={newInvoice}>Make new Invoice with description and amount</Button>
                <div></div>
                <Form.Select name="status" onChange={storeValue}>
                    <option>Pending</option>
                    <option>Approved</option>
                    <option>Denied</option>
                </Form.Select>
        
                    
                <Form.Control
                        type="number"
                        placeholder="0"
                        name="id2"
                        onChange={storeValues}
                    />
                <Button variant="outline-danger" onClick={updateStatus}>Admin: Update Invoice status</Button>
                <div></div>
                <Form.Control
                        type="number"
                        placeholder="0"
                        name="user_id"
                        onChange={storeValues}
                    />
                <Button variant="outline-danger" onClick={delUser}>Admin: Delete user by ID</Button>
                <div></div>
                <h3>Invoices: </h3>
            <Table className="table-dark table-hover table-striped w-50">
                <thead>
                    <tr>
                        <th>Invoice Id</th>
                        <th>User Id</th>
                        <th>Amount</th>
                        <th>Description</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody className="table-secondary">
                    {invoices.map((invoice:Invoice) => (
                        <tr key={invoice.invoiceId}>
                            <td>{invoice.invoiceId}</td>
                            <td>{invoice.user}</td>
                            <td>{invoice.amount}</td>
                            <td>{invoice.description}</td>
                            <td>{invoice.status}</td>
                        </tr>
                    ))} 
                </tbody>
            </Table>
            <div></div>
                <h3>Users: </h3>
            <Table className="table-dark table-hover table-striped w-50">
                <thead>
                    <tr>
                        <th>User Id</th>
                        <th>Role</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                    </tr>
                </thead>
                <tbody className="table-secondary">
                    {users.map((user:User) => (
                        <tr key={user.userId}>
                            <td>{user.userId}</td>
                            <td>{user.role}</td>
                            <td>{user.fName}</td>
                            <td>{user.lName}</td>
                        </tr>
                    ))} 
                </tbody>
            </Table>
            <Button variant="outline-dark" onClick={logout}>Logout</Button>
        </Container>
    )

}