import React from 'react';

const LoanFormTable = ({ loanForms }) => (
  <table className="table">
    <thead>
      <tr>
        <th>ID Number</th>
        <th>Birthdate</th>
        <th>Result</th>
        <th>Limit</th>
      </tr>
    </thead>
    <tbody>
      {loanForms && loanForms.map(loanForm => ( // Added null check for loanForms
        <tr key={loanForm.id}>
          <td>{loanForm.idNumber}</td>
          <td>{loanForm.birthDate}</td>
          <td>{loanForm.loanResult}</td>
          <td>{loanForm.loanLimit}</td>
        </tr>
      ))}
    </tbody>
  </table>
);

export default LoanFormTable;
