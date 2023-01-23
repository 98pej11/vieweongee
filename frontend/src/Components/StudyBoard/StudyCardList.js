// import CardItem from "./CardItem";
import * as React from "react";
import StudyCardItem from "./StudyCardItem";
import "./StudyBoard.css";

const StudyCardList = ({CardList }) => {
  return (
    <div>
      <div
      className="studycard-list"
      >
        {CardList.map((it) => (
          <StudyCardItem key={it.id} {...it}></StudyCardItem>
        ))}
      </div>
    </div>
  );
};

export default StudyCardList;
