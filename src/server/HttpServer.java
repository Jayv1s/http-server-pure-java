package server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void startServer() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        try(ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started on port 8080");

            while(true) {
                Socket clientSocket = serverSocket.accept();
                new ConnectionHandler(clientSocket).handler();
            }
        }
    }
}
