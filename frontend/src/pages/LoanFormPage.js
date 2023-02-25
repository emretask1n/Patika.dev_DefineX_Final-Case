import React, { useState } from "react";
import axios from "axios";
import Navbar from '../components/Navbar'

const LoanFormPage = () => {
const [customer, setCustomer] = useState({
    idNumber: "",
    nameSurname: "",
    monthlyIncome: 0,
    phoneNumber: "",
    birthDate: "",
    deposit: 0
  });

  const onChange = (event) => {
    setCustomer((prevState) => ({
      ...prevState,
      [event.target.name]: event.target.value,
    }));
  };

  const onSubmit = async (event) => {
    event.preventDefault();
  
    const token = localStorage.getItem("token");

    try {
      const response = await axios.post("/api/v1/forms/apply", customer,
      {
        headers: { Authorization: `Bearer ${token}` } });
      const result = response.data.result;
      const limit = response.data.limit;
      alert(`Application ${result} with limit ${limit}`);
    } catch (error) {
        alert(error.response.data.message);
    }
  };

  return (
    <div>
        <Navbar />
        <div className="container">
            <form className="mt-5">
            <div className="form-group">
                <h1 className="text-center mb-3">Loan Form</h1>
                <label htmlFor="idNumber">ID Number</label>
                <input 
                type="text" 
                className="form-control"
                placeholder="Please enter your 11-digit ID number"
                id="idNumber"
                name="idNumber" // "name" özniteliği eklendi
                value={customer.idNumber} // "value" özniteliği değiştirildi
                onChange={onChange}
                />
            </div>
            <div className="form-group mt-3">
                <label htmlFor="nameSurname">Name Surname</label>
                <input 
                type="text" 
                className="form-control" 
                id="nameSurname"
                name="nameSurname" // "name" özniteliği eklendi
                value={customer.nameSurname} // "value" özniteliği değiştirildi 
                onChange={onChange}
                />
            </div>
            <div className="form-group mt-3">
                <label htmlFor="monthlyIncome">Monthly Income</label>
                <input 
                type="number" 
                className="form-control" 
                id="monthlyIncome"
                name="monthlyIncome" // "name" özniteliği eklendi
                value={customer.monthlyIncome} // "value" özniteliği değiştirildi 
                placeholder="0₺" 
                onChange={onChange}
                />
            </div>
            <div className="form-group mt-3">
                <label htmlFor="phoneNumber">Phone Number</label>
                <input
                type="tel" 
                className="form-control" 
                id="phoneNumber"
                name="phoneNumber" // "name" özniteliği eklendi
                value={customer.phoneNumber} // "value" özniteliği değiştirildi 
                placeholder="0555555555" 
                onChange={onChange}
                />
            </div>
            <div className="form-group mt-3">
                <label htmlFor="birthDate">Birthday</label>
                <input
                type="date" 
                className="form-control" 
                id="birthDate"
                name="birthDate" // "name" özniteliği eklendi
                value={customer.birthDate} // "value" özniteliği değiştirildi 
                placeholder="yyyy-MM-dd" 
                onChange={onChange}
                />
            </div>
            <div className="form-group mt-3">
                <label htmlFor="deposit">Deposit</label>
                <input 
                type="number" 
                className="form-control" 
                id="deposit"
                name="deposit" // "name" özniteliği eklendi
                value={customer.deposit} // "value" özniteliği değiştirildi 
                placeholder="0₺" 
                onChange={onChange}
                />
            </div>
            <div className="text-center mt-3">
                <button type="submit" className="btn btn-primary mt-3" onClick={onSubmit}>Submit</button>
            </div>
            </form>
        </div>  
    </div>
    
    
  );  
}
export default LoanFormPage;
