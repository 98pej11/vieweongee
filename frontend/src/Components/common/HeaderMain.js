import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import PersonPinIcon from "@mui/icons-material/PersonPin";
import MenuIcon from "@mui/icons-material/Menu";
import { Link, Route, userNavigate } from "react-router-dom";
import React from "react";

const HeaderMain = () => {
  return (
    <div className="HeaderMain">
      <div className="header-icon">
        <a>
          <NotificationsActiveIcon fontSize="large"></NotificationsActiveIcon>
        </a>
        <a>
          <Link to={"/mypage"}>
            <PersonPinIcon fontSize="large"></PersonPinIcon>
          </Link>
        </a>
        <a>
          <MenuIcon fontSize="large"></MenuIcon>
        </a>
      </div>
    </div>
  );
};

export default HeaderMain;
