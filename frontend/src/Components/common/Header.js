import "./Common.css";
import logo from "../../assets/icons/logo.png";

import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import PersonPinIcon from "@mui/icons-material/PersonPin";
// import Menu from "@mui/material/Menu";
import { Link } from "react-router-dom";
// import React,{MouseEvent} from "react";
import * as React from "react";
import Menu from "@mui/material/Menu";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Avatar from "@mui/material/Avatar";
import Tooltip from "@mui/material/Tooltip";
import MenuItem from "@mui/material/MenuItem";
import Box from "@mui/material/Box";

const settings = ["Profile", "Account", "Dashboard", "Logout"];

const Header = () => {
  const { anchorElUser, setAnchorElUser } =
    (React.useState < null) | (HTMLElement > null);
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };
  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

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
        {/* <a>
          <Link to="/rstudy">
            <MenuIcon fontSize="large"></MenuIcon>
          </Link>
        </a> */}

        <Box sx={{ flexGrow: 0 }}>
          <Tooltip title="Open settings">
            <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
              <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
            </IconButton>
          </Tooltip>
          <Menu
            sx={{ mt: "45px" }}
            id="menu-appbar"
            anchorEl={anchorElUser}
            anchorOrigin={{
              vertical: "top",
              horizontal: "right",
            }}
            keepMounted
            transformOrigin={{
              vertical: "top",
              horizontal: "right",
            }}
            open={Boolean(anchorElUser)}
            onClose={handleCloseUserMenu}
          >
            {settings.map((setting) => (
              <MenuItem key={setting} onClick={handleCloseUserMenu}>
                <Typography textAlign="center">{setting}</Typography>
              </MenuItem>
            ))}
          </Menu>
        </Box>
      </nav>
    </div>
  );
};

export default Header;
