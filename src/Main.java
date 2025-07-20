import server.HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Main {
    /*
    *
    * The term socket programming refers to writing programs that execute across multiple computers in which the devices are all connected to each other using a network.
    * Socket is one endpoint of a two-way communication
    *
    * TCP: Connection Oriented, connection must be estabilished between server and client
    * UDP: Connection-less
    *
    * HTTP defaults to UTF-8 (Unicode Transformation Format – 8-bit) this 8 defines numbers of bits used to store ASCII characters;
    * Java uses UTF-16 for String, Chars, etc. so when creating a request/response we have to convert/calculate the size to correct UTF-8 pattern;
    *
    * HTTP 1.1 uses CRLF lines ending (\r\n) so LF (\n) is not valid and can cause clients/servers to interpret it as malformed request/response
    * For example, use of """ to build string is not valid, since it results in a LF
    * INVALID:
    *         return """
                HTTP/1.1 200 OK
                Content-Type: text/plain
                Content-Length: 22
                Connection: close

                Hello from Java server
                """;
    * VALID:
    *         String body = "Hello from Java server";
              return "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: " + body.length() + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n" +
                        body;
    *ALSO VALID:
    *         String body = "Hello from Java server";
              return """
                HTTP/1.1 200 OK \r
                Content-Type: text/plain \r
                Content-Length: 22 \r
                Connection: close \r
                \r
                Hello from Java server\r
                \r
              """
    *
    * HTTP Request look like this:
        METHOD PATH HTTP/VERSION  (example: GET /index.txt HTTP/1.1)    ← first line, mandatory
        <HEADER>: <VALUE> (example: Host: localhost:8080)        ← 0 or more lines
        <HEADER>: <VALUE>
        <HEADER>: <VALUE>
        ...
        <CRLF>                     ← Blank line (mandatory to terminate headers)
        [OPTIONAL BODY]            ← Only for methods like POST or PUT

    * HTTP Response look like this: -
        HTTP/VERSION <status-code> <reason-phrase>   (example: HTTP/1.1 200 OK)           ← One line
        <HEADER>: <VALUE> (example: Content-Type: text/plain)          ← 0 or more headers
        <HEADER>: <VALUE>
        ...
        (blank line)                ← Mandatory: separates headers from body
        <BODY>                      ← Optional, based on status and method


    * GET on port 8080 using cURL:  curl http://localhost:8080/ (the Header "host" is automatically add by cURL - but if need to add manually: curl http://localhost:8080/ -H "Host: localhost:8080"
    * */
    public static void main(String[] args) throws IOException {
        HttpServer.startServer();
        System.out.println("Hello, World!");
    }
}