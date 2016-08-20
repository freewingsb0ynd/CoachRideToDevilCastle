package com.example.hoangelato.coachridetodevilcastle.GameModels;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by bloe on 19/08/2016.
 */
public class HostData implements Serializable {

    public ArrayList<PlayerData> playersList = new ArrayList<>();
    public ArrayList<Occupation> occupationsLeft = new ArrayList<>();
    public ArrayList<Item> itemsLeft = new ArrayList<>();
    public static int gameTurn;
    public int numberOfPlayers;
    ArrayList<Integer> teamBudget = new ArrayList<>();
    public int wonTeam;

    public HostData(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        Log.e("HostData", String.valueOf(numberOfPlayers));
        initHostData();
    }

    private void initHostData() {

        initPlayersData();

        initConstantLists();

        initOccupationList();
    }

    private void initPlayersData() {
        for(int i = 0; i < numberOfPlayers; i++) {
            playersList.add(new PlayerData());
        }
    }

    private void initOccupationList() {
        for(int i = 0; i <= 9; i++) {
            occupationsLeft.add(new Occupation(i));
        }
        Collections.shuffle(occupationsLeft);

        for(int i = numberOfPlayers-1; i >= 0; i--) {
            Log.e("loop", String.valueOf(i));
            playersList.get(i).setOccupation(occupationsLeft.get(i));
            occupationsLeft.remove(i);
        }
    }

    private void initConstantLists() {
        for(int i = 0; i < numberOfPlayers; i++) {
            teamBudget.add(i >= (numberOfPlayers / 2) ? 2 : 1);
        }
        Collections.shuffle(teamBudget);

        for(int i = 0; i <numberOfPlayers; i++) {
            playersList.get(i).setTeam(teamBudget.get(i));
        }
    }
}
