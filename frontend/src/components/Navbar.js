import React from 'react';
import { Navbar, Nav, NavLink, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import axios from 'axios';

function Navigation() {
  const idNumber = localStorage.getItem("idNumber");

  function onClickLogout() {
    if (window.confirm("Are you sure you want to log out?")) {
      localStorage.clear();
      window.location.href = "/home";
    }
  }

  function onClickDelete() {
    if (window.confirm("Are you sure you want to delete your account?")) {
      const userId = localStorage.getItem("userId");
      const token = localStorage.getItem("token");
  
      axios
        .delete(`/api/v1/users/${userId}`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((response) => {
          localStorage.clear();
          window.location.href = "/home";
        })
        .catch((error) => {
          console.log(error);
          // handle error
        });
    }
  }

  return (
    <Navbar collapseOnSelect expand="lg" bg="primary" variant="dark" className="align-items-center">
      <Navbar.Brand>
        <Link to="/" style={{ color: 'white', textDecoration: 'none', fontWeight: 'bold' }}>
          💸Patika.bank🚀
        </Link>
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="responsive-navbar-nav" />
      <Navbar.Collapse id="responsive-navbar-nav">
        <Nav className="me-auto">
          {idNumber ? (
            <>
              <Nav.Item>
                <NavLink as={Link} to="/form" activeClassName="navlink-active" style={{ color: 'white' }}>
                  Apply for loan!💰
                </NavLink>
              </Nav.Item>
              <Nav.Item>
                <NavLink as={Link} to="/update" activeClassName="navlink-active" style={{ color: 'white' }}>
                  Update your information!🔄
                </NavLink>
              </Nav.Item>
            </>
          ) : (
            <>
              <Nav.Item>
                <NavLink as={Link} to="/login" activeClassName="navlink-active" style={{ color: 'white' }}>
                  Login!🔒
                </NavLink>
              </Nav.Item>
              <Nav.Item>
                <NavLink as={Link} to="/signup" activeClassName="navlink-active" style={{ color: 'white' }}>
                  Signup!🔏
                </NavLink>
              </Nav.Item>
            </>
          )}
          <Nav.Item>
            <NavLink as={Link} to="/result" activeClassName="navlink-active" style={{ color: 'white' }}>
              Find out application result!🔍
            </NavLink>
          </Nav.Item>
        </Nav>
        {idNumber && (
          <Nav className="ms-auto">
            <Nav.Item>
              <Navbar.Text className="me-1" style={{ color: 'white' }}>Signed in as: {idNumber}</Navbar.Text>
            </Nav.Item>
            <Nav.Item>
              <Button variant="secondary" className="me-1" onClick={onClickLogout}>
                Logout
              </Button>
            </Nav.Item>
            <Nav.Item>
              <Button variant="danger" className="me-1" onClick={onClickDelete}>
                Delete Account
              </Button>
            </Nav.Item>
          </Nav>
        )}
      </Navbar.Collapse>
    </Navbar>
  );
}

export default Navigation;
