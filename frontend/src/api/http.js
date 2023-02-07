import axios from "axios";

// local vue api axios instance
export default axios.create ({
    baseURL: "http://localhost:8080/api",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });