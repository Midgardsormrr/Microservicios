import axios from "axios";

// En Vite, las vars de entorno deben empezar con VITE_
// y se acceden vía import.meta.env.VITE_<NOMBRE>
const API_URL = import.meta.env.VITE_API_URL || "http://172.24.243.207:30801";

const httpClient = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json"
  }
});

export default httpClient;