package com.example.hoangelato.coachridetodevilcastle.GameModels;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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

        initItemList();
    }

    private void initItemList() {
        itemsLeft.add(new Item(0));
        itemsLeft.add(new Item(0));
        itemsLeft.add(new Item(0));
        itemsLeft.add(new Item(1));
        itemsLeft.add(new Item(1));
        itemsLeft.add(new Item(1));
        itemsLeft.add(new Item(4));
        itemsLeft.add(new Item(5));
        itemsLeft.add(new Item(6));
        itemsLeft.add(new Item(7));
        itemsLeft.add(new Item(8));
        itemsLeft.add(new Item(9));
        itemsLeft.add(new Item(10));
        itemsLeft.add(new Item(11));
        itemsLeft.add(new Item(12));
        itemsLeft.add(new Item(13));
        itemsLeft.add(new Item(14));
        itemsLeft.add(new Item(15));
        itemsLeft.add(new Item(16));

        Collections.shuffle(itemsLeft);

        Random rnd = new Random();
        itemsLeft.add(rnd.nextInt(7), new Item(2));
        itemsLeft.add(rnd.nextInt(8), new Item(3));

        for(int i = numberOfPlayers-1; i >= 0; i--) {
            playersList.get(i).addItem(itemsLeft.get(i));
            itemsLeft.remove(i);
        }
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
