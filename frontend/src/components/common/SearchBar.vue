<template>
  <div id="search-field">
    <el-input style="max-width: 700px; height: 50px" v-model="words">
    </el-input>
    <div id="search-button">
      <div @click="goToSearchResult">
        <el-icon size="30" style="cursor: pointer"><Search /></el-icon>
      </div>
      <!-- <el-button @click="goToSearchResult"></el-butto> -->
    </div>
    <div v-if="noResult">
      <el-alert title="검색 결과가 없습니다" type="warning" center show-icon />
    </div>
    <div v-if="showAlert">
      <el-alert
        title="검색 내용을 입력해주세요"
        type="error"
        center
        show-icon
      />
    </div>
  </div>
</template>
<script>
import { mapActions, mapMutations, mapState } from "vuex";
import { Search } from "@element-plus/icons-vue";
const studyStore = "studyStore";

export default {
  name: "SearchBar",
  components: {
    Search,
  },
  computed: {
    ...mapState(studyStore, ["noResult", "studyList"]),
  },
  data() {
    return {
      words: "",
      showAlert: false,
    };
  },
  methods: {
    ...mapActions(studyStore, ["searchConfirm", "getPersonnel"]),
    ...mapMutations(studyStore, ["SET_SEARCH_RESULT", "SET_STUDY_LIST"]),

    async goToSearchResult() {
      if (this.words == "") {
        this.showAlert = true;
        await this.sleep(3000).then(() => {
          this.showAlert = false;
        });
      } else {
        await this.searchConfirm(this.words);

        // 현재 참가자 수 받아오기
        for (let idx = 0; idx < this.studyList.length; idx++) {
          await this.getPersonnel(this.studyList[idx].id);
        }
        if (this.noResult) {
          console.log("검색 결과 없음");
        } else {
          // console.log(this.studyList);
          this.SET_SEARCH_RESULT(false);
          this.$router.replace({ name: "search" });
        }
      }
    },
    sleep(ms) {
      return new Promise((r) => setTimeout(r, ms));
    },
  },
};
</script>
<style scoped>
#search-field {
  margin: 0 auto;
  max-width: 700px;
  position: relative;
  z-index: 1;
}

#search-button {
  width: 100px;
  z-index: 2;
  position: absolute;
  top: 10px;
  right: 1px;
}

button {
  color: white;
  z-index: 6;
  /* width: 100%; */
}

.el-icon {
  color: gray;
}
</style>
