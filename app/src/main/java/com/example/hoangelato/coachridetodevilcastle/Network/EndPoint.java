package com.example.hoangelato.coachridetodevilcastle.Network;

import android.content.Context;
import android.os.Parcelable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by NguyenDuc on 8/2/2016.
 */
public class EndPoint {
    Vector<Connection> connectedConnections = new Vector<>();
    Thread dataListener;
    Vector<DataSolver> dataSolvers = new Vector<>();
    Vector<String> connectedIp = new Vector<>();

    public void addConnection(Socket socket) {
        connectedConnections.add(new Connection(socket));
        connectedIp.add(socket.getInetAddress().getHostAddress());
    }

    public boolean isConnected(Socket socket) {
        return connectedIp.contains(socket.getInetAddress().getHostAddress());
    }

    public interface DataSolver {
        void onDataReceived(final byte[] bytesReceived, Connection fromConnection);

        void onNewConnection(final int count, Connection newConnection);

        void onConnectFail(String reason);
    }

    public interface ProgressListener{
        void onUpdateProgress(double percentage);
        void onDone(Object result);
    }

    public void addDataSolver(DataSolver dataSolver) {
        dataSolvers.add(dataSolver);
    }

    public EndPoint() {
        dataListener = new Thread(new ListenForDataThread());
        dataListener.start();
    }

    void onDataReceived(byte[] bytesReceived, Connection fromConnection) {
        Enumeration<DataSolver> solvers = dataSolvers.elements();
        while (solvers.hasMoreElements()) {
            solvers.nextElement().onDataReceived(bytesReceived, fromConnection);
        }
    }

    void onNewConnection(int count) {
        Enumeration<DataSolver> solvers = dataSolvers.elements();
        while (solvers.hasMoreElements()) {
            solvers.nextElement().onNewConnection(count, connectedConnections.get(count));
        }
    }

    void onConnectFail(String reason) {
        Enumeration<DataSolver> solvers = dataSolvers.elements();
        while (solvers.hasMoreElements()) {
            solvers.nextElement().onConnectFail(reason);
        }
    }

    class ListenForDataThread extends Thread {
        @Override
        public void run() {
            while (true) {
                Enumeration<Connection> connections = connectedConnections.elements();
                while (connections.hasMoreElements()) {
                    Connection curConnection = connections.nextElement();
                    try {
                        if (curConnection.mObjectReader.available() != 0) {
                            int dataLength = curConnection.mObjectReader.readInt();
                            byte[] bytesReceived = new byte[dataLength];
                            curConnection.mObjectReader.readFully(bytesReceived);

                            onDataReceived(bytesReceived, curConnection);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


    public void send(int connectionPos, Parcelable data) {
        send(connectedConnections.get(connectionPos), data);
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


    public static String getCurrentIp() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip = inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }

    public void onDestroy() {
        Iterator<Connection> connections = connectedConnections.iterator();
        while (connections.hasNext()) {
            connections.next().close();
        }
    }
}
