FROM node:18 as build-stage
WORKDIR /app
ADD . .
RUN npm install
RUN npm run build

FROM nginx:stable-alpine as production-stage
#컨테이너 안에 nginx.conf를 옆 해당경로에 옮겨준다.
COPY  ./nginx/nginx.conf /etc/nginx/conf.d/default.conf 

COPY --from=build-stage /app/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]