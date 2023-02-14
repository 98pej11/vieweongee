import axios from "axios";

// local vue api axios instance
export default axios.create({
  // baseURL: "https://vieweongee.kro.kr:8080/api",
  baseURL: "http://localhost:8080/api",
  // baseURL: "https://jsonplaceholder.typicode.com",
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});
