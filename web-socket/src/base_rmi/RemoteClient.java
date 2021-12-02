package base_rmi;

import java.rmi.Naming;

public class RemoteClient {
    public static void main(String[] args) {
        try {
            RemoteServer service = (RemoteServer) Naming.lookup("rmi://127.0.0.1/init");
            String result = service.init();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
