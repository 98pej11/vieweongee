<template>
  <el-container>
    <el-main class="main-box">
      <h2 class="text-h6 mb-3">스터디 만들기</h2>
      <el-form>
        <el-row :gutter="20">
          <el-col><p>제목</p></el-col>
          <el-col
            ><el-input placeholder="제목을 입력하세요."
          /></el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <p>기업</p>
          </el-col>
          <el-col :span="12">
            <p>직군</p>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-input placeholder="제목을 입력하세요." />
          </el-col>
          <el-col :span="12">
            <el-input  placeholder="제목을 입력하세요." />
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col><p>날짜</p></el-col>
          <el-col
            ><el-input placeholder="제목을 입력하세요."
          /></el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <p>인원</p>
          </el-col>
          <el-col :span="12">
            <p>면접 유형</p>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-input placeholder="제목을 입력하세요." />
          </el-col>
          <el-col :span="12">
            <el-input  placeholder="제목을 입력하세요." />
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-button
            color="#9DADD8"
            class="mt-10 mb-10"
            size="large"
            style="margin: 0 auto; margin-top: 3%"
          >
            채점 템플릿 선택
          </el-button>
        </el-row>

        <el-row :gutter="20">
          <el-col><p>진행시간</p></el-col>

          <el-checkbox-group>
            <el-checkbox label="1시간" />
            <el-checkbox label="2시간" />
            <el-checkbox label="3시간" />
            <el-checkbox label="4시간" />
          </el-checkbox-group>
        </el-row>

        <el-row :gutter="20">
          <el-col><p>소개</p></el-col>
          <el-col><el-input type="textarea"></el-input></el-col>
        </el-row>

        <el-row :gutter="20">
          <el-button
            block
            color="#9DADD8"
            class="mt-10 mb-10"
            size="large"
            style="margin: 0 auto; margin-top: 3%"
          >
            완료
          </el-button>
        </el-row>
      </el-form>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import { parseHeight } from 'element-plus/es/components/table/src/util';
import { ref, defineComponent} from 'vue'


export default defineComponent({ 
    name: "UserSignup" ,
    setup(){
        const checkList = ref(['1시간']);
    }
});
</script>


<style scoped>
h2 {
  text-align: center;
}
.el-container {
  margin: 0 auto;
  margin-top: 5%;
  width: 45%;
}

p {
  margin: 30px 0 10px 0;
}

.el-button {
  margin-top: 30px;
  color: white;
}
</style>
