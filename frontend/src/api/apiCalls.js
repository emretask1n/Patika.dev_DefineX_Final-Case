import axios from "axios";

export const signup = body => {
    axios.post('/api/v1/customers', body);
};