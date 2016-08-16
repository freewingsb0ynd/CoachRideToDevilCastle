package com.example.hoangelato.coachridetodevilcastle.Network;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by NguyenDuc on 8/5/2016.
 */

public class NetworkNode {

    public static final String ACTION_TAG = "action";
    public static final String ACTION_DISCONNECT = "disconnect";
    public static final String ACTION_CHECK_IF_CONNECTION_ALIVE = "check";
    public static final String ACTION_SEND_INITIAL_DATA = "sending initial data";

    public static final String IP_TAG = "ip";
    public static final String NAME_TAG = "name";

    protected static final int TIME_OUT = 3;
    protected static final int THREAD_SLEEP_TIME = 500;

    protected Context context;
    protected Thread dataListenerThread;
    protected ConnectionChecker connectionChecker;
    protected WifiManager wm;

    Vector<Connection> connections = new Vector<>();
    Vector<EventListener> eventListeners = new Vector<>();

    public NetworkNode(Context context) {
        this.context = context;

        wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        createDataListenerThread();
        connectionChecker = new ConnectionChecker();
    }

    public static Bundle createBundleWithAction(String action) {
        Bundle bundle = new Bundle();
        bundle.putString(ACTION_TAG, action);

        return bundle;
    }

    private InetAddress getInetAddress() {
        InetAddress mAddress = null;
        try {
            mAddress = InetAddress.getByName(Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return mAddress;
    }

    public String getStringCurrentIp() {
        return getInetAddress().getHostAddress();
    }

    public byte[] getBytesCurrentIp() {
        return getInetAddress().getAddress();
    }

    public void addConnection(Connection newConnection) {
        connections.add(newConnection);
        newConnection(newConnection);
    }

    public boolean isAlreadyConnected(Socket socket) {
        return connections.contains(new Connection(socket));
    }

    public int getConnectionIndex(Connection connection) {
        return connections.indexOf(connection);
    }

    public Connection getConnection(int connectionPos) {
        return connections.get(connectionPos);
    }

    public void disconnectWith(int connectionPos, Bundle message) {
        disconnectWith(connections.get(connectionPos), message);
    }

    public void disconnectWith(Connection connection, Bundle message) {
        this.send(connection, message);
        connections.remove(connection);
        connection.close();
    }

    public void disconnectWith(Connection connection) {
        disconnectWith(connection, createBundleWithAction(ACTION_DISCONNECT));
    }

    public void disconnectAll(Bundle message) {
        Enumeration<Connection> connectionEnumeration = connections.elements();
        while (connectionEnumeration.hasMoreElements()) {
            disconnectWith(connectionEnumeration.nextElement(), message);
        }
    }

    public void disconnectAll() {
        disconnectAll(createBundleWithAction(ACTION_DISCONNECT));
    }

    public void stopListenForData() {
        if (dataListenerThread != null) {
            dataListenerThread.interrupt();
        }
    }

    public void createDataListenerThread() {
        dataListenerThread = new Thread(new ListenForDataThread());
        dataListenerThread.start();
        addDefaultListener();
    }

    public void addEventListener(EventListener eventListener) {
        eventListeners.add(eventListener);
    }

    private void addDefaultListener() {
        addEventListener(new EventListener() {
            @Override
            public void onDataReceived(Bundle data, Connection connection) {
                String action = data.getString(ACTION_TAG);
                if (action.equals(ACTION_DISCONNECT)) {
                    disconnected(data, connection);
                    disconnectWith(connection);
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

    void dataReceived(Bundle dataReceived, Connection connection) {
        Enumeration<EventListener> solvers = eventListeners.elements();
        while (solvers.hasMoreElements()) {
            solvers.nextElement().onDataReceived(dataReceived, connection);
        }
    }

    void newConnection(Connection connection) {
        Enumeration<EventListener> solvers = eventListeners.elements();
        while (solvers.hasMoreElements()) {
            solvers.nextElement().onNewConnection(connection);
        }
    }

    void connectFail(String reason, String destinationIp) {
        Enumeration<EventListener> solvers = eventListeners.elements();
        while (solvers.hasMoreElements()) {
            solvers.nextElement().onConnectFail(reason, destinationIp);
        }
    }

    void initialDataReceived(Bundle data, Connection connection) {
        Enumeration<EventListener> solvers = eventListeners.elements();
        while (solvers.hasMoreElements()) {
            solvers.nextElement().onInitialDataReceived(data, connection);
        }
    }

    void disconnected(Bundle message, Connection connection) {
        Enumeration<EventListener> solvers = eventListeners.elements();
        while (solvers.hasMoreElements()) {
            solvers.nextElement().onDisconnected(message, connection);
        }
    }

    void losingConnection(Connection connection) {
        Enumeration<EventListener> solvers = eventListeners.elements();
        while (solvers.hasMoreElements()) {
            solvers.nextElement().onLosingConnection(connection);
        }
    }


    public void send(int connectionPos, Parcelable data) {
        send(connections.get(connectionPos), data);
    }

    public void send(Connection connection, Parcelable data) {
        byte[] bytes = DataHelper.toByte(data);
        try {
            connection.mObjectWriter.writeInt(bytes.length);
            connection.mObjectWriter.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(Parcelable data) {
        byte[] bytes = DataHelper.toByte(data);

        Enumeration<Connection> connectionEnumeration = connections.elements();
        while (connectionEnumeration.hasMoreElements()) {
            send(connectionEnumeration.nextElement(), data);
        }
    }

    public Bundle getInitialDataFromConnection(final Connection newConnection) throws TimeoutException {
        return (Bundle) ThreadHelper.executeTaskWithTimeout(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                while (true) {
                    if (newConnection.mObjectReader.available() != 0) {
                        int dataLength = newConnection.mObjectReader.readInt();
                        byte[] bytesReceived = new byte[dataLength];
                        newConnection.mObjectReader.readFully(bytesReceived);

                        return DataHelper.toBundle(bytesReceived);
                    }
                }
            }
        }, TIME_OUT*1000);
    }

    public void onDestroy() {
        disconnectAll(createBundleWithAction(ACTION_DISCONNECT));

        dataListenerThread.interrupt();
        connectionChecker.onDestroy();
    }

    class ListenForDataThread extends Thread {
        @Override
        public void run() {
            while (true) {
                Enumeration<Connection> connection = connections.elements();
                Log.e("Listen for data Thread",
                        "number of connection " + String.valueOf(connections.size()));
                while (connection.hasMoreElements()) {
                    Connection curConnection = connection.nextElement();
                    try {
                        if (curConnection.mObjectReader.available() != 0) {
                            int dataLength = curConnection.mObjectReader.readInt();
                            byte[] bytesReceived = new byte[dataLength];

                            curConnection.mObjectReader.readFully(bytesReceived);

                            dataReceived(DataHelper.toBundle(bytesReceived), curConnection);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {

                    this.sleep(THREAD_SLEEP_TIME);
                } catch (InterruptedException e) {
                    Log.e("Listen for data thread", "interrupted");
                    e.printStackTrace();
                    return;
                }

                if (isInterrupted()) {
                    return;
                }
            }
        }
    }

    public class ConnectionChecker {
        Thread sendMessage;
        Thread checkConnection;

        public ConnectionChecker() {
            sendMessage = new Thread(new Thread() {
                @Override
                public void run() {
                    Bundle message = new Bundle();
                    message.putString(ACTION_TAG, ACTION_CHECK_IF_CONNECTION_ALIVE);
                    while (true) {
                        try {
                            sleep(THREAD_SLEEP_TIME * 1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sendToAll(message);
                    }

                }
            });
            sendMessage.start();

            addEventListener(new EventListener() {
                @Override
                public void onDataReceived(Bundle data, Connection connection) {
                    String action = data.getString(ACTION_TAG);
                    if (action.equals(ACTION_CHECK_IF_CONNECTION_ALIVE)) {
                        connection.lastChecked = new Date().getTime();
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

            checkConnection = new Thread(new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            sleep(THREAD_SLEEP_TIME * 8);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        long currentTime = new Date().getTime();
                        Enumeration<Connection> connectionEnumeration = connections.elements();
                        while (connectionEnumeration.hasMoreElements()) {
                            Connection curConnection = connectionEnumeration.nextElement();
                            long delta = currentTime - curConnection.lastChecked;
                            if (delta > THREAD_SLEEP_TIME * 8) {
                                losingConnection(curConnection);
                            }
                        }
                    }
                }
            });
            checkConnection.start();
        }

        public void onDestroy() {
            sendMessage.interrupt();
            checkConnection.interrupt();
        }
    }
}
