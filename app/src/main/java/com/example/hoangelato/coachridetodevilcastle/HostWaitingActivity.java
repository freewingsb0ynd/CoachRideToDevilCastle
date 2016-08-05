package com.example.hoangelato.coachridetodevilcastle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hoangelato.coachridetodevilcastle.Network.Connection;
import com.example.hoangelato.coachridetodevilcastle.Network.DataHelper;
import com.example.hoangelato.coachridetodevilcastle.Network.EndPoint;
import com.example.hoangelato.coachridetodevilcastle.Network.Server;

import java.util.ArrayList;
import java.util.List;

public class HostWaitingActivity extends AppCompatActivity {
    ListView connectedPlayers;
    Button startGameBtn;
    Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_waiting);
        initView();
    }

    private void initView() {
        connectedPlayers = (ListView) findViewById(R.id.list_player);
        startGameBtn = (Button) findViewById(R.id.btn_start_game);
        server = new Server();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, R.layout.connected_player, R.id.player_ip, new ArrayList<String>(0)
        );

        connectedPlayers.setAdapter(arrayAdapter);

        server.addDataSolver(new EndPoint.DataSolver() {
            @Override
            public void onDataReceived(byte[] bytesReceived, Connection fromConnection) {

            }

            @Override
            public void onNewConnection(int count, final Connection newConnection) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrayAdapter.add(newConnection.mSocket.getInetAddress().getHostAddress());
                }
                });
            }

            @Override
            public void onConnectFail(String reason) {

            }
        });
    }
}
