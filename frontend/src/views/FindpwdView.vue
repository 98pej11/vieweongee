<template>
  <div class="findpwd">
    <FindPassword />
    <!--<SearchBar /> -->
  </div>
</template>

<script>
import { defineComponent } from "vue";
import FindPassword from "@/components/user/FindPassword.vue";

export default defineComponent({
  components: {
    FindPassword,
  },
});
</script>
<style scoped>
.MainCenter {
  text-align: center;
}
</style>
