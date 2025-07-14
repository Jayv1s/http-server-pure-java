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
    public static String method;
    public static String path;
    public static String protocol;

    public static void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket clientSocket = serverSocket.accept();

        InputStream inputStream = clientSocket.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = reader.readLine();

        if(isGetMethod(line)) {
            System.out.println("Method: " + method);
            System.out.println("Path: " + path);
            System.out.println("Protocol: " + protocol);

            while((line = reader.readLine()) != null &&  !line.isEmpty()) {
                System.out.println("Ignoring header: " + line);
            }

            String response = buildResponse();

            try(OutputStream outputStream = clientSocket.getOutputStream()) {
                outputStream.write(response.getBytes());
                outputStream.flush();
            }

        } else {
            System.out.println("Unsupported HTTP request!" + line);
        }
    }

    public static String buildResponse() {
        String body = "Hello from Java server"; //Doing body.length will return ONLY THE NUMBERS OF CHAR considering UTF-16
        int utf8Length = body.getBytes(StandardCharsets.UTF_8).length; //Here we are converting body to bytes already using UTF-8, the size of this array of bytes represents my content-length;
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + utf8Length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;
    }

    public static boolean isGetMethod(String firstLine) {
        if(firstLine == null) {
            return false;
        }

        String[] firstLineSplitted = firstLine.split(" ");

        //Basic firstLine is composed of METHOD - PATH - PROTOCOL
        if(firstLineSplitted.length < 3) {
            return false;
        }

        method = firstLineSplitted[0];
        path = firstLineSplitted[1];
        protocol = firstLineSplitted[2];

        return Objects.equals(firstLineSplitted[0], "GET");
    }


    public static void main(String[] args) throws IOException {
        startServer();
        System.out.println("Hello, World!");
    }
}