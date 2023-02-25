import './App.css';
import {Route,Routes} from "react-router-dom"
import LoanFormPage from './pages/LoanFormPage';
import UserSignupPage from './pages/UserSignupPage';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage'
import UpdateInformationPage from './pages/UpdateInformationPage';
import ResultRequestPage from './pages/ResultRequestPage';

function App() {
  return (
    <Routes>
      <Route path='/home' element={<HomePage />} />
      <Route path='/' element={<HomePage />} />
      <Route path='/form' element={<LoanFormPage />} />
      <Route path='/signup' element={<UserSignupPage />} />
      <Route path='/update' element={<UpdateInformationPage />} />
      <Route path='/result' element={<ResultRequestPage />} />
      <Route path='/login' element={<LoginPage />} />
    </Routes>
  );
}

export default App;
