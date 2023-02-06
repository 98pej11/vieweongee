<template>
  <div>
    <!-- <StudyListCard v-if="type === 'studylist'"></StudyListCard> -->
    <!-- <StudyCreate v-if="type === 'create'"></StudyCreate> -->
    <!-- <StudyDetail v-if="type === 'detail'" @showComp="showComp"></StudyDetail> -->
    <router-view></router-view>
  </div>
</template>

<script>
// import StudyDetail from "@/components/study/StudyDetail.vue";
// import StudyCreate from "@/components/study/StudyCreate.vue";
// import StudyListCard from "@/components/study/StudyListCard.vue";

export default {
  name: "StudyView",
  components: {
    // StudyDetail,
    // StudyCreate,
    // StudyListCard,
  },
  data() {
    return { type: "studylist" };
  },
  mounted() {
    // this.showList();
  },
  methods: {},
};
</script>
<style scoped></style>
