package base_udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        try {
            //连接
            DatagramSocket udpSocket = new DatagramSocket();
            udpSocket.setSoTimeout(1000);
            udpSocket.connect(InetAddress.getByName("localhost"),65535);
            //发送数据
            byte[] data = "hello".getBytes();
            DatagramPacket packet = new DatagramPacket(data,data.length);
            udpSocket.send(packet);
            //接收数据
            byte[] bytes = new byte[1024];
            packet = new DatagramPacket(bytes, bytes.length);
            udpSocket.receive(packet);
            String rsp = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println(rsp);
            //结束
            udpSocket.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
