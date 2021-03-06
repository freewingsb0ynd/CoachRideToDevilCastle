package com.example.hoangelato.coachridetodevilcastle.GameModels;

import android.os.Bundle;
import android.util.Log;

import com.example.hoangelato.coachridetodevilcastle.Network.Connection;
import com.example.hoangelato.coachridetodevilcastle.Network.EventListener;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkTags;
import com.example.hoangelato.coachridetodevilcastle.Network.Server;


/**
 * Created by hoangelato on 26/07/2016.
 */
public class Host {

    HostData mHostData;
    int numberOfPlayers;

    int turn = 0;

    public Server mServer;

    public Host(Server mServer) {
        this.mServer = mServer;
        setupSever();
    }

    private void setupSever() {
        initHost();

        mServer.addEventListener(new EventListener() {
            int countInitialDataReceived = 0;

            @Override
            public void onDataReceived(Bundle data, Connection connection) {
                String action = data.getString(NetworkTags.ACTION_TAG, "null");
                String gameAction = data.getString(GameTags.GAME_ACTION_TAG, "null");

                switch (action) {
                    case NetworkTags.ACTION_PUSH_NEW_DATA_TO_HOST :
                        updateData((HostData) data.getSerializable(GameTags.HOST_DATA));
                        break;
                    case NetworkTags.ACTION_PUSH_PLAYER_INITIAL_DATA_TO_HOST:
                        updatePlayerInitialData(mServer.getConnectionIndex(connection), data);

                        pushPlayerPosition(mServer.getConnectionIndex(connection));

                        countInitialDataReceived++;
                        if (countInitialDataReceived == numberOfPlayers) {
                            pushNewDataToPlayers();
                            notifyPlayerTurn(0);
                        }
                        break;
                    case NetworkTags.ACTION_SEND_TO_OTHER_CLIENT :
                        int desClient = data.getInt(NetworkTags.TO_CLIENT);
                        mServer.send(desClient, data);
                        Log.e("Host", "passed to other client " + desClient);
                        break;
                }

                switch (gameAction) {
                    case GameTags.ACTION_FINISH_TURN :
                        Log.e("Host", numberOfPlayers+" "+turn);
                        notifyPlayerTurn(
                                (++turn) % mHostData.numberOfPlayers
                        );
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
    }

    private void notifyPlayerTurn(int playerIndex) {
        Bundle bundle = new Bundle();
        bundle.putString(GameTags.GAME_ACTION_TAG, GameTags.ACTION_PLAY_TURN);

        mServer.send(playerIndex, bundle);
    }

    private void pushPlayerPosition(int playerIndex) {
        Bundle bundle = new Bundle();
        bundle.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_PUSH_PLAYER_POSITION_TO_PLAYER);
        bundle.putInt(NetworkTags.PLAYER_POSITION, playerIndex);

        mServer.send(playerIndex, bundle);
    }

    private void initHost() {
        numberOfPlayers = mServer.getConnectionCount();
        mHostData = new HostData(numberOfPlayers);
    }

    private void updatePlayerInitialData(int playerIndex, Bundle data) {
        mHostData.playersList.get(playerIndex).setPlayerName(data.getString(NetworkTags.NAME_TAG));
    }

    public void startGame() {
        //init playerList from server connectionList
        notifyPlayersToStartGame();
    }

    private void notifyPlayersToStartGame() {
        Bundle bundle = new Bundle();
        bundle.putString(GameTags.GAME_ACTION_TAG, GameTags.GAME_ACTION_START_GAME);

        mServer.sendToAll(bundle);
    }

    private void pushNewDataToPlayers() {
        Bundle data = new Bundle();
        data.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_PUSH_NEW_DATA_TO_PLAYERS);
        data.putSerializable(GameTags.HOST_DATA, mHostData);

        mServer.sendToAll(data);
    }
    private void updateData(HostData newHostData) {
        //update data
        this.mHostData = newHostData;

        pushNewDataToPlayers();
    };
}
