<template>
  <div>
    <StudyListCard></StudyListCard>
    <StudyCreate></StudyCreate>
    <StudyDetail></StudyDetail>
  </div>
</template>

<script>
import StudyDetail from "@/components/study/StudyDetail.vue";
import StudyCreate from "@/components/study/StudyCreate.vue";
import StudyListCard from "@/components/study/StudyListCard.vue";

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
