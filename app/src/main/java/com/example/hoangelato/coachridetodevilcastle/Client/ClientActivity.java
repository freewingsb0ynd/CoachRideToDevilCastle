package com.example.hoangelato.coachridetodevilcastle.Client;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hoangelato.coachridetodevilcastle.CustomView.MyAdapter;
import com.example.hoangelato.coachridetodevilcastle.CustomView.MyDataNode;
import com.example.hoangelato.coachridetodevilcastle.CustomView.OnItemClickListener;
import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkNode;
import com.example.hoangelato.coachridetodevilcastle.Network.ProgressListener;
import com.example.hoangelato.coachridetodevilcastle.Player;
import com.example.hoangelato.coachridetodevilcastle.R;

import java.util.Enumeration;
import java.util.Vector;

public class ClientActivity extends AppCompatActivity {

    RecyclerView availableServers;
    Client mClient;
    String serverToConnect = "null";
    SwipeRefreshLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initView();
    }

    private void initView() {
        availableServers = (RecyclerView) findViewById(R.id.server_list);
        contentView = (SwipeRefreshLayout) findViewById(R.id.content_view);
        mClient = new Client(this) {
            @Override
            public void customInitialData(Bundle data) {
                data.putString(NetworkNode.NAME_TAG, getIntent().getStringExtra(Player.USERNAME_TAG));
            }
        };

        final AvailableServersAdapter availableServersAdapter = new AvailableServersAdapter(
                this, new Vector<ServerInfo>()
                , new OnItemClickListener<AvailableServersAdapter.ServerInfoViewHolder>() {
                    @Override
                    public void onItemClick(AvailableServersAdapter.ServerInfoViewHolder myViewHolder) {
                        serverToConnect = myViewHolder.data.serverIp;
                    }
                }
        );

        availableServers.setAdapter(availableServersAdapter);
        availableServers.setLayoutManager(new LinearLayoutManager(this));
        contentView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Vector<ServerInfo> serverInfos = new Vector<ServerInfo>();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClient.findServersIp(new ProgressListener<Vector<String>>() {
                            @Override
                            public void onUpdateProgress(double percentage) {
                                Log.e("ClientAct", String.valueOf(percentage));
                            }

                            @Override
                            public void onDone(Vector<String> result) {
                                Enumeration<String> ip = result.elements();
                                while(ip.hasMoreElements()) {
                                    serverInfos.add(new ServerInfo("client", ip.nextElement()));
                                }
                                Log.e("ClientAct", "done refreshing");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        availableServersAdapter.setDataList(serverInfos);
                                        contentView.setRefreshing(false);
                                    }
                                });
                            }
                        });
                    }
                }).start();

            }
        });
        contentView.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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