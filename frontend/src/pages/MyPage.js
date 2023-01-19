import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "../Components/common/Header";
import SideBar from "../Components/MyPage/SideBar";
import UserDelete from "../Components/MyPage/UserDelete";
import UserModify from "../Components/MyPage/UserModify";
import { styled } from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid";
import "./MyPage.css";

const Item = styled(Paper)(({ theme }) => ({
  textAlign: "center",
}));

const MyPage = () => {
  return (
    <div className="MyPage">
      <Header></Header>
      <Grid container className="mypage-content">
        <Grid item xs={2}>
          <SideBar></SideBar>
        </Grid>
        <Grid item xs={8}>
          {/* 좌측 메뉴에 따라 컴포넌트 교체 */}
          {/* <UserDelete></UserDelete> */}
          <UserModify></UserModify>
        </Grid>
      </Grid>
    </div>
  );
};

export default MyPage;
