package http;

import enums.HttpContentTypeEnum;
import enums.HttpStatusEnum;

import java.nio.charset.StandardCharsets;

public class HttpResponse {
    HttpStatusEnum httpStatus;
    HttpContentTypeEnum httpContentTypeEnum;
    String body;

     public HttpResponse(HttpResponse.Builder builder) {
         this.httpStatus = builder.httpStatus;
         this.httpContentTypeEnum = builder.httpContentTypeEnum;
         this.body = builder.body;
     }

     public String getResponse() {
         int utf8Length = body.getBytes(StandardCharsets.UTF_8).length;

         return "HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getStatusDescription() + "\r\n" +
                 "Content-Type: text/plain\r\n" +
                 "Content-Length: " + utf8Length + "\r\n" +
                 "Connection: close\r\n" +
                 "\r\n" +
                 body;
     }


    public static class Builder {
        HttpStatusEnum httpStatus;
        HttpContentTypeEnum httpContentTypeEnum;
        String body;

        public Builder httpStatus(HttpStatusEnum httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder httpContentTypeEnum(HttpContentTypeEnum httpContentTypeEnum) {
            this.httpContentTypeEnum = httpContentTypeEnum;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}
