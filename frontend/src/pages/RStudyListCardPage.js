import Header from "../Components/common/Header";
import SearchBar from "../Components/common/SearchBar";
import ReadStudyListCard from "../Components/StudyBoard/ReadStudyListCard";
import React from "react";

const RStudyListCardPage = () => {
  return (
    <div style={{margin: "30px"}}>
      <Header></Header>
        <SearchBar></SearchBar>
        <ReadStudyListCard></ReadStudyListCard>
    </div>
  );
};

export default RStudyListCardPage;
