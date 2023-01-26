// import CardItem from "./CardItem";
import * as React from "react";
import MainCardItem from "./MainCardItem";
import "./MainCard.css";

const MainCardList = ({ CardList }) => {
  return (
    <div>
      <div
      className="card-list"
      >
        {CardList.map((it) => (
          <MainCardItem key={it.id} {...it}></MainCardItem>
        ))}
      </div>
    </div>
  );
};

export default MainCardList;
