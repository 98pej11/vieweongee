import HeaderMain from "../Components/common/HeaderMain";
import MainCenter from "../Components/MainPage/MainCenter";
import MainBottom from "../Components/MainPage/MainBottom";
import SearchBar from "../Components/common/SearchBar";
import React from "react";

const MainPage = () => {
  return (
    <div>
      <HeaderMain></HeaderMain>
      <div className="MainPage">
        <MainCenter></MainCenter>
        <SearchBar></SearchBar>
        <MainBottom></MainBottom>
      </div>
    </div>
  );
};

export default MainPage;
