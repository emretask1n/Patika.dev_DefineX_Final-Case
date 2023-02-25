import React from "react";
import Navbar from "../components/Navbar";

const HomePage = () => {

    return (
        <>
            <Navbar />
            <div className="container my-5">
            <h1 className="text-center mb-4">PATİKA.BANK</h1>
            <p>Welcome to Patika.bank!</p>
        
            <p>In order to apply for a loan, you must log in to the system. You can register to our system from the Signup section.</p>
        
            <p>After logging in, you can update your current information or delete your account.</p>
        
            <p>You can use the Find out application results section to learn the results of the loan applications you have applied before, without the need to login! Note that you will need the ID number and date of birth information you provided during the application process to learn about the results.</p>
        
            <p>Thank you for choosing us!</p>
            
            </div>
        </>
      ); 


}

export default HomePage;