import "./Common.css";
import logo from "../../assets/icons/logo.png";

import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import PersonPinIcon from "@mui/icons-material/PersonPin";
// import Menu from "@mui/material/Menu";
import { Link } from "react-router-dom";
// import React,{MouseEvent} from "react";
import * as React from "react";
import MenuIcon from "@mui/icons-material/Menu";

const Header = () => {

  return (
    <div className="Header">
      <Link to={"/"}>
        <img src={logo} alt="logo" className="Header-logo" />
      </Link>
      <nav>
        <a>
          <NotificationsActiveIcon fontSize="large"></NotificationsActiveIcon>
        </a>

        <Link to="/login">
          <PersonPinIcon fontSize="large"></PersonPinIcon>
        </Link>
        
        <a>
          <Link to="/cstudy">
            <MenuIcon fontSize="large"></MenuIcon>
          </Link>
        </a>
      </nav>
    </div>
  );
};

export default Header;
