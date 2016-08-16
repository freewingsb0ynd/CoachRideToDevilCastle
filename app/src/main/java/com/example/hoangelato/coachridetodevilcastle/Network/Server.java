package com.example.hoangelato.coachridetodevilcastle.Network;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

/**
 * Created by bloe on 07/08/2016.
 */

public abstract class Server extends NetworkNode {
    public static final int SERVER_PORT = 6969;
    public static final String NAME = "SERVER";

    ServerSocket serverSocket;
    Thread serverConnectionListerThread;

    public Server(Context context) {
        super(context);
    }

    public abstract void customInitialData(Bundle data);

    public void create() {
        serverConnectionListerThread = new Thread(new ServerListenForConnectionThread());
        serverConnectionListerThread.start();
    }

    public void stopListenForConnection() {
        if (serverConnectionListerThread != null) {
            serverConnectionListerThread.interrupt();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bundle getInitialData() {
        Bundle initialData = new Bundle();

        initialData.putString(IP_TAG, getStringCurrentIp());
        initialData.putString(ACTION_TAG, ACTION_SEND_INITIAL_DATA);

        customInitialData(initialData);

        return initialData;
    }


    private class ServerListenForConnectionThread extends Thread {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SERVER_PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    if (!isAlreadyConnected(clientSocket)) {
                        final Connection newConnection = new Connection(clientSocket);

                        Log.e("Server", "sending initial data");
                        send(newConnection, getInitialData());
                        Log.e("Server", "sent");

                        try {
                            Log.e("Server", "started listening for initial data");
                            Bundle initialData = getInitialDataFromConnection(newConnection);
                            addConnection(newConnection);
                            initialDataReceived(initialData, newConnection);
                            Log.e("Server", "received initial data from client");
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                            Log.e("Server", "client didn't response for initial data in time, will close the connection");
                            newConnection.close();
                        }
                    }

                    try {
                        this.sleep(THREAD_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e("Server", "thread sleep exception");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Server", "something is wrong with the serverSocket of data listener thread");
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (serverConnectionListerThread != null) {
            serverConnectionListerThread.interrupt();
        }
    }
}
