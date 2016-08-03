package com.example.hoangelato.coachridetodevilcastle.Network;

import android.content.Context;
import android.os.Parcelable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
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
    public DataSender dataSender;
    Vector<DataSolver> dataSolvers = new Vector<>();



    public interface DataSolver {
        void onDataReceived(final byte[] bytesReceived);
        void onNewConnection(final int count);
    }

    public void addDataSolver(DataSolver dataSolver) {
        dataSolvers.add(dataSolver);
    }

    public EndPoint() {
        dataListener = new Thread(new ListenForDataThread());
        dataListener.start();

        dataSender = new DataSender();
    }

    void onDataReceived(byte[] bytesReceived) {
        Enumeration<DataSolver> solvers = dataSolvers.elements();
        while(solvers.hasMoreElements()) {
            solvers.nextElement().onDataReceived(bytesReceived);
        }
    }

    void onNewConnection(int count) {
        Enumeration<DataSolver> solvers = dataSolvers.elements();
        while(solvers.hasMoreElements()) {
            solvers.nextElement().onNewConnection(count);
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

                            onDataReceived(bytesReceived);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public class DataSender {
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
