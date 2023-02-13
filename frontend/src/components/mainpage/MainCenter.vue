<template>
  <div class="MainCenter">
    <div class="button-component">
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
/* @keyframes slideInFromSide {
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
/* @keyframes animateComponent {
  0% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(50%) translateX(100%) rotate(45deg);
  }
  100% {
    transform: translateY(0%) translateX(200%) rotate(90deg);
  }
}
.button-component {
  animation: animateComponent 3s ease-in-out infinite;
} */
.MainCenter {
  margin-top: 3%;
}
</style>
