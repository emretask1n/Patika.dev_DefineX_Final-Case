import React from "react";
import axios from "axios";
import Navbar from '../components/Navbar'


class LoanFormPage extends React.Component {
  state = {
    idNumber: "",
    nameSurname: "",
    monthlyIncome: 0,
    phoneNumber: "",
    birthDate: "",
    deposit: 0,
    errorMessage: null
  };
  
  onChange = event => {
    const { name, value } = event.target;
    this.setState({
      [name]: value
    });
  };
  
  validateForm = () => {
    const { idNumber, nameSurname, monthlyIncome, phoneNumber, birthDate } = this.state;
    let errorMessage = {};

    if (!idNumber || idNumber.length !== 11) {
      errorMessage.idNumber = "ID number must be 11 digits long.";
    }
    
    if (!nameSurname) {
      errorMessage.nameSurname = "Name and surname cannot be empty.";
    }
    
    if (!monthlyIncome || monthlyIncome <= 0) {
      errorMessage.monthlyIncome = "Monthly income must be greater than 0.";
    }
    
    if (!phoneNumber || phoneNumber.length !== 10) {
      errorMessage.phoneNumber = "Phone number must be 10 digits long.";
    }
    
    if (!birthDate) {
      errorMessage.birthDate = "Birthdate cannot be empty.";
    }
    
    if (Object.keys(errorMessage).length > 0) {
      this.setState({ errorMessage });
      return false;
    }
    
    this.setState({ errorMessage: null });
    return true;
  };


  onSubmit = async (event) => {
    event.preventDefault();

    this.setState({ errorMessage: null });
  
    const token = localStorage.getItem("token");

    if (!this.validateForm()) {
      return;
    }

    try {
      const response = await axios.post("/api/v1/forms/apply", this.state,
      {
        headers: { Authorization: `Bearer ${token}` } });
      const result = response.data.result;
      const limit = response.data.limit;
      alert(`Application ${result} with limit ${limit}`);
    } catch (error) {
        this.setState({ errorMessage: error.response.data.message });
    }
  };

  render() {
  const { errorMessage } = this.state;
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
                className={`form-control ${errorMessage && errorMessage.idNumber ? "is-invalid" : ""}`}
                placeholder="Please enter your 11-digit ID number"
                id="idNumber"
                name="idNumber" 
                value={this.state.idNumber} 
                onChange={this.onChange}
                />
                {errorMessage && errorMessage.idNumber && <div className="invalid-feedback">{errorMessage.idNumber}</div>}
            </div>
            <div className="form-group mt-3">
                <label htmlFor="nameSurname">Name Surname</label>
                <input 
                type="text" 
                className={`form-control ${errorMessage && errorMessage.nameSurname ? "is-invalid" : ""}`}
                id="nameSurname"
                name="nameSurname" 
                value={this.state.nameSurname} 
                onChange={this.onChange}
                />
                {errorMessage && errorMessage.nameSurname && <div className="invalid-feedback">{errorMessage.nameSurname}</div>}
            </div>
            <div className="form-group mt-3">
                <label htmlFor="monthlyIncome">Monthly Income</label>
                <input 
                type="number" 
                className={`form-control ${errorMessage && errorMessage.monthlyIncome ? "is-invalid" : ""}`}
                id="monthlyIncome"
                name="monthlyIncome" 
                value={this.state.monthlyIncome} 
                placeholder="0₺" 
                onChange={this.onChange}
                />
                {errorMessage && errorMessage.monthlyIncome && <div className="invalid-feedback">{errorMessage.monthlyIncome}</div>}
            </div>
            <div className="form-group mt-3">
                <label htmlFor="phoneNumber">Phone Number</label>
                <input
                type="tel" 
                className={`form-control ${errorMessage && errorMessage.phoneNumber ? "is-invalid" : ""}`}
                id="phoneNumber"
                name="phoneNumber" 
                value={this.state.phoneNumber} 
                placeholder="0555555555" 
                onChange={this.onChange}
                />
                {errorMessage && errorMessage.phoneNumber && <div className="invalid-feedback">{errorMessage.phoneNumber}</div>}
            </div>
            <div className="form-group mt-3">
                <label htmlFor="birthDate">Birthday</label>
                <input
                type="date" 
                className={`form-control ${errorMessage && errorMessage.birthDate ? "is-invalid" : ""}`}
                id="birthDate"
                name="birthDate" // "name" özniteliği eklendi
                value={this.state.birthDate} // "value" özniteliği değiştirildi 
                placeholder="yyyy-MM-dd" 
                onChange={this.onChange}
                />
                {errorMessage && errorMessage.birthDate && <div className="invalid-feedback">{errorMessage.birthDate}</div>}
            </div>
            <div className="form-group mt-3">
                <label htmlFor="deposit">Deposit</label>
                <input 
                type="number" 
                className={`form-control ${errorMessage && errorMessage.deposit ? "is-invalid" : ""}`}
                id="deposit"
                name="deposit"
                value={this.state.deposit} 
                placeholder="0₺" 
                onChange={this.onChange}
                />
                {errorMessage && errorMessage.deposit && <div className="invalid-feedback">{errorMessage.deposit}</div>}
            </div>
            <div className="text-center mt-3">
                <button type="submit" className="btn btn-primary mt-3" onClick={this.onSubmit}>Submit</button>
            </div>
            </form>
        </div>  
    </div>
  );
}
}  
export default LoanFormPage;
