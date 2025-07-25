package server;

import domain.SimpleObject;
import enums.HttpContentTypeEnum;
import enums.HttpStatusEnum;
import http.HttpRequest;
import http.HttpResponse;
import parser.JsonParser;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConnectionHandler {
    Socket clientSocket;
    JsonParser<SimpleObject> jsonParser;

    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.jsonParser = new JsonParser<>();
    }

    public void handler() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
                HttpResponse response = new HttpResponse.Builder()
                        .httpStatus(HttpStatusEnum.NOT_FOUND_404)
                        .httpContentTypeEnum(HttpContentTypeEnum.TEXT)
                        .build();

                try(OutputStream outputStream = clientSocket.getOutputStream()) {
                    outputStream.write(response.getResponse().getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                }

                break;
        }
    }

    public void handlePUT(HttpRequest httpRequest) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Path: " + httpRequest.getPath());
        System.out.println("Protocol: " + httpRequest.getProtocol());
        System.out.println("Body:" + httpRequest.getBody());

        String type = httpRequest.getHeaders().get("Content-Type");

        String body;

        if(HttpContentTypeEnum.isJSON(type.trim())) {
            SimpleObject bodyObject = jsonParser.parse(httpRequest.getBody(), SimpleObject.class);
            bodyObject.setName("Cecilia");
            body = bodyObject.toString();
        } else {
            body = httpRequest.getBody();
        }

        HttpResponse response = new HttpResponse.Builder()
                .httpStatus(HttpStatusEnum.OK_200)
                .httpContentTypeEnum(HttpContentTypeEnum.TEXT)
                .body(body)
                .build();

        try(OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(response.getResponse().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }

    public void handlePOST(HttpRequest httpRequest) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Path: " + httpRequest.getPath());
        System.out.println("Protocol: " + httpRequest.getProtocol());
        System.out.println("Body:" + httpRequest.getBody());

        String type = httpRequest.getHeaders().get("Content-Type");

        String body;

        if(HttpContentTypeEnum.isJSON(type.trim())) {
            SimpleObject bodyObject = jsonParser.parse(httpRequest.getBody(), SimpleObject.class);
            body = bodyObject.toString();
        } else {
            body = httpRequest.getBody();
        }

        HttpResponse response = new HttpResponse.Builder()
                .httpStatus(HttpStatusEnum.OK_200)
                .httpContentTypeEnum(HttpContentTypeEnum.TEXT)
                .body(body)
                .build();

        try(OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(response.getResponse().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }

    public void handleGET(HttpRequest httpRequest) throws IOException {
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Path: " + httpRequest.getPath());
        System.out.println("Protocol: " + httpRequest.getProtocol());

        String body = "Hello from Java server"; //Doing body.length will return ONLY THE NUMBERS OF CHAR considering UTF-16

        HttpResponse response = new HttpResponse.Builder()
                .httpStatus(HttpStatusEnum.OK_200)
                .httpContentTypeEnum(HttpContentTypeEnum.TEXT)
                .body(body)
                .build();

        try(OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(response.getResponse().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }

    public void handleDELETE(HttpRequest httpRequest) throws IOException {
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Path: " + httpRequest.getPath());
        System.out.println("Protocol: " + httpRequest.getProtocol());


        HttpResponse response = new HttpResponse.Builder()
                .httpStatus(HttpStatusEnum.NO_CONTENT_204)
                .httpContentTypeEnum(HttpContentTypeEnum.TEXT)
                .build();

        try(OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(response.getResponse().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }
}
