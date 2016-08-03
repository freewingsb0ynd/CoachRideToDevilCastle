package com.example.hoangelato.coachridetodevilcastle.Network;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by NguyenDuc on 8/2/2016.
 */
public class Client extends EndPoint {

    Thread clientThread;

    public Client() {
        super();
    }

    public Client(String serverAddress, int serverPort) {
        this();

        clientThread = new Thread(new ConnectToServer(serverAddress, serverPort));
        clientThread.start();
    }

    private class ConnectToServer extends Thread {
        String serverAddress;
        int serverPort;

        public ConnectToServer(String serverAddress, int serverPort) {
            this.serverAddress = serverAddress;
            this.serverPort = serverPort;
        }

        @Override
        public void run() {
            try {
                connectedConnections.add(new Connection(new Socket(serverAddress, serverPort)));
                onNewConnection(0);
            } catch (IOException e) {
                e.printStackTrace();
                onConnectFail(e.toString());
            }
        }
    }

    public void connectToHost(String serverAddress, int serverPort) {
        if (clientThread != null) {
            clientThread.interrupt();
        }
        connectedConnections.clear();

        clientThread = new Thread(new ConnectToServer(serverAddress, serverPort));
        clientThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clientThread.interrupt();
    }
}
