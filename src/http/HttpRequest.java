package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    public String method;
    public String path;
    public String protocol;
    public List<String> headers;

    public HttpRequest() {
        headers = new ArrayList<>();
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

        while((line = reader.readLine()) != null &&  !line.isEmpty()) {
            headers.add(line);
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
}
