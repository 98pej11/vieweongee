// import CardItem from "./CardItem";
import * as React from "react";
import CardItem from "./CardItem";
import "./Card.css";

const CardList = ({ CardList }) => {
  return (
    <div>
      <div
      className="card-list"
      style={{
        // display: "grid",
        gridTemplateRows: "1fr ",
        gridTemplateColumns: "1fr 1fr 1fr"
      }}
      >
        {CardList.map((it) => (
          <CardItem key={it.id} {...it}></CardItem>
        ))}
      </div>
    </div>
  );
};

export default CardList;
