<template>
  <div class="study">
    <StudyCreate></StudyCreate>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import StudyCreate from "@/components/board/StudyCreate.vue";

export default defineComponent({
  name: "StudyView",
  components: {
    StudyCreate,
  },
});
</script>
<style scoped>
.study {
  text-align: center;
}
</style>
