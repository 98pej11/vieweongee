import { TextField } from "@mui/material";
import React from "react";
import "./Main.css";

const SearchBar = () => {
  return (
    <div className="search-bar">
      <TextField size="sm" className="search-field" />
    </div>
  );
};

export default SearchBar;
