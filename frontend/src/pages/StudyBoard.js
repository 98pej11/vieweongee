import React from "react";
import Header from "../Components/common/Header";
import CreateStudy from "../Components/StudyBoard/CreateStudy";

const StudyBoard = () => {
  return (
    <div className="MyPage">
      <Header></Header>
      {/* <ReadStudy></ReadStudy> */}
      <CreateStudy></CreateStudy>
    </div>
  );
};

export default StudyBoard;
