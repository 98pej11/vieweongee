// import CardItem from "./CardItem";
import * as React from "react";
import CardItem from "./CardItem";

const CardList = ({ CardList }) => {
  return (
    <div>
      <div className="card-list">
        {CardList.map((it) => (
          <CardItem key={it.id} {...it}></CardItem>
        ))}
      </div>
    </div>
  );
};

export default CardList;
