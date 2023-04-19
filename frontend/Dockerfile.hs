FROM node:alpine as development

WORKDIR "/app"

COPY package.json /app/package.json

RUN npm install

COPY ./ /app



FROM development AS build
RUN npm run build


FROM nginx:alpine

COPY --from=build /app/.nginx/nginx.conf /etc/nginx/conf.d/default.conf

WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

COPY --from=build /app/build ./

ENTRYPOINT ["nginx", "-g", "daemon off;"]