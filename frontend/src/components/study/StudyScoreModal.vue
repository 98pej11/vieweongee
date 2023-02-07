<template>
  <div class="modal-view">
    모달입니다
    <div class="overlay">모달</div>
    <div class="modal-card">
      채점템플릿
      <div>채점표</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ModalView",
};
</script>

<style>
.modal-view {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  right: 0;
  z-index: 10;
}
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  right: 0;
  opacity: 0.5;
  background-color: black;
}
.modal-card {
  font-size: large;
  font-weight: bold;
  position: relative;
  max-width: 40%;
  margin: auto;
  margin-top: 30px;
  padding: 30px;
  background-color: #c7cde6;
  border-radius: 3%;
  box-shadow: 5px 5px 30px 3px rgba(0, 0, 0, 0.3);
  min-height: 500px;
  z-index: 10;
  opacity: 1;
}
</style>
