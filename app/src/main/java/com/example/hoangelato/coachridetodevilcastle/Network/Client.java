package com.example.hoangelato.coachridetodevilcastle.Network;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.RecoverySystem;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by NguyenDuc on 8/2/2016.
 */
public class Client extends EndPoint {

    public static final int SOCKET_PER_THREAD = 20;

    Context mContext;
    Thread clientThread;

    public Client(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public Client(String serverAddress, int serverPort, Context mContext) {
        this(mContext);

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


    private static class SearchIp extends Thread {
        int start, end;
        Vector<String> result;
        EndPoint.ProgressListener progressListener;
        byte[] ip;

        public SearchIp(int start, int end, Vector<String> result, ProgressListener progressListener, byte[] ip) {
            this.start = start;
            this.end = end;
            this.result = result;
            this.progressListener = progressListener;
            this.ip = ip.clone();
        }

        @Override
        public void run() {


            for (int i = start; i <= end; i++) {
                ip[3] = (byte) i;

                try {
                    Socket s = new Socket();
                    s.connect(new InetSocketAddress(InetAddress.getByAddress(ip), 6969), 1000);
                    Log.d("found ", String.valueOf(ip[3]));
                    result.add(s.getInetAddress().getHostAddress());
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }

            progressListener.onDone(null);
        }
    }

    public void findServersIp(final ProgressListener progressListener) {
        Vector<String> result = new Vector<>(0);
        final int[] count = {0};
        final int total = 254 / SOCKET_PER_THREAD + 1;

        final WifiManager wm = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        final byte[] bytes = inetAddress.getAddress();

        for (int i = 0; i <= 254 / SOCKET_PER_THREAD; i++) {
            new Thread(
                    new SearchIp(i * SOCKET_PER_THREAD + 1, Math.min((i + 1) * SOCKET_PER_THREAD, 255), result,
                            new ProgressListener() {
                                @Override
                                public void onUpdateProgress(double percentage) {

                                }

                                @Override
                                public void onDone(Object result) {
                                    count[0]++;
                                    progressListener.onUpdateProgress((double) count[0] / total);
                                }
                            }, bytes)
            ).start();
        }

        while (true) {
            if (count[0] == total) {
                progressListener.onDone(result);
                return;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clientThread.interrupt();
    }
}
