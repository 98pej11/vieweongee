import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import MyPageModifyPage from "./pages/MyPageModifyPage";
import MyPageDeletePage from "./pages/MyPageDeletePage";
import LoginPage from "./pages/LoginPage";
import CStudyPage from "./pages/CStudyPage";
import RStudyDetailPage from "./pages/RStudyDetailPage";
import RStudyListCardPage from "./pages/RStudyListCardPage";
import SignUpPage from "./pages/SignUpPage";
import "./App.css";
import "./default.css";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/mypagemodify" element={<MyPageModifyPage />} />
        <Route path="/mypagedelete" element={<MyPageDeletePage />} />
        <Route path="/cstudy" element={<CStudyPage />} />
        <Route path="/rstudydetail" element={<RStudyDetailPage />} />
        <Route path="/rstudylistcard" element={<RStudyListCardPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
