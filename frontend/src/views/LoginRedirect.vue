<template>
    <div>
    </div>
    </template>
     
    <script>
    import router from "@/router";
    import store from "@/store";
     
    export default {
      created() {

        console.log("로그인뷰다");
        console.log(this.$route.query.ACCESS);

        const token = this.$route.query.ACCESS;
        console.log('ACCESS',token)
        if (token) {
          store.commit('setToken', token);
          sessionStorage.setItem('accessToken', token);
          window.alert('로그인하였습니다');
          router.push({path:'/'})
        } else {
          window.alert('로그인에 실패하였습니다.')
          router.push({path:'/login'})
        }
      }
    }
    </script>
     
    <style scoped>
     
    </style>