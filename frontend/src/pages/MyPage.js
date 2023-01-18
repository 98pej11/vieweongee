import React from "react";
import Header from "../Components/common/Header";
import SideBar from "../Components/MyPage/SideBar";
import "./MyPage.css";

const MyPage = () => {
  return (
    <div className="MyPage">
      <Header></Header>
      <SideBar></SideBar>
    </div>
  );
};

export default MyPage;
