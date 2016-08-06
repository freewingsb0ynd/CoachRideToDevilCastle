package com.example.hoangelato.coachridetodevilcastle.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by NguyenDuc on 8/2/2016.
 */
public class Server extends EndPoint {
    public static final int SERVERPORT = 6969;
    ServerSocket serverSocket;
    Thread serverThread;

    public Server() {
        super();

        serverThread = new Thread(new ServerListenForConnectionThread());
        serverThread.start();
    }

    private class ServerListenForConnectionThread extends Thread {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SERVERPORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    if (!isConnected(clientSocket)) {
                        connectedConnections.add(new Connection(clientSocket));
                        onNewConnection(connectedConnections.size()-1);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serverThread.interrupt();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
