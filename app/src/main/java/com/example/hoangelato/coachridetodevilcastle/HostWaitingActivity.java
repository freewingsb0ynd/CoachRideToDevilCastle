package com.example.hoangelato.coachridetodevilcastle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hoangelato.coachridetodevilcastle.CustomView.MyAdapter;
import com.example.hoangelato.coachridetodevilcastle.CustomView.MyDataNode;
import com.example.hoangelato.coachridetodevilcastle.CustomView.OnItemClickListener;
import com.example.hoangelato.coachridetodevilcastle.Network.Connection;
import com.example.hoangelato.coachridetodevilcastle.Network.EventListener;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkNode;
import com.example.hoangelato.coachridetodevilcastle.Network.Server;

import java.util.Vector;

public class HostWaitingActivity extends AppCompatActivity {
    public static final String PLAYER_INFO_VIEWHOLDER = "player_info_viewHolder";

    RecyclerView connectedPlayersRecyclerView;
    Button startGameBtn;
    Server mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_waiting);
        initView();
    }

    private void initView() {
        connectedPlayersRecyclerView = (RecyclerView) findViewById(R.id.list_player);
        startGameBtn = (Button) findViewById(R.id.btn_start_game);

        mServer = new Server(this) {
            @Override
            public void customInitialData(Bundle data) {
                data.putString(NetworkNode.NAME_TAG, getIntent().getStringExtra(Player.USERNAME_TAG));
            }
        };
        mServer.create();

        setUpConnectedPlayersRecyclerView();
    }

    public void setUpConnectedPlayersRecyclerView() {
        final PlayerInfoAdapter playerInfoAdapter = new PlayerInfoAdapter(
                this, new Vector<PlayerInfo>(), null
        );
        connectedPlayersRecyclerView.setAdapter(playerInfoAdapter);
        connectedPlayersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN
                        , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mServer.disconnectWith(mServer.getConnection(viewHolder.getAdapterPosition()));
                                playerInfoAdapter.delete(viewHolder.getAdapterPosition());
                            }
                        });
                    }
                });
        itemTouchHelper.attachToRecyclerView(connectedPlayersRecyclerView);

        mServer.addEventListener(new EventListener() {
            @Override
            public void onDataReceived(Bundle data, Connection connection) {
                Log.e("HostAct", data.getString(NetworkNode.ACTION_TAG));
            }

            @Override
            public void onNewConnection(Connection connection) {
            }

            @Override
            public void onConnectFail(String reason, String destinationIp) {

            }

            @Override
            public void onInitialDataReceived(final Bundle data, Connection connection) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playerInfoAdapter.add(
                                new PlayerInfo(
                                        data.getString(NetworkNode.NAME_TAG, "null"), data.getString(NetworkNode.IP_TAG, "null")
                                )
                                , playerInfoAdapter.getItemCount()
                        );
                        Log.e("Host", "new client");
                    }
                });
                Host host = (Host)data.getSerializable("hihi");
                Log.e("hihi", String.valueOf(host.test));

            }

            @Override
            public void onDisconnected(Bundle message, final Connection connection) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playerInfoAdapter.delete(mServer.getConnectionIndex(connection));
                        Log.e("HostAct", "client disconnected @ pos " + String.valueOf(mServer.getConnectionIndex(connection)));
                    }
                });
            }

            @Override
            public void onLosingConnection(Connection connection) {

            }
        });
    }

    public class PlayerInfo implements MyDataNode {
        String playerName;
        String playerIp;

        public PlayerInfo(String playerName, String playerIp) {
            this.playerName = playerName;
            this.playerIp = playerIp;
        }
    }

    public class PlayerInfoAdapter extends MyAdapter<PlayerInfo> {

        public PlayerInfoAdapter(Context mContext, Vector<PlayerInfo> dataList, OnItemClickListener onItemClickListener) {
            super(mContext, dataList, onItemClickListener);
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.connected_player, parent, false);

            return new PlayerInfoViewHolder(view);
        }

        public class PlayerInfoViewHolder extends MyViewHolder {
            TextView textViewPlayerName;
            TextView textViewPlayerIp;
            public PlayerInfoViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            public void initView(View view) {
                textViewPlayerIp = (TextView) view.findViewById(R.id.player_ip);
                textViewPlayerName = (TextView) view.findViewById(R.id.player_name);
            }

            @Override
            public void setData(PlayerInfo data) {
                textViewPlayerName.setText(data.playerName);
                textViewPlayerIp.setText(data.playerIp);
            }
        }
    }
}
