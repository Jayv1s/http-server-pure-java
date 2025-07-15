package server;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler {
    Socket clientSocket;

    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handler() throws IOException {
        HttpRequest httpRequest = new HttpRequest().getRequestData(clientSocket);

        switch (httpRequest.getMethod()) {
            case "GET":
                handleGET(httpRequest);
            case "DELETE":
                //TODO: handleDelete();
            case "POST":
                //TODO: handlePost();
            case "PUT":
                //TODO: handlePut();
            default:
                System.out.println("Unsupported HTTP request! Method" + httpRequest.getMethod() + " Path: " + httpRequest.getPath());
        }
    }

    public void handleGET(HttpRequest httpRequest) throws IOException {
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Path: " + httpRequest.getPath());
        System.out.println("Protocol: " + httpRequest.getProtocol());

        HttpResponse response = new HttpResponse().buildResponse();

        try(OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(response.getResponse().getBytes());
            outputStream.flush();
        }
    }
}
