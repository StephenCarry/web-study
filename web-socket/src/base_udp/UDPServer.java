package base_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket updSocket = new DatagramSocket(65535);
            String str;
            while (true) {
                byte[] bytes = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                updSocket.receive(packet);
                str = new String(packet.getData(),packet.getOffset(),packet.getLength());
                System.out.println(str);
                byte[] data = "ACK".getBytes();
                packet.setData(data);
                updSocket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
