<template>
  <div class="footer">
    <p class="hr-sect" style="display: flex; justify-content: space-between">
      <img src="@/assets/image/github.png" :width="60" />
      <img src="@/assets/image/youtube.png" :width="60" />
      <img src="@/assets/image/notion.png" :width="60" />
    </p>
    <div class="line">
      <img
        src="@/assets/image/logo.png"
        :width="150"
        style="margin-right: 1%"
      />
      <p>@ 2023. 컴공쥬들 all rights reserved.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "MyFooter",
};
</script>

<style scoped>
.footer {
  margin-top: 3%;
  /* margin-bottom: 3%; */
  height: 20vh;
}
.line {
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
}
p img {
  margin: 1%;
}
.hr-sect {
  display: flex;
  flex-basis: 100%;
  align-items: center;
  color: rgba(149, 148, 148, 0.35);
  font-size: 12px;
  margin: 8px 0px;
}
.hr-sect::before,
.hr-sect::after {
  content: "";
  flex-grow: 1;
  background: rgba(149, 148, 148, 0.35);
  height: 1px;
  font-size: 0px;
  line-height: 0px;
  margin: 0px 16px;
}
</style>
