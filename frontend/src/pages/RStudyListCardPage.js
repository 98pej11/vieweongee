import Header from "../Components/common/Header";
import SearchBar from "../Components/common/SearchBar";
import ReadStudyListCard from "../Components/StudyBoard/ReadStudyListCard";
import React from "react";

const RStudyListCardPage = () => {
  return (
    <div>
      <Header></Header>
      <div>
        <SearchBar></SearchBar>
        <ReadStudyListCard></ReadStudyListCard>
      </div>
    </div>
  );
};

export default RStudyListCardPage;
