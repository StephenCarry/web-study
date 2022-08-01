package com.yaoo.message;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 8080));

        Scanner scanner = new Scanner(System.in);
        int code;
        while(true) {
            code = scanner.nextInt();
            if (code == -1) {
                break;
            } else if (code == 0) {
                ByteBuffer buffer = ByteBuffer.allocate(5);
                buffer.put((byte) 0);
                buffer.putInt(0);
                socket.getOutputStream().write(buffer.array());
            } else if (code == 1) {
                byte[] content = ("hello, I'm "+ socket.hashCode()).getBytes();
                ByteBuffer buffer = ByteBuffer.allocate(content.length+5);
                buffer.put((byte) 1);
                buffer.putInt(content.length);
                buffer.put(content);
                socket.getOutputStream().write(buffer.array());
            }
        }
        socket.close();
    }
}
