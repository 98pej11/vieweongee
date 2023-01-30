<template>
  <MyHeader></MyHeader>
  <router-view />
  <MyFooter></MyFooter>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import MyHeader from "./components/common/MyHeader.vue";
import MyFooter from "./components/common/MyFooter.vue";
export default defineComponent({
  name: "App",
  components: {
    MyHeader,
    MyFooter,
  },
});
</script>

<style>
@font-face {
  font-family: "nexon";
  src: url("@/assets/font/NEXONLv1GothicRegular.ttf") format("truetype");
  /* font-weight: 400; */
}
@font-face {
  font-family: "nexonbold";
  src: url("@/assets/font/NEXONLv1GothicBold.ttf") format("truetype");
  /* font-weight: 400; */
}
@font-face {
  font-family: "nexonlight";
  src: url("@/assets/font/NEXONLv1GothicLight.ttf") format("truetype");
  /* font-weight: 400; */
}
@font-face {
  font-family: "tenada";
  src: url("@/assets/font/Tenada.ttf") format("truetype");
  /* font-weight: 400; */
}
* {
  font-family: "nexon";
}
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
