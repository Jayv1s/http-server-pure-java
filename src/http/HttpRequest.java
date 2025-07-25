package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class HttpRequest {
    public String method;
    public String path;
    public String protocol;
    public HashMap<String, String> headers;
    public String body;

    public HttpRequest() {
        headers = new HashMap<>();
    }

    public HttpRequest getRequestData(Socket clientSocket) throws IOException {
        InputStream inputStream = clientSocket.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = reader.readLine();

        if(line == null) {
            return null;
        }

        String[] firstLineSplitted = line.split(" ");

        if(firstLineSplitted.length < 3) {
            return null;
        }

        this.method = firstLineSplitted[0];
        this.path = firstLineSplitted[1];
        this.protocol = firstLineSplitted[2];

        while((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] headerLine = line.split(":", 2);

            headers.put(headerLine[0], headerLine[1]);
        }

        if(headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length").trim());

            char[] readBody =  new char[contentLength];

            reader.read(readBody, 0, contentLength);

            this.body = new String(readBody);
        }

        return this;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getBody() {
        return this.body;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }
}
