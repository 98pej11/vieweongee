// import Login from "./Components/Login";
// import LoginPage from "./pages/LoginPage";
import React from "react";
import { BrowserRouter } from "react-router-dom";
import MainPage from "./pages/MainPage";
// import MyPage from "./pages/MyPage";
// import MainPage from "./pages/MainPage";
// import HeaderMain from "./Components/common/HeaderMain";
import "./App.css";
// import LoginPage from "./pages/LoginPage";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <MainPage></MainPage>
        {/* <LoginPage></LoginPage> */}
        {/* <MyPage></MyPage> */}
      </div>
    </BrowserRouter>
  );
}

export default App;
