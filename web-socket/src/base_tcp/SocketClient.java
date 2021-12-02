package base_tcp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 65535)) {
            handler(socket.getInputStream(),socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handler(InputStream in, OutputStream out) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        System.out.println("server: "+reader.readLine());
        String res,rsp;
        do {
            System.out.print(">>> ");
            res = scanner.nextLine();
            writer.println(res);
            writer.flush();
            rsp = reader.readLine();
            System.out.println("<<< " + rsp);
        } while (!res.equals("bye"));
    }
}
