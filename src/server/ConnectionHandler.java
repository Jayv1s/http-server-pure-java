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
                break;
            case "DELETE":
                handleDELETE(httpRequest);
                break;
            case "POST":
                handlePOST(httpRequest);
                break;
            case "PUT":
                handlePUT(httpRequest);
                break;
            default:
                System.out.println("Unsupported HTTP request! Method" + httpRequest.getMethod() + " Path: " + httpRequest.getPath());
                HttpResponse response = new HttpResponse().build404Response();

                try(OutputStream outputStream = clientSocket.getOutputStream()) {
                    outputStream.write(response.getResponse().getBytes());
                    outputStream.flush();
                }

                break;
        }
    }

    public void handlePUT(HttpRequest httpRequest) throws IOException {
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Path: " + httpRequest.getPath());
        System.out.println("Protocol: " + httpRequest.getProtocol());
        System.out.println("Body:" + httpRequest.getBody());

        String body = String.format("Body %s MODIFIED!", httpRequest.getBody());

        HttpResponse response = new HttpResponse().build200ResponseWithBody(body);

        try(OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(response.getResponse().getBytes());
            outputStream.flush();
        }
    }

    public void handlePOST(HttpRequest httpRequest) throws IOException {
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Path: " + httpRequest.getPath());
        System.out.println("Protocol: " + httpRequest.getProtocol());
        System.out.println("Body:" + httpRequest.getBody());

        HttpResponse response = new HttpResponse().build200ResponseWithBody(httpRequest.getBody());

        try(OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(response.getResponse().getBytes());
            outputStream.flush();
        }
    }

    public void handleGET(HttpRequest httpRequest) throws IOException {
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Path: " + httpRequest.getPath());
        System.out.println("Protocol: " + httpRequest.getProtocol());

        String body = "Hello from Java server"; //Doing body.length will return ONLY THE NUMBERS OF CHAR considering UTF-16

        HttpResponse response = new HttpResponse().build200ResponseWithBody(body);

        try(OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(response.getResponse().getBytes());
            outputStream.flush();
        }
    }

    public void handleDELETE(HttpRequest httpRequest) throws IOException {
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Path: " + httpRequest.getPath());
        System.out.println("Protocol: " + httpRequest.getProtocol());


        HttpResponse response = new HttpResponse().build202Response();

        try(OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(response.getResponse().getBytes());
            outputStream.flush();
        }
    }
}
