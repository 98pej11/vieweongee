<template>
    <div>
    </div>
    </template>
     
    <script>
    import {mapActions } from "vuex";
    import router from "@/router";
    // import store from "@/store";

    const memberStore = "memberStore";
     
    export default {
      name:'LoginRedirect',
      methods: {
    
        ...mapActions(memberStore, [
          "socialConfirm"
        ]),
        async confirm(tokens){
          await this.socialConfirm(tokens);
        }
    },
    // computed:{
    //     socailConfirm(){
    //       return this.$store.socailConfirm();
    //     }
    //   }
    // ,
      created() {
        
        console.log("로그인뷰다");
        console.log(this.$route.query.ACCESS);

        const token = this.$route.query.ACCESS;
        console.log('ACCESS',token)
        if (token) {
          let ACCESS = this.$route.query.ACCESS;
          let REFRESH = this.$route.query.REFRESH;
          const tokens=[ACCESS, REFRESH];
          console.log("드러와쓰");
          this.confirm(tokens);
          // this.socailConfirm(tokens);
          // this.socailConfirm(tokens);
            console.log(ACCESS + " 그리고 " + REFRESH);
            console.log(JSON.stringify(ACCESS));
            // commit("SET_IS_LOGIN", true);
            // commit("SET_IS_LOGIN_ERROR", false);
            // commit("SET_IS_VALID_TOKEN", true);
            // sessionStorage.setItem("ACCESS", ACCESS);
            // sessionStorage.setItem("REFRESH", REFRESH);


          window.alert('로그인하였습니다');
          router.push({path:'/'})
        } else {
          window.alert('로그인에 실패하였습니다.')
          router.push({path:'/login'})
        }
      },

    }
    
    </script>
     
    <style scoped>
     
    </style>