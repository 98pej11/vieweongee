import { TextField } from "@mui/material";
import { InputAdornment } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import React from "react";
import "./Common.css";

const SearchBar = () => {
  return (
    <div className="search-bar">
      <TextField
        className="search-field"
        size="sm"
        InputProps={{
          endAdornment: (
            <InputAdornment position="end">
              <SearchIcon />
            </InputAdornment>
          ),
        }}
      />
    </div>
  );
};

export default SearchBar;
