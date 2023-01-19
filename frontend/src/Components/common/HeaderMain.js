import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import PersonPinIcon from "@mui/icons-material/PersonPin";
import MenuIcon from "@mui/icons-material/Menu";
import { Link, Route, userNavigate } from "react-router-dom";
import React from "react";

const HeaderMain = () => {
  return (
    <div className="HeaderMain">
      <nav>
        <a>
          <NotificationsActiveIcon fontSize="large"></NotificationsActiveIcon>
        </a>

        <Link to="/login">
          <PersonPinIcon fontSize="large"></PersonPinIcon>
        </Link>
        <a>
          <Link to="/rstudy">
            <MenuIcon fontSize="large"></MenuIcon>
          </Link>
        </a>
      </nav>
    </div>
  );
};

export default HeaderMain;
