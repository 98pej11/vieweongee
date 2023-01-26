import Header from "../Components/common/Header";
import SearchBar from "../Components/common/SearchBar";
import ReadStudyListCard from "../Components/StudyBoard/ReadStudyListCard";
import AddBoxIcon from '@mui/icons-material/AddBox';
import { Link } from "react-router-dom";
import React from "react";

const RStudyListCardPage = () => {
  return (
    <div className="rstudylistcardpage">
      <Header></Header>
      <div style={{marginTop: "30px"}}>
      <SearchBar />
      </div>
      <div style={{float: "right", marginRight: "50px"}}>
        <Link to="/cstudy">
        <AddBoxIcon fontSize="large"/>
        </Link>
        </div>
      <div style={{marginTop: "30px"}}></div>
      
      <div style={{marginTop: "30px"}}></div>
      <ReadStudyListCard></ReadStudyListCard>
    </div>
  );
};

export default RStudyListCardPage;
