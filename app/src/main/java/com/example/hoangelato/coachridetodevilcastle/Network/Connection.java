package com.example.hoangelato.coachridetodevilcastle.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by NguyenDuc on 8/5/2016.
 */

public class Connection {
    public Socket mSocket;
    public DataInputStream mObjectReader;
    public DataOutputStream mObjectWriter;
    public String destinationIp;
    public long lastChecked;

    public Connection(Socket socket) {
        mSocket = socket;
        destinationIp = mSocket.getInetAddress().getHostAddress();

        try {
            mObjectReader = new DataInputStream(mSocket.getInputStream());
            mObjectWriter = new DataOutputStream(mSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            mObjectReader.close();
            mObjectWriter.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Connection) {
            Connection connectionObj = (Connection) obj;
            return this.destinationIp.equals(connectionObj.destinationIp);
        } return false;
    }
}
