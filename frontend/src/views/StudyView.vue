<template>
  <div class="main">
    <MyHeader></MyHeader>
    <StudyCreate></StudyCreate>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import MyHeader from "@/components/common/MyHeader.vue";
// import StudyDetail from "@/components/board/StudyDetail.vue";
import StudyCreate from "@/components/board/StudyCreate.vue";

export default defineComponent({
  name: "StudyView",
  components: {
    MyHeader,
    // StudyDetail,
    StudyCreate,
  },
});
</script>
