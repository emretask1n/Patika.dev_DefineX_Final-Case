import React, { Component } from "react";
import Navbar from "../components/Navbar";
import axios from "axios";


class UpdateInformationPage extends Component {
  state = {
    nameSurname: "",
    password: "",
    pendingApiCall: false,
    errorMessage: null,
  };

  onChange = (event) => {
    const { name, value } = event.target;
    this.setState({
      [name]: value,
    });
  };

  validateForm = () => {
    const { nameSurname, password } = this.state;
    let errorMessage = {};

    if (!nameSurname) {
      errorMessage.nameSurname = "Name and surname cannot be empty.";
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

  onClickUpdate = (event) => {
    event.preventDefault();

    this.setState({ errorMessage: null });

    if (!this.validateForm()) {
      return;
    }

    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");
    const idNumber = localStorage.getItem("idNumber");
    const { nameSurname, password } = this.state;
    const body = {
      idNumber:idNumber,
      nameSurname,
      password
    };

    this.setState({ pendingApiCall: true });
    axios
      .put(`/api/v1/users/${userId}`, body, {
        headers: { Authorization: `Bearer ${token}` } })
      .then((response) => {
        this.setState({ pendingApiCall: false });
        alert("Congratulations! Your information has been updated.");
        window.location.href = "/home";
      })
      .catch((error) => {
        this.setState({ pendingApiCall: false });
        alert("Something went wrong! Please try again.");
      });
  };

  render() {
    const { pendingApiCall, errorMessage } = this.state;
    return (
      <>
        <Navbar />
        <div className="container">
          <form className="mt-5">
            <h1 className="text-center">Update your information</h1>
            <div className="form-group mt-3">
              <label>New name and surname</label>
              <input
                className={`form-control ${
                  errorMessage && errorMessage.nameSurname ? "is-invalid" : ""
                }`}
                name="nameSurname"
                type="text"
                onChange={this.onChange}
                required
                placeholder="New name and surname"
              />
              {errorMessage && errorMessage.nameSurname && (
                <div className="invalid-feedback">{errorMessage.nameSurname}</div>
              )}
            </div>
            <div className="form-group mt-3">
              <label>New password</label>
              <input
                className={`form-control ${
                  errorMessage && errorMessage.password ? "is-invalid" : ""
                }`}
                name="password"
                type="password"
                placeholder="New password"
                onChange={this.onChange}
                required
              />
              {errorMessage && errorMessage.password && (
                <div className="invalid-feedback">{errorMessage.password}</div>
              )}
            </div>
            <div className="text-center">
              <button
                className="btn btn-primary mt-4"
                onClick={this.onClickUpdate}
                disabled={pendingApiCall}
              >
                {pendingApiCall && (
                  <span className="spinner-border spinner-border-sm"></span>
                )}
                Update
              </button>
            </div>
          </form>
        </div>
      </>
    );
  }
}

export default UpdateInformationPage;
