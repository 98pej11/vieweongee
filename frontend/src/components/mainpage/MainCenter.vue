<template>
  <div class="MainCenter">
    <div class="blurred-background slide-in-component">
      <img src="@/assets/image/interview2.png" :width="800" />
      <!-- <img
        src="@/assets/image/logo_mascot.png"
        style="margin-right: 2%; margin-left: 3%"
      />
      <img src="@/assets/image/interview2.png" :width="600" /> -->
    </div>
    <!-- <div>
      <img src="@/assets/image/logo_vieweongee.png" />
    </div> -->
  </div>
</template>
<script>
import { defineComponent } from "vue";

export default defineComponent({
  name: "MainCenter",
});
</script>

<style scoped>
@keyframes slideInFromSide {
  from {
    transform: translateX(-100%);
  }
  to {
    transform: translateX(0);
  }
}
.slide-in-component {
  animation: slideInFromSide 1s ease-out;
}
/* .blurred-background {
  background-size: cover;
  background-blend-mode: multiply;
  opacity: 0.7;
} */
.MainCenter {
  margin-top: 3%;
}
</style>
