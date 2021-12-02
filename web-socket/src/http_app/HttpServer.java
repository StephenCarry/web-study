package http_app;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    public static void main(String[] args) {
        try {
            ExecutorService threadPool = Executors.newFixedThreadPool(8);
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("connect to "+socket.getRemoteSocketAddress());
                threadPool.execute(new Handler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Handler implements Runnable {
        private final Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String request;
                while ((request = reader.readLine()) != null) {
                    System.out.println(request);
                }
                writer.write("HTTP/1.0 200 OK\r\n");
                writer.write("Content-Type:text/html\r\n");
                writer.write("Content-Length:50\r\n");
                writer.write("\r\n");
                writer.write("<html><body><h1>Hello, world!</h1></body></html>\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
