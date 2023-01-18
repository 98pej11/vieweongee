import "./Common.css";
import logo from "../../assets/icons/logo.png";
import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import PersonPinIcon from "@mui/icons-material/PersonPin";
import MenuIcon from "@mui/icons-material/Menu";
import { Link } from "@mui/material";
import React from "react";

const Header = () => {
  return (
    <div className="Header">
      <img src={logo} alt="logo" className="Header-logo" />
      <nav>
        <a>
          <NotificationsActiveIcon fontSize="large"></NotificationsActiveIcon>
        </a>
        <a>
          <Link to="../pages/LoginPage">
            <PersonPinIcon fontSize="large"></PersonPinIcon>
          </Link>
        </a>
        <a>
          <MenuIcon fontSize="large"></MenuIcon>
        </a>
        {/* <ul>
                    <li>></li>
                    <li></li>
                    <li></li>
                </ul> */}
      </nav>
    </div>
  );
};

export default Header;
