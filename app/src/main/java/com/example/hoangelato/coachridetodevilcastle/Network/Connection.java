package com.example.hoangelato.coachridetodevilcastle.Network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by NguyenDuc on 8/2/2016.
 */
public class Connection {
    private Socket mSocket;
    public BufferedReader mReader;
    public PrintWriter mWriter;
    public DataInputStream mObjectReader;
    public DataOutputStream mObjectWriter;

    public Connection(Socket socket) {
        mSocket = socket;

        try {
            mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mWriter = new PrintWriter(mSocket.getOutputStream(), true);
            mObjectReader = new DataInputStream(mSocket.getInputStream());
            mObjectWriter = new DataOutputStream(mSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            mReader.close();
            mWriter.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
