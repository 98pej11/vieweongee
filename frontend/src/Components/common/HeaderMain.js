import NotificationsNone from "@mui/icons-material/NotificationsNone";
import PersonOutline from "@mui/icons-material/PersonOutline";
import MenuIcon from "@mui/icons-material/Menu";
import { Link, Route, userNavigate } from "react-router-dom";
import React from "react";

const HeaderMain = () => {
  return (
    <div className="HeaderMain">
      <nav>
        <a>
          <NotificationsNone fontSize="large"></NotificationsNone>
        </a>

        <Link to="/login">
          <PersonOutline fontSize="large"></PersonOutline>
        </Link>
        <a>
          <Link to="/rstudydetail">
            <MenuIcon fontSize="large"></MenuIcon>
          </Link>
        </a>
      </nav>
    </div>
  );
};

export default HeaderMain;
