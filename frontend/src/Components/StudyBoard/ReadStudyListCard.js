import * as React from "react";
import CardList from "./CardList";

const defaultList = [
  {
    id: 1,
    title: "싸피 비전공자 면접스터디 급구",
    person: "1/6",
    ent: "SSAFY",
    dept: "백엔드",
    date: "2023.01.17 18:00",
  },
  {
    id: 2,
    person: "1/6",
    title: "신한은행 IT 면접 스터디",
    ent: "신한은행",
    dept: "프론트엔드",
    date: "2023.01.17 18:00",
  },
  {
    id: 3,
    person: "1/6",
    title: "ssafy 비전공자 면접스터디!",
    ent: "SSAFY",
    dept: "프론트엔드",
    date: "2023.01.17 18:00",
  },
  {
    id: 4,
    title: "싸피 비전공자 면접스터디 급구",
    person: "1/6",
    ent: "SSAFY",
    dept: "백엔드",
    date: "2023.01.17 18:00",
  },
  {
    id: 5,
    person: "1/6",
    title: "신한은행 IT 면접 스터디",
    ent: "신한은행",
    dept: "프론트엔드",
    date: "2023.01.17 18:00",
  },
  {
    id: 6,
    person: "1/6",
    title: "ssafy 비전공자 면접스터디!",
    ent: "SSAFY",
    dept: "프론트엔드",
    date: "2023.01.17 18:00",
  },
];

export default function ReadStudyListCard() {
  return (
    <div>
      <CardList CardList={defaultList}></CardList>
    </div>
  );
}
