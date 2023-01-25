import "./App.css";
import Header from "./Components/Header";
// import Login from "./Components/Login";
import MainCenter from "./Components/MainCenter";
import MainBottom from "./Components/MainBottom";
import SearchBar from "./Components/SearchBar";
import React from "react";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        {/* <Header />
        {/* <img src={logo} className="App-logo" alt="logo" /> */}
        {/* <Box
          sx={{
            width: 300,
            height: 300,
            backgroundColor: "primary.dark",
            "&:hover": {
              backgroundColor: "primary.main",
              opacity: [0.9, 0.8, 0.7],
            },
          }}
        />
        {/* <Login /> */}
        <MainCenter></MainCenter>
        <SearchBar></SearchBar>
        <MainBottom></MainBottom>
      </header>
    </div>
  );
}

export default App;
