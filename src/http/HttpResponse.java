package http;

import java.nio.charset.StandardCharsets;

public class HttpResponse {
    String response;

    public HttpResponse() {
        this.response = null;
    }

    public HttpResponse buildResponse() {
        String body = "Hello from Java server"; //Doing body.length will return ONLY THE NUMBERS OF CHAR considering UTF-16
        int utf8Length = body.getBytes(StandardCharsets.UTF_8).length; //Here we are converting body to bytes already using UTF-8, the size of this array of bytes represents my content-length;
        this.response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + utf8Length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;

        return this;
    }

    public String getResponse() {
        return this.response;
    }
}
