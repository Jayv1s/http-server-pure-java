package http;

import java.nio.charset.StandardCharsets;

public class HttpResponse {
    String response;

    public HttpResponse() {
        this.response = null;
    }

    public HttpResponse build200ResponseWithBody(String body) {
        int utf8Length = body.getBytes(StandardCharsets.UTF_8).length; //Here we are converting body to bytes already using UTF-8, the size of this array of bytes represents my content-length;
        this.response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + utf8Length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;

        return this;
    }

    public HttpResponse build202Response() {
        this.response = """
                HTTP/1.1 204 No Content\r
                Content-Type: text/plain\r
                Content-Length: 0\r
                Connection: close\r
                \r
                """;

        return this;
    }

    public HttpResponse build404Response() {
        String defaultNotFoundMessage = "URL / Path not found!";
        int length = defaultNotFoundMessage.getBytes(StandardCharsets.UTF_8).length;
        this.response = "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                defaultNotFoundMessage;

        return this;
    }

    public String getResponse() {
        return this.response;
    }
}
