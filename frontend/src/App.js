import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import MyPage from "./pages/MyPage";
import LoginPage from "./pages/LoginPage";
import StudyBoard from "./pages/StudyBoard";
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
        <Route path="/study" element={<StudyBoard />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
