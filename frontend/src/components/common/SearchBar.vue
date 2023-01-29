<template>
  <div>
    <el-input style="max-width: 700px; height: 50px">
      <template #append>
        <el-button :icon="Search" @click="goToSearchResult"></el-button>
      </template>
    </el-input>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { Search } from "@element-plus/icons-vue";

export default defineComponent({
  name: "SearchBar",

  setup() {
    const searchQuery = ref("");
    const selectedCategory = ref("title");
    const router = useRouter();
    const store = useStore();
    const goToSearchResult = () => {
      if (searchQuery.value.trim()) {
        let query = {};
        if (selectedCategory.value === "title") {
          query = { title: searchQuery.value, page: 1 };
        }
      }

      const wholeQuery = {
        title: selectedCategory.value === "title" ? searchQuery.value : "",
      };
      router.push({ name: "Search", query: wholeQuery });
      //   store.dispatch("search", query);
    };

    return {
      searchQuery,
      selectedCategory,
      goToSearchResult,
      Search,
    };
  },
});
</script>
<style scoped></style>
