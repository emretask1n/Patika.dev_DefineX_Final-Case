import React from "react";
import axios from "axios";
import Navbar from "../components/Navbar";


class UserSignupPage extends React.Component {
  state = {
    idNumber: null,
    nameSurname: null,
    password: null,
    repeatPassword: null,
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
    const { idNumber, nameSurname, password, repeatPassword } = this.state;
    let errorMessage = {};

    if (!idNumber || idNumber.length !== 11) {
      errorMessage.idNumber = "ID number must be 11 digits long.";
    }
    
    if (!nameSurname) {
      errorMessage.nameSurname = "Name Surname cannot be empty.";
    }
    
    if (password !== repeatPassword) {
      errorMessage.password = "Password and Repeat Password must match.";
    }
    
    if (Object.keys(errorMessage).length > 0) {
      this.setState({ errorMessage });
      return false;
    }
    
    this.setState({ errorMessage: null });
    return true;
  };

  handleSignUpSuccess = () => {
    localStorage.setItem("token", this.token);
    localStorage.setItem("idNumber", this.idNum);
    localStorage.setItem("userId", this.userId);
    // Pop-up mesaj gösterimi
    alert("Registration is successful!");
    window.location.href = "/home";
  };
  

  onClickSignUp = async (event) => {
    event.preventDefault();
  
    // Hata mesajlarını sıfırlama işlemi
    this.setState({ errorMessage: null });
  
    if (!this.validateForm()) {
      return;
    }
  
    this.setState({ pendingApiCall: true });
    const { idNumber, nameSurname, password } = this.state;
    const body = {
      idNumber,
      nameSurname,
      password
    };
  
    try {
      const response = await axios.post("/api/v1/auth/register", body);
      this.token = response.data.token;
      this.idNum = response.data.idNumber;
      this.userId = response.data.userId;
      this.handleSignUpSuccess(); // handleLoginSuccess yerine handleSignUpSuccess çağrılıyor
    } catch (error) {
      if (error.response && error.response.status === 403) {
        this.setState({
          pendingApiCall: false,
          errorMessage: {
            type: "error",
            message: "ID number is already in use.",
          },
        });
      } else {
        this.setState({
          pendingApiCall: false,
          errorMessage: {
            type: "error",
            message: "An error occurred while registering in.",
          },
        });
      }
    }
  };
  


  render() {
    const { pendingApiCall, errorMessage } = this.state;
    return (
      <>
        <Navbar />
        <div>
          <div className="container">
            <form className="mt-5">
              <h1 className="text-center">Sign Up</h1>
              <div className="form-group mt-3">
                <label>ID Number</label>
                <input 
                  className={`form-control ${errorMessage && errorMessage.idNumber ? "is-invalid" : ""}`}
                  name="idNumber" 
                  onChange={this.onChange}
                  required
                  minLength={11}
                  maxLength={11}
                />
                {errorMessage && errorMessage.idNumber && <div className="invalid-feedback">{errorMessage.idNumber}</div>}
              </div>
              <div className="form-group mt-3">
                <label>Name Surname</label>
                <input 
                  className={`form-control ${errorMessage && errorMessage.nameSurname ? "is-invalid" : ""}`} 
                  name="nameSurname" 
                  onChange={this.onChange}
                  required
                />
                {errorMessage && errorMessage.nameSurname && <div className="invalid-feedback">{errorMessage.nameSurname}</div>}
              </div>
              <div className="form-group mt-3">
                <label>Password</label>
                <input 
                  className={`form-control ${errorMessage && errorMessage.password ? "is-invalid" : ""}`} 
                  name="password" 
                  type="password" 
                  onChange={this.onChange}
                  required
                />
              </div>
              <div className="form-group mt-3">
                <label>Repeat Password</label>
                <input 
                  className={`form-control ${errorMessage && errorMessage.password ? "is-invalid" : ""}`} 
                  name="repeatPassword" 
                  type="password" 
                  onChange={this.onChange}
                  required
                />
                {errorMessage && errorMessage.password && <div className="invalid-feedback">{errorMessage.password}</div>}
              </div>
              {errorMessage && errorMessage.type === "error" && 
                <div className="alert alert-danger mt-3">{errorMessage.message}</div>
              }
              <div className="text-center">
                <button 
                  className="btn btn-primary mt-4" 
                  onClick={this.onClickSignUp} 
                  disabled={pendingApiCall}
                >
                  {pendingApiCall && 
                    <span className="spinner-border spinner-border-sm"></span>
                  }
                  Sign Up
                </button>
              </div>
            </form>
          </div>
        </div>
      </>
    );
  }
}
export default UserSignupPage;
