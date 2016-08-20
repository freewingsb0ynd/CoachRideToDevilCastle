package com.example.hoangelato.coachridetodevilcastle.Activities.HostActivities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.hoangelato.coachridetodevilcastle.Activities.ClientActivities.ClientConnectActivity;
import com.example.hoangelato.coachridetodevilcastle.Activities.GameplayActivities.GameplayActivity;
import com.example.hoangelato.coachridetodevilcastle.Activities.MainActivity;
import com.example.hoangelato.coachridetodevilcastle.CustomView.ConnectedPlayerAdapter;
import com.example.hoangelato.coachridetodevilcastle.CustomView.PlayerInfo;
import com.example.hoangelato.coachridetodevilcastle.GameModels.GameTags;
import com.example.hoangelato.coachridetodevilcastle.GameModels.Host;
import com.example.hoangelato.coachridetodevilcastle.GameModels.Player;
import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.Connection;
import com.example.hoangelato.coachridetodevilcastle.Network.EventListener;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkTags;
import com.example.hoangelato.coachridetodevilcastle.Network.Server;
import com.example.hoangelato.coachridetodevilcastle.R;
import com.example.hoangelato.coachridetodevilcastle.databinding.HostWaitingActivityBinding;

import java.util.Vector;

/**
 * Created by bloe on 18/08/2016.
 */

public class HostWaitingActivity extends AppCompatActivity {
    public static final int NUMBER_OF_PLAYERS_NEEDED_TO_START = 1;

    public static Client mClient;
    public static Server mServer;
    public static Player mPlayer;
    public static Host mHost;
    public static Context mContext;

    HostWaitingActivityBinding binding;
    ConnectedPlayerAdapter connectedPlayerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.host_waiting_activity);

        initView();
        initNetwork();

    }

    private void initNetwork() {
        mServer = new Server(this) {
            @Override
            public void customInitialData(Bundle data) {
                data.putString(NetworkTags.NAME_TAG, MainActivity.USERNAME);
            }
        };
        mServer.create();
        mServer.addEventListener(new EventListener() {
            @Override
            public void onDataReceived(Bundle data, Connection connection) {
                String action = data.getString(NetworkTags.ACTION_TAG, "null");

                switch (action) {
                    case NetworkTags.ACTION_REQUEST_PLAYERS_LIST:
                        pushNewPlayerListToPlayers();
                }
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
                        connectedPlayerAdapter.add(new PlayerInfo(
                                data.getString(NetworkTags.NAME_TAG),
                                data.getString(NetworkTags.IP_TAG)
                        ));

                    }
                });

                pushNewPlayerListToPlayers();
            }

            @Override
            public void onDisconnected(Bundle message, Connection connection) {

            }

            @Override
            public void onLosingConnection(Connection connection) {

            }
        });

        mClient = new Client(this) {
            @Override
            public void customInitialData(Bundle data) {
                data.putString(NetworkTags.NAME_TAG, MainActivity.USERNAME);
            }
        };
        connectClientToServer();
    }

    private void pushNewPlayerListToPlayers() {
        Bundle bundle = new Bundle();
        bundle.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_PUSH_NEW_PLAYERS_LIST);
        bundle.putSerializable(NetworkTags.PLAYERS_LIST, connectedPlayerAdapter.getDataList());

        mServer.sendToAll(bundle);
    }

    private void connectClientToServer() {
        String connectResult = mClient.connectToHost(
                mServer.getStringCurrentIp(),
                Server.SERVER_PORT,
                ClientConnectActivity.TIME_TO_WAIT_FOR_CONNECTION
        );
        Log.e("Connect result", connectResult);
    }

    private void initView() {
        connectedPlayerAdapter = new ConnectedPlayerAdapter(this, new Vector<PlayerInfo>(), null);
        binding.recyclerViewHostListConnecctedPlayer.setAdapter(connectedPlayerAdapter);
        binding.recyclerViewHostListConnecctedPlayer.setLayoutManager(new LinearLayoutManager(this));

        binding.buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mServer.getConnectionCount() < NUMBER_OF_PLAYERS_NEEDED_TO_START) {
                    Toast.makeText(
                            mContext,
                            "Need at least " + String.valueOf(NUMBER_OF_PLAYERS_NEEDED_TO_START) + " to start",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    mHost = new Host(mServer);
                    mClient.addEventListener(new EventListener() {
                        @Override
                        public void onDataReceived(Bundle data, Connection connection) {
                            String gameAction = data.getString(GameTags.GAME_ACTION_TAG, "null");

                            switch (gameAction) {
                                case GameTags.GAME_ACTION_START_GAME:
                                    mPlayer = new Player(mClient);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            moveToGamePlayActivity();
                                        }
                                    });

                                    removeListener();
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

                    mHost.startGame();
                }
            }
        });
    }

    private void moveToGamePlayActivity() {
        Intent nextScreen = new Intent(HostWaitingActivity.this, GameplayActivity.class);
        nextScreen.putExtra(GameTags.IS_HOST, true);
        startActivity(nextScreen);
    }

}
