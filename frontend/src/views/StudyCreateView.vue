<template>
  <div class="study">
    <StudyCreate></StudyCreate>
  </div>
</template>

<script>
import StudyCreate from "@/components/board/StudyCreate.vue";

export default {
  name: "StudyView",
  components: {
    StudyCreate,
  },
};
</script>
<style scoped>
.study {
  text-align: center;
}
</style>
