package com.example.hoangelato.coachridetodevilcastle.GameModels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bloe on 19/08/2016.
 */
public class PlayerData implements Serializable {

    public String playerName;
    public int team;
    public Occupation occupation;
    public ArrayList<Item> itemsList;
    public int playerPos;

    public PlayerData() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
        occupation.setUsed(true);
    }

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public int getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(int playerPos) {
        this.playerPos = playerPos;
    }
}
