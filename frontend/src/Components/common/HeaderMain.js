import NotificationsNone from "@mui/icons-material/NotificationsNone";
import PersonOutline from "@mui/icons-material/PersonOutline";
import MenuIcon from "@mui/icons-material/Menu";
import LibraryBooks from '@mui/icons-material/LibraryBooks';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { Link, Route, userNavigate } from "react-router-dom";
import React from "react";

const HeaderMain = () => {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [znchorEl, setZnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const zpen = Boolean(znchorEl);
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
    setZnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
    setZnchorEl(null);
  };

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
          <Link>
          <MenuIcon 
            fontSize="large"
            onClick={handleClick}
            aria-controls={open ? 'board-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={open ? 'true' : undefined}
            >
            </MenuIcon>
            </Link>
        </a>
      </nav>

      <Menu
        anchorEl={anchorEl}
        id="board-menu"
        open={open}
        onClose={handleClose}
        onClick={handleClose}
        PaperProps={{
          elevation: 0,
          sx: {
            overflow: 'visible',
            filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
            mt: 1.5,
            '& .MuiAvatar-root': {
              width: 32,
              height: 32,
              ml: -0.5,
              mr: 1,
            },
            '&:before': {
              content: '""',
              display: 'block',
              position: 'absolute',
              top: 0,
              right: 14,
              width: 10,
              height: 10,
              bgcolor: 'background.paper',
              transform: 'translateY(-50%) rotate(45deg)',
              zIndex: 0,
            },
          },
        }}
        transformOrigin={{ horizontal: 'right', vertical: 'top' }}
        anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
      >
        <Link to={"/rstudylistcard"}>
        <MenuItem>
          <LibraryBooks /> 스터디 모집게시판
        </MenuItem>
        </Link>

        <Link>
        <MenuItem>
          <LibraryBooks /> 공지사항
        </MenuItem>
        </Link>

        <Link to={"/mypagemodify"}>
        <MenuItem>
          <LibraryBooks /> 마이페이지
        </MenuItem>
        </Link>
      </Menu>
    </div>
  );
};

export default HeaderMain;
