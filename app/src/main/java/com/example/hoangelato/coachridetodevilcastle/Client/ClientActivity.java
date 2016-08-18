package com.example.hoangelato.coachridetodevilcastle.Client;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hoangelato.coachridetodevilcastle.CustomView.MyAdapter;
import com.example.hoangelato.coachridetodevilcastle.CustomView.MyDataNode;
import com.example.hoangelato.coachridetodevilcastle.CustomView.OnItemClickListener;
import com.example.hoangelato.coachridetodevilcastle.Host;
import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkNode;
import com.example.hoangelato.coachridetodevilcastle.Network.ProgressListener;
import com.example.hoangelato.coachridetodevilcastle.Network.Server;
import com.example.hoangelato.coachridetodevilcastle.Network.ThreadHelper;
import com.example.hoangelato.coachridetodevilcastle.Player;
import com.example.hoangelato.coachridetodevilcastle.R;

import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ClientActivity extends AppCompatActivity {

    Client mClient;
    String serverToConnect = "null";
    EditText editTextIp;
    Button buttonConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initView();
    }

    private void initView() {
        editTextIp = (EditText) findViewById(R.id.ip_to_connect);
        buttonConnect =(Button) findViewById(R.id.connect);

        mClient = new Client(this) {
            @Override
            public void customInitialData(Bundle data) {
                data.putString(NetworkNode.NAME_TAG, getIntent().getStringExtra(Player.USERNAME_TAG));
                Host host = new Host();
                host.test = 10;
                data.putSerializable("hihi", host);
            }
        };

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.connectToHost(editTextIp.getText().toString(), 6969);
                Log.e("ClientAct","Connected");
            }
        });
    }

    class ServerInfo implements MyDataNode {
        String serverName;
        String serverIp;

        public ServerInfo(String serverName, String serverIp) {
            this.serverName = serverName;
            this.serverIp = serverIp;
        }
    }

    class AvailableServersAdapter extends MyAdapter<ServerInfo> {

        public AvailableServersAdapter(Context mContext, Vector<ServerInfo> dataList, OnItemClickListener<ServerInfoViewHolder> onItemClickListener) {
            super(mContext, dataList, onItemClickListener);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.avail_server, parent, false);
            return new ServerInfoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
        }

        public class ServerInfoViewHolder extends AvailableServersAdapter.MyViewHolder {

            TextView textViewServerIp;
            TextView textViewServerName;

            public ServerInfoViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            public void initView(View view) {
                textViewServerIp = (TextView) view.findViewById(R.id.server_ip);
                textViewServerName = (TextView) view.findViewById(R.id.server_name);
            }

            @Override
            public void setData(ServerInfo data) {
                textViewServerIp.setText(data.serverIp);
                textViewServerName.setText(data.serverName);
            }
        }
    }
}