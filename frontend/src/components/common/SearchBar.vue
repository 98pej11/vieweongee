<template>
  <div>
    <el-input style="max-width: 700px; height: 50px" v-model="words">
    </el-input>
    <el-button @click="goToSearchResult">검색</el-button>
  </div>
</template>
<script>
import { mapActions } from "vuex";
const studyStore = "studyStore";

// import { Search } from "@element-plus/icons-vue";

export default {
  name: "SearchBar",
  components: {
    // Search,
  },
  data() {
    return {
      words: "",
    };
  },
  methods: {
    ...mapActions(studyStore, ["searchConfirm"]),

    async goToSearchResult() {
      await this.searchConfirm(this.words);
      // this.$router.push({ name: "studylist" });
    },
  },
};
</script>
<style scoped></style>
