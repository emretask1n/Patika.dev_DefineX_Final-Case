import React from "react";
import axios from "axios";
import Navbar from '../components/Navbar'
import Alert from 'react-bootstrap/Alert';

class LoginPage extends React.Component {
    state = {
      idNumber: null,
      password: null,
      pendingApiCall: false,
      errorMessage: null,
    };
  
    onChange = (event) => {
      this.setState({
        [event.target.name]: event.target.value,
        errorMessage: null,
      });
    };
  
    validateForm = () => {
      const { idNumber, password } = this.state;
      let errorMessage = {};
  
      if (!idNumber || idNumber.length !== 11) {
        errorMessage.idNumber = "ID number must be 11 digits long.";
      }
  
      if (!password) {
        errorMessage.password = "Password cannot be empty.";
      }
  
      if (Object.keys(errorMessage).length > 0) {
        this.setState({ errorMessage });
        return false;
      }
  
      this.setState({ errorMessage: null });
      return true;
    };
  
    handleLoginSuccess = () => {
      localStorage.setItem("token", this.token);
      localStorage.setItem("idNumber", this.idNum);
      localStorage.setItem("userId", this.userId);
      window.location.href = "/home";
    };
  
    onLogin = async (event) => {
      event.preventDefault();

      this.setState({ errorMessage: null });

      if (!this.validateForm()) {
        return;
      }
  
      const { idNumber, password } = this.state;
      this.setState({ pendingApiCall: true, errorMessage: null });
  
      try {
        const response = await axios.post("/api/v1/auth/login", {
          idNumber,
          password,
        });
        this.token = response.data.token;
        this.idNum = response.data.idNumber;
        this.userId = response.data.userId;
        this.handleLoginSuccess();
      } catch (error) {
        if (error.response && error.response.status === 403) {
          this.setState({
            pendingApiCall: false,
            errorMessage: {
              type: "error",
              message: "Username or password is incorrect.",
            },
          });
        } else {
          this.setState({
            pendingApiCall: false,
            errorMessage: {
              type: "error",
              message: "An error occurred while logging in.",
            },
          });
        }
      }
    };
    

    render() {
        const { idNumber, password, pendingApiCall, errorMessage } = this.state;
        return (
          <div>
            <Navbar />
            <div className="container">
              <form className="mt-5" onSubmit={this.onSubmit}>
                <div className="form-group">
                  <h1 className="text-center mb-3">Login</h1>
                  <label htmlFor="idNumber">ID Number</label>
                  <input 
                    type="text" 
                    className="form-control"
                    placeholder="Please enter your 11-digit ID number"
                    id="idNumber"
                    name="idNumber"
                    value={idNumber}
                    onChange={this.onChange}
                  />
                  {errorMessage && errorMessage.idNumber &&
                    <Alert variant="danger" className="mt-2">{errorMessage.idNumber}</Alert>
                  }
                </div>
                <div className="form-group mt-3">
                  <label htmlFor="password">Password</label>
                  <input 
                    type="password" 
                    className="form-control" 
                    id="password"
                    name="password"
                    value={password}
                    onChange={this.onChange}
                  />
                  {errorMessage && errorMessage.password &&
                    <Alert variant="danger" className="mt-2">{errorMessage.password}</Alert>
                  }
                </div>
                <div className="text-center mt-3">
                    <button
                      type="submit"
                      className="btn btn-primary mt-4"
                      disabled={pendingApiCall}
                      onClick={this.onLogin}
                    >
                      {pendingApiCall ? "Logging in..." : "Login"}
                    </button>
                    {errorMessage && errorMessage.message &&
                      <Alert variant={errorMessage.type} className="text-center mt-3">{errorMessage.message}</Alert>
                    }
                  </div>
                  </form>
                </div>
              </div>
            );
        }
}    
export default LoginPage;