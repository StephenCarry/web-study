package chat_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private Socket socket;

    private BufferedReader reader;

    private PrintWriter writer;

    private JTextArea incoming;

    private JTextField outgoing;

    private final String ip = "127.0.0.1";

    private final int port = 6666;

    public void gui() {
        //桌面窗体
        JFrame frame = new JFrame("Chat Client");
        JPanel mainPanel = new JPanel();

        //聊天框
        incoming = new JTextArea(25,30);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(incoming);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //输入框
        outgoing = new JTextField(25);

        //按钮
        JButton button = new JButton("Send");
        button.addActionListener(new SendButtonListener());

        //主窗体显示上述组件
        mainPanel.add(scrollPane);
        mainPanel.add(outgoing);
        mainPanel.add(button);

        //连接服务器且监听服务器回复
        try {
            this.initConnect();
        } catch (Exception e) {
            return;
        }
        try {
            this.initListen();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //显示主窗体
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400,600);
        frame.setVisible(true);
    }

    private void initConnect() {
        try {
            if (socket == null) socket = new Socket(ip,port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Client is connected to server...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListen() {
        new Thread(new ScrollPaneListener()).start();
    }

    public class ScrollPaneListener implements Runnable {

        @Override
        public void run() {
            String message;
            try {
                message = reader.readLine();
                while (message != null) {
                    incoming.append(message + "\n");
                    message = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            writer.println(outgoing.getText());
            writer.flush();
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    public static void main(String[] args) {
        new ChatClient().gui();
    }
}
