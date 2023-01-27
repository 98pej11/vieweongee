<template>
    <div class="CardList">
        {CardList.map((it) => (
          <MainCardItem key={it.id} {...it}></MainCardItem>
        ))}
    </div>
</template>
<script lang="ts">
import { defineComponent } from 'vue'
import MainCardItem from "@/components/mainpage/MainCardItem.vue";
export default defineComponent({
    name: "MainCardList",
    components: {
        MainCardItem
    },
    props: {
        // CardList
    }
})
</script>
<style scoped>

</style>>