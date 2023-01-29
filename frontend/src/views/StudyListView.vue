<template>
  <div class="study">
    <StudyListCard></StudyListCard>
    <!-- <StudyCreate></StudyCreate> -->
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
// import StudyDetail from "@/components/board/StudyDetail.vue";
// import StudyCreate from "@/components/board/StudyCreate.vue";
import StudyListCard from "@/components/board/StudyListCard.vue";

export default defineComponent({
  name: "StudyView",
  components: {
    // StudyDetail,
    // StudyCreate,
    StudyListCard,
  },
});
</script>
<style scoped>
.study {
  text-align: center;
}
</style>
