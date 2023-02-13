<template>
  <div id="search-field">
    <el-input
      style="max-width: 700px; height: 50px"
      v-model="words"
      @keyup.enter="goToSearchResult"
    >
    </el-input>
    <div id="search-button">
      <div @click="goToSearchResult">
        <el-icon size="30"><Search /></el-icon>
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
    ...mapState(studyStore, ["noResult"]),
  },
  data() {
    return {
      words: "",
      showAlert: false,
    };
  },
  methods: {
    ...mapActions(studyStore, ["searchConfirm"]),
    ...mapMutations(studyStore, ["SET_SEARCH_RESULT"]),

    async goToSearchResult() {
      if (this.words == "") {
        this.showAlert = true;
        console.log(this.showAlert);
        await this.sleep(3000).then(() => {
          this.showAlert = false;
          console.log(this.showAlert);
        });
      } else {
        await this.searchConfirm(this.words);
        if (this.noResult) {
          console.log("검색 결과 없음");
        } else {
          this.SET_SEARCH_RESULT(false);
          this.$router.push({ name: "search", params: { type: "result" } });
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
