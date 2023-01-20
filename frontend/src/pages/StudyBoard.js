import React from "react";
import Header from "../Components/common/Header";
import CreateStudy from "../Components/StudyBoard/CreateStudy";
import ReadStudy from "../Components/StudyBoard/ReadStudy";
import Reply from "../Components/StudyBoard/Reply";
const StudyBoard = () => {
  return (
    <div className="MyPage">
      <Header></Header>
      <ReadStudy></ReadStudy>
      <Reply></Reply>
      {/* <CreateStudy></CreateStudy> */}
    </div>
  );
};

export default StudyBoard;
