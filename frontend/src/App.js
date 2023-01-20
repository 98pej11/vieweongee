import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import MyPage from "./pages/MyPage";
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
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/cstudy" element={<CStudyPage />} />
        <Route path="/rstudydetail" element={<RStudyDetailPage />} />
        <Route path="/rstudylistcard" element={<RStudyListCardPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
