<template>
  <div>
    <div>
      <el-calendar :range="[new Date(2019, 2, 4), new Date(2019, 2, 24)]" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "MyData",
});
</script>
<style scoped></style>
