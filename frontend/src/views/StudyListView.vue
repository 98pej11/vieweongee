<template>
  <div>
    <StudyListCard></StudyListCard>
    <StudyCreate></StudyCreate>
    <StudyDetail></StudyDetail>
  </div>
</template>

<script>
import StudyDetail from "@/components/board/StudyDetail.vue";
import StudyCreate from "@/components/board/StudyCreate.vue";
import StudyListCard from "@/components/board/StudyListCard.vue";

export default {
  name: "StudyView",
  components: {
    StudyDetail,
    StudyCreate,
    StudyListCard,
  },
};
</script>
<style scoped></style>
