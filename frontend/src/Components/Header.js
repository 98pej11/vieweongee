import './Header.css';
import logo from './logo.png';
import SearchIcon from '@mui/icons-material/Search';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';
import PersonPinIcon from '@mui/icons-material/PersonPin';
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from '@mui/material';
import React from 'react'

const Header = () => {
    return(
        <div className="Header">
            <img src={logo} alt="logo" className="Header-logo"/>
            <div>
                {/* <a><SearchIcon fontSize="large"></SearchIcon></a> */}
                <a><NotificationsActiveIcon fontSize="large"></NotificationsActiveIcon></a>
                <a><Link to='./Components/Login.js'><PersonPinIcon fontSize="large"></PersonPinIcon></Link></a>
                <a><MenuIcon fontSize="large"></MenuIcon></a>
            </div>
        </div>
    )
}

export default Header;