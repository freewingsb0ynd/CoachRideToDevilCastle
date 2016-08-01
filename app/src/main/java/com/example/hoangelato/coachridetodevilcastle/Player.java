package com.example.hoangelato.coachridetodevilcastle;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Player implements PlayerActions{
    private String username;
    private int team;
    private Occupation occupation;
    public ArrayList<Item> itemsList;
    public ArrayList<Player> teammatesList;
    public ArrayList<Player> opponentsList;
    public ArrayList<Item> teammateItems;
    public ArrayList<Item> opponentItems;
    protected Host host;

    //Getters and Setters


    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    //Constructors

    public Player(String username, Host host) {
        this.username = username;
        this.itemsList = new ArrayList<Item>();
        this.opponentsList = new ArrayList<Player>();
        this.teammatesList = new ArrayList<Player>();
        this.teammateItems = new ArrayList<Item>();
        this.opponentItems = new ArrayList<Item>();
        this.host = host;
    }

    //methods


    @Override
    public void trade(Player p) {

    }

    @Override
    public void war(Player p) {
        WarEvent war = new WarEvent(this, p);

    }

    @Override
    public void pass() {

    }

    @Override
    public void declare(int team) {

    }

    public void playTurn(){

    }

    @Override
    public void sendMess(Player p){
        this.teammatesList.add(p);
        p.teammatesList.add(this);
    }



}
