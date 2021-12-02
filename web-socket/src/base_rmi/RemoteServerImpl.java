package base_rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RemoteServerImpl extends UnicastRemoteObject implements RemoteServer {
    public RemoteServerImpl() throws RemoteException {
    }

    @Override
    public String init() throws RemoteException {
        return "Get Server return";
    }

    public static void main(String[] args) {
        try {
            RemoteServer remoteServer = new RemoteServerImpl();
            Naming.rebind("init",remoteServer);
            LocateRegistry.createRegistry(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
