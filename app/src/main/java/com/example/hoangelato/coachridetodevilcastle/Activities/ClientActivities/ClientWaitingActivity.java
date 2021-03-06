package com.example.hoangelato.coachridetodevilcastle.Activities.ClientActivities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;


import com.example.hoangelato.coachridetodevilcastle.Activities.GameplayActivities.GameplayActivity;
import com.example.hoangelato.coachridetodevilcastle.CustomView.ConnectedPlayerAdapter;
import com.example.hoangelato.coachridetodevilcastle.CustomView.PlayerInfo;
import com.example.hoangelato.coachridetodevilcastle.GameModels.GameTags;
import com.example.hoangelato.coachridetodevilcastle.GameModels.Player;
import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.Connection;
import com.example.hoangelato.coachridetodevilcastle.Network.EventListener;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkTags;
import com.example.hoangelato.coachridetodevilcastle.R;
import com.example.hoangelato.coachridetodevilcastle.databinding.ClientWaitingActivityBinding;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by bloe on 18/08/2016.
 */

public class ClientWaitingActivity extends AppCompatActivity {
    public static Player mPlayer;
    public static Client mClient;

    ClientWaitingActivityBinding binding;
    ConnectedPlayerAdapter connectedPlayerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        binding = DataBindingUtil.setContentView(this, R.layout.client_waiting_activity);

        mClient = ClientConnectActivity.mClient;

        initNetwork();
        initView();
    }

    private void initNetwork() {
        mClient.addEventListener(new EventListener() {
            @Override
            public void onDataReceived(final Bundle data, Connection connection) {
                String gameAction = data.getString(GameTags.GAME_ACTION_TAG, "null");
                String action = data.getString(NetworkTags.ACTION_TAG, "null");

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

                switch (action) {
                    case NetworkTags.ACTION_PUSH_NEW_PLAYERS_LIST:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                connectedPlayerAdapter.setDataList(
                                        new Vector<PlayerInfo>(
                                                (ArrayList<PlayerInfo>) data.getSerializable(NetworkTags.PLAYERS_LIST)
                                        )
                                );

                                Toast.makeText(ClientWaitingActivity.this, "updated", Toast.LENGTH_LONG).show();
                            }
                        });

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
        requestForPlayerList();
    }

    private void requestForPlayerList() {
        Bundle bundle = new Bundle();
        bundle.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_REQUEST_PLAYERS_LIST);

        mClient.send(0, bundle);
    }

    private void initView() {
        connectedPlayerAdapter = new ConnectedPlayerAdapter(this, new Vector<PlayerInfo>(), null);
        binding.recyclerViewPlayerListConnecctedPlayer.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewPlayerListConnecctedPlayer.setAdapter(connectedPlayerAdapter);
    }

    private void moveToGamePlayActivity() {
        Intent intent = new Intent(ClientWaitingActivity.this, GameplayActivity.class);
        intent.putExtra(GameTags.IS_HOST, false);
        startActivity(intent);
    }
}
