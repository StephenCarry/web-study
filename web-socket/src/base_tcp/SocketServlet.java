package base_tcp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServlet {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(65535);
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        System.out.println("Server is running...");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Connected from " + socket.getRemoteSocketAddress());
//            Thread thread = new Handler(socket);
            executorService.execute(new ConnectHandler(socket));
        }
    }

    static class ConnectHandler extends Thread {
        Socket socket;

        public ConnectHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (InputStream in = socket.getInputStream()) {
                try (OutputStream out = socket.getOutputStream()) {
                    String socket = this.socket.getRemoteSocketAddress().toString();
                    handle(in, out, socket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handle(InputStream in, OutputStream out, String socket) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
            writer.write("hello! "+socket+"\n");
            writer.flush();
            while (true) {
                String s = reader.readLine();
                if (s.equals("bye")) {
                    writer.write("bye\n");
                    writer.flush();
                    break;
                }
                writer.write("ok\n");
                writer.flush();
                System.out.println(Thread.currentThread()+"Accept("+socket+"): "+s);
            }
        }
    }

    static class Handler extends Thread {
        Socket socket;

        public Handler(Socket socket) {
            this.socket=socket;
        }

        private void handle(InputStream in, OutputStream out) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
            /*接收请求*/
            boolean requestOk = false;
            if(reader.readLine().startsWith("GET / HTTP/")) requestOk = true;
            while (true) {
                String head = reader.readLine();
                if(head.isEmpty()) break;
                System.out.println(head);
            }
            System.out.println(requestOk ? "Response OK" : "Response error");
            /*返回结果*/
            if(requestOk) {
                String returnData = "<html><body><h1>Hello web-Socket</h1></body></html>";
                int length = returnData.getBytes(StandardCharsets.UTF_8).length;
                writer.write("HTTP/1.0 200 OK\r\n");
                writer.write("Connection: close\r\n");
                writer.write("Content-Type: text\\html\r\n");
                writer.write("\r\n");
                writer.write(returnData);
                writer.flush();
            } else {
                writer.write("HTTP/1.0 404 Not Found\r\n");
                writer.write("Content-Length: 0\r\n");
                writer.write("\r\n");
                writer.flush();
            }
        }

        public void run() {
            try(InputStream in = this.socket.getInputStream()) {
                try(OutputStream out = this.socket.getOutputStream()) {
                    handle(in, out);
                }
            } catch (Exception e) {
                try {
                    this.socket.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}
