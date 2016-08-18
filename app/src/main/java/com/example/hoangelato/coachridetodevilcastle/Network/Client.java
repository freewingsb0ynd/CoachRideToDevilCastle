package com.example.hoangelato.coachridetodevilcastle.Network;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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


    static class SearchIp implements Runnable {
        int start, end;
        Vector<Bundle> result = new Vector<>();
        ProgressListener progressListener;
        byte[] ip;
        Client client;

        public SearchIp(int start, int end, ProgressListener<Vector<Bundle>> progressListener, byte[] ip, Client client) {
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
                    result.add(initialData);
                    Log.e("Ip searcher", initialData.getString(NetworkNode.IP_TAG));
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

    public void findServersIp(final ProgressListener<Vector<Bundle>> progressListener, int timeOut, TimeUnit timeUnit) {
        final Vector<Bundle> mResult = new Vector<>(0);
        final int[] count = {0, 0};
        final int total = 254 / SOCKET_PER_THREAD + 1;
        byte[] bytesIp = getBytesCurrentIp();
        Log.e("Ip searcher: ", String.valueOf((int) bytesIp[0]) + String.valueOf((int) bytesIp[1]) + String.valueOf((int) bytesIp[2]));

        ExecutorService pool = Executors.newFixedThreadPool(total);

        for (int i = 0; i <= 254 / SOCKET_PER_THREAD; i++) {
            pool.submit(
                    new SearchIp(i * SOCKET_PER_THREAD + 1, Math.min((i + 1) * SOCKET_PER_THREAD, 255),
                            new ProgressListener<Vector<Bundle>>() {
                                @Override
                                public void onUpdateProgress(double percentage) {
                                    count[0]++;
                                    progressListener.onUpdateProgress((double) count[0] / 255);
                                }

                                @Override
                                public void onDone(Vector<Bundle> result) {
                                    count[1]++;
                                    mResult.addAll(result);
                                }
                            }, bytesIp, this)
            );
        }

        try {
            pool.awaitTermination(timeOut, timeUnit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdownNow();
        progressListener.onDone(mResult);

    }

    public void request(final Bundle req, int pos, final String expectedAction, final ProgressListener<Bundle> progressListener) {
        send(pos, req);


        addEventListener(new EventListener() {
            @Override
            public void onDataReceived(Bundle data, Connection connection) {
                if (expectedAction.equals(data.getString(ACTION_TAG))) {
                    progressListener.onDone(data);
                }
            }

            @Override
            public void onNewConnection(Connection connection) {

            }

            @Override
            public void onConnectFail(String reason, String destinationIp) {

            }

            @Override
            public void onInitialDataReceived(Bundle data, Connection connection) {

            }

            @Override
            public void onDisconnected(Bundle message, Connection connection) {

            }

            @Override
            public void onLosingConnection(Connection connection) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (clientThread != null && clientThread.isInterrupted() == false) {
            clientThread.interrupt();
        }
    }
}