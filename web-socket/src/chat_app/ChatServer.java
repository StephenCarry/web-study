package chat_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private ExecutorService threadPool;

    private List<PrintWriter> allUser;

    private final int port = 6666;

    public void chatServer() {
        threadPool = Executors.newFixedThreadPool(8);
        allUser = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                allUser.add(new PrintWriter(socket.getOutputStream()));
                System.out.println("["+socket.getRemoteSocketAddress()+"] "+"is connected");
                new Thread(new ChatClientHandler(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ChatClientHandler implements Runnable {
        private Socket socket;

        private BufferedReader reader;

        public ChatClientHandler () {
            super();
        }

        public ChatClientHandler(Socket socket) {
            super();
            try {
                this.socket = socket;
                this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    for (PrintWriter printWriter : allUser) {
                        printWriter.println("["+socket.getRemoteSocketAddress()+"]:"+message);
                        printWriter.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ChatServer().chatServer();
    }
}
