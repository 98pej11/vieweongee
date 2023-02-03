<template>
  <div>
    <h3 class="text-h6 mb-3">참여예정 스터디 목록</h3>

    <div class="block text-center" style="margin-bottom: 10%">
      <el-carousel height="300px">
        <el-carousel-item v-for="item in 4" :key="item">
          <h3 class="small justify-center" text="2xl">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>
    <h3 class="text-h6 mb-3">나의 스터디 전체 통계</h3>
    <div class="demo-progress">
      <el-progress :percentage="50" />
      <el-progress :percentage="100" :format="format" />
      <el-progress :percentage="100" status="success" />
      <el-progress :percentage="100" status="warning" />
      <el-progress :percentage="50" status="exception" />
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  name: "MyMain",
  data() {
    return {
      format: (percentage) => (percentage === 100 ? "Full" : `${percentage}%`),
      
    };
  },
});
</script>

<style scoped>
h3 {
  text-align: center;
  margin-bottom: 6%;
}

.demo-progress {
  margin-top: 10%;
}
.demo-progress .el-progress--line {
  margin-bottom: 15px;
  width: 50%;
}
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
