package com.example.hoangelato.coachridetodevilcastle;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Player implements PlayerActions {
    private String username;
    private int team;
    private String occupation;
    private Vector<Item> itemsList;
    private int order;
    private String vote;


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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Vector<Item> getItemsList() {
        return itemsList;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {

        this.order = order;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    //Constructors

    public Player(String username) {
        this.username = username;
        this.itemsList = new Vector<Item>();
        this.vote = "pass";
    }

    //methods

    @Override
    public void trade(String username) {

    }

    @Override
    public void war(String username) {

    }

    @Override
    public void pass() {

    }

    @Override
    public void declare(int team) {

    }


}
