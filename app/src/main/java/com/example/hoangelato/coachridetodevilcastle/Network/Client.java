package com.example.hoangelato.coachridetodevilcastle.Network;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

/**
 * Created by bloe on 07/08/2016.
 */

public abstract class Client extends NetworkNode {
    public static final int SOCKET_PER_THREAD = 10;
    public static final String NAME = "Client";

    Thread clientThread;


    public Client(Context context) {
        super(context);
    }

    public abstract void customInitialData(Bundle data);

    private Bundle getInitialData() {
        Bundle initialData = new Bundle();

        initialData.putString(IP_TAG, getStringCurrentIp());
        initialData.putString(ACTION_TAG, ACTION_SEND_INITIAL_DATA);

        customInitialData(initialData);

        return initialData;
    }


    public void connectToHost(String serverAddress, int serverPort) {
        if (clientThread != null) {
            clientThread.interrupt();
        }
        disconnectAll(createBundleWithAction(ACTION_DISCONNECT));

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
                final Connection newConnection = new Connection(new Socket(serverAddress, serverPort));

                Log.e("Client", "sending initial data");
                send(newConnection, getInitialData());
                Log.e("Client", "sent");

                try {
                    Log.e("Client", "start listening for initial data from server");
                    Bundle initialData = getInitialDataFromConnection(newConnection);
                    addConnection(newConnection);
                    initialDataReceived(initialData, newConnection);
                    Log.e("Client", "received initial data from server");
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    Log.e("Client", "server didn't response in time, will close the connection");
                    newConnection.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                connectFail(e.toString(), serverAddress);
                Log.e("Client", "cant connect to server " + e.toString());
            }
        }
    }


    static class SearchIp extends Thread {
        int start, end;
        Vector<String> result = new Vector<>();
        ProgressListener progressListener;
        byte[] ip;
        Client client;

        public SearchIp(int start, int end, ProgressListener<Vector<String>> progressListener, byte[] ip, Client client) {
            this.start = start;
            this.end = end;
            this.progressListener = progressListener;
            this.ip = ip.clone();
            this.client = client;
        }

        @Override
        public void run() {

            for (int i = start; i <= end; i++) {
                ip[3] = (byte) i;

                try {
                    Socket s = new Socket();
                    s.connect(new InetSocketAddress(InetAddress.getByAddress(ip), Server.SERVER_PORT), 1000);
                    Bundle initialData = client.getInitialDataFromConnection(new Connection(s));
                    result.add(initialData.getString(IP_TAG));
                    Log.e("Ip searcher", result.lastElement());
                    s.close();

                } catch (IOException e) {
                    //e.printStackTrace();
                } catch (TimeoutException e) {
                    //e.printStackTrace();
                    Log.e("Ip searcher", "another app is also listening to 6969 port but isn't our game");
                }
                Log.e("Ip searcher", String.valueOf(i));
                progressListener.onUpdateProgress(0);
            }

            progressListener.onDone(result);
        }
    }
    public void findServersIp(final ProgressListener<Vector<String>> progressListener) {
        final Vector<String> mResult = new Vector<>(0);
        final int[] count = {0, 0};
        final int total = 254 / SOCKET_PER_THREAD + 1;
        byte[] bytesIp = getBytesCurrentIp();

        for (int i = 0; i <= 254 / SOCKET_PER_THREAD; i++) {
            new Thread(
                    new SearchIp(i * SOCKET_PER_THREAD + 1, Math.min((i + 1) * SOCKET_PER_THREAD, 255),
                            new ProgressListener<Vector<String>>() {
                                @Override
                                public void onUpdateProgress(double percentage) {
                                    count[0]++;
                                    progressListener.onUpdateProgress((double) count[0]/255);
                                }

                                @Override
                                public void onDone(Vector<String> result) {
                                    count[1]++;
                                    mResult.addAll(result);
                                }
                            }, bytesIp, this)
            ).start();
        }

        while (true) {
            if (count[1] == total) {
                progressListener.onDone(mResult);
                return;
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (clientThread != null && clientThread.isInterrupted() == false) {
            clientThread.interrupt();
        }
    }
}