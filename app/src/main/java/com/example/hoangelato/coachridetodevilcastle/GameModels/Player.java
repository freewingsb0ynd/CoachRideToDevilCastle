package com.example.hoangelato.coachridetodevilcastle.GameModels;

import android.os.Bundle;

import com.example.hoangelato.coachridetodevilcastle.Activities.MainActivity;
import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.Connection;
import com.example.hoangelato.coachridetodevilcastle.Network.EventListener;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkTags;


/**
 * Created by bloe on 19/08/2016.
 */
public class Player {

    public Client mClient;
    public HostData mHostData;

    public int position;

    public boolean finishedLoadingDataFromHost = false;

    public Player(Client mClient) {
        this.mClient = mClient;
        setupClient();
    }

    public void setupClient() {
        mClient.addEventListener(new EventListener() {
            @Override
            public void onDataReceived(Bundle data, Connection connection) {
                String action = data.getString(NetworkTags.ACTION_TAG, "null");
                String gameAction = data.getString(GameTags.GAME_ACTION_TAG, "null");

                switch (action) {
                    case NetworkTags.ACTION_PUSH_NEW_DATA_TO_PLAYERS :
                        mHostData = (HostData) data.getSerializable(GameTags.HOST_DATA);
                        finishedLoadingDataFromHost = true;
                        break;
                    case NetworkTags.ACTION_PUSH_PLAYER_POSITION_TO_PLAYER:
                        position = data.getInt(NetworkTags.PLAYER_POSITION);
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
        pushPlayerInitialDataToHost();
    }

    private void pushPlayerInitialDataToHost() {
        Bundle bundle = new Bundle();
        bundle.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_PUSH_PLAYER_INITIAL_DATA_TO_HOST);
        bundle.putString(NetworkTags.NAME_TAG, MainActivity.USERNAME);
        //other initial data

        mClient.send(bundle);
    }

    public void pushNewDataToHost() {
        Bundle bundle = new Bundle();
        bundle.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_PUSH_NEW_DATA_TO_HOST);
        bundle.putSerializable(GameTags.HOST_DATA, mHostData);
        mClient.send(0, bundle);
    }

    public PlayerData getPlayerData() {
        return mHostData.playersList.get(position);
    }
}
