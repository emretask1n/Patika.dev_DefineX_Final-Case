import React,{ Component } from "react";
import Navbar from "../components/Navbar";
import axios from "axios";
import LoanFormTable from "../components/LoanFormTable";


class ResultRequestPage extends Component {
    state = {
        idNumber: null,
        birthDate: null,
        loanForms: null,
        pendingApiCall: false,
        errorMessage: null
      };

    onChange = event => {
        const { name, value } = event.target;
        this.setState({
          [name]: value
        });
    };

    validateForm = () => {
        const { idNumber, birthDate} = this.state;
        let errorMessage = {};
    
        if (!idNumber || idNumber.length !== 11) {
          errorMessage.idNumber = "ID number must be 11 digits long.";
        }

        if (!birthDate) {
            errorMessage.birthDate = "Birthday cannot be empty.";
          }
        
        
        if (Object.keys(errorMessage).length > 0) {
          this.setState({ errorMessage });
          return false;
        }
        
        this.setState({ errorMessage: null });
        return true;
    };

    onClickLogin = event => {
        event.preventDefault();

        this.setState({ errorMessage: null });
    
        if (!this.validateForm()) {
          return;
        }
    
        this.setState({ pendingApiCall: true });

        const { idNumber, birthDate } = this.state;
        const body = {
          idNumber,
          birthDate
        };

        axios.post('/api/v1/forms/results', body)
          .then(response => {
          const loanForms = response.data;
          this.setState({ loanForms });
          this.setState({ pendingApiCall: false });
          // Do something with loanForms, e.g. update state
         })
          .catch(error => {
            if (error.response.status === 403) {
        // Loan form not found, show error message
              alert('Loan form not found, please check your ID number and birthdate.');
              this.setState({ pendingApiCall: false });
            } else {
        // Other error, handle it as appropriate
              alert('Something went wrong!');
              this.setState({ pendingApiCall: false });
           }
       });
    };

    render() {
      const { pendingApiCall, errorMessage, loanForms } = this.state;
      return (
        <>
          <Navbar />
          <div className="container">
            <form className="mt-5">
              <h1 className="text-center">Find out application result!</h1>
              <div className="form-group mt-3">
                <label>ID Number</label>
                <input 
                  className={`form-control ${errorMessage && errorMessage.idNumber ? "is-invalid" : ""}`}
                  name="idNumber" 
                  onChange={this.onChange}
                  required
                  placeholder="The 11- digits ID number you entered while registering to the system."
                  minLength={11}
                  maxLength={11}
                />
                {errorMessage && errorMessage.idNumber && <div className="invalid-feedback">{errorMessage.idNumber}</div>}
              </div>
              <div className="form-group mt-3">
                <label>Birthday</label>
                <input 
                  className={`form-control ${errorMessage && errorMessage.birthDate ? "is-invalid" : ""}`} 
                  name="birthDate" 
                  type="date" 
                  onChange={this.onChange}
                  required
                />
              </div>
              <div className="text-center">
                <button 
                  className="btn btn-primary mt-4" 
                  onClick={this.onClickLogin} 
                  disabled={pendingApiCall}
                >
                  {pendingApiCall && 
                    <span className="spinner-border spinner-border-sm"></span>
                  }
                  Inquiry
                </button>
              </div>
            </form>
          </div>
          <LoanFormTable loanForms={loanForms} />
        </>
      );
    }    
}
export default ResultRequestPage;