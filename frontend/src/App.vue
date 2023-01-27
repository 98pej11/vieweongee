<template>
  <router-view />
</template>

<style>
a {
  text-decoration: none;
  color: #3f67d3;
}

/* div 정가운데 배치 */
.div-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.content-center {
  text-align: center;
}

/* 아웃라인 box */
.outline-box {
  border: 3px solid #d3daff;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
  border-radius: 35px;
}

/* 연보라색 배경 box */
.main-box {
  background-color: #eeecf8;
  border-radius: 5%;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
}

.main-font {
  font-family: "nexonbold";
}

/* 그림자 버튼 */
button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}

/* 입력 폼 */
form {
  width: 80%;
  margin: 0 auto;
}
</style>
